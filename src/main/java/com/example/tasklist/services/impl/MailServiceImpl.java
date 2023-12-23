package com.example.tasklist.services.impl;

import com.example.tasklist.domain.MailType;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.services.MailService;
import com.example.tasklist.services.props.MailProperties;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

  private final Configuration configurationFreemarker;
  private final JavaMailSender mailSender;
  private final MailProperties mailProperties;

  @Override
  public void sendEmail(final User user, final MailType type, final Properties params) {
    switch (type) {
      case REGISTRATION -> sendRegistrationEmail(user, params);
      case REMINDER -> sendReminderEmail(user, params);
      default -> {
      }
    }
  }

  @SneakyThrows
  private void sendReminderEmail(final User user, final Properties params) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

    helper.setSubject("You have task to do in 1 hour");
    helper.setTo(user.getUsername());
    helper.setFrom(mailProperties.getUsername());

    String emailContent = getRemainderEmailContent(user, params);
    helper.setText(emailContent, true);

    mailSender.send(mimeMessage);
  }

  @SneakyThrows
  private void sendRegistrationEmail(final User user, final Properties params) {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

      helper.setSubject("Thank you for registration " + user.getName());
      helper.setTo(user.getUsername());
      helper.setFrom(mailProperties.getUsername());

      String emailContent = getRegistrationEmailContent(user, params);
      helper.setText(emailContent, true);

      mailSender.send(mimeMessage);
  }

  @SneakyThrows
  private String getRemainderEmailContent(final User user, final Properties params) {
    StringWriter stringWriter = new StringWriter();

    Map<String, Object> model = new HashMap<>();
    model.put("name", user.getName());
    model.put("title", params.getProperty("task.title"));
    model.put("description", params.getProperty("task.description"));
    configurationFreemarker.getTemplate("reminder.ftlh").process(model, stringWriter);

    return stringWriter.getBuffer().toString();
  }

  @SneakyThrows
  private String getRegistrationEmailContent(final User user, final Properties params) {
    StringWriter stringWriter = new StringWriter();

    Map<String, Object> model = new HashMap<>();
    model.put("name", user.getName());

    configurationFreemarker.getTemplate("register.ftlh").process(model, stringWriter);

    return stringWriter.getBuffer().toString();
  }
}
