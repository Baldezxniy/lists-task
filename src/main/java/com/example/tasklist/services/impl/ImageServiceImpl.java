package com.example.tasklist.services.impl;

import com.example.tasklist.domain.exceprion.ImageUploadExceprionException;
import com.example.tasklist.domain.task.TaskImage;
import com.example.tasklist.services.ImageService;
import com.example.tasklist.services.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  private final MinioClient minioClient;

  private final MinioProperties minioProperties;

  @Override
  public String upload(TaskImage image) {
    try {
      createBucket();
    } catch (Exception e) {
      throw new ImageUploadExceprionException("Image upload failed. " + e.getMessage());
    }

    MultipartFile file = image.getFile();
    if (file.isEmpty() || file.getOriginalFilename() == null) {
      throw new ImageUploadExceprionException("Image must have name.");
    }
    String filename = generateFilename(file);

    InputStream inputStream;

    try {
      inputStream = file.getInputStream();
    } catch (Exception e) {
      throw new ImageUploadExceprionException("Image upload failed. " + e.getMessage());
    }

    saveImage(inputStream, filename);
    return filename;
  }

  @SneakyThrows
  private void createBucket() {
    boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
            .bucket(minioProperties.getBucket())
            .build());

    if (!found) {
      minioClient.makeBucket(MakeBucketArgs.builder()
              .bucket(minioProperties.getBucket())
              .build());
    }
  }

  private String generateFilename(MultipartFile file) {
    String extension = getExtension(file);
    return UUID.randomUUID() + "." + extension;
  }

  private String getExtension(MultipartFile file) {
    return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
  }

  @SneakyThrows
  private void saveImage(InputStream inputStream, String filename) {
    minioClient.putObject(PutObjectArgs.builder()
            .stream(inputStream, inputStream.available(), -1)
            .bucket(minioProperties.getBucket())
            .object(filename)
            .build());
  }

}
