package com.example.tasklist.web.dto.user;

import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Schema(description = "UserDto")
public class UserDto {
  @Schema(description = "User id", example = "1")
  @NotNull(message = "Id must be not null.", groups = {OnUpdate.class})
  private long userId;

  @Schema(description = "User name", example = "john Doe")
  @NotNull(message = "Name must be not null.", groups = {OnCreate.class, OnUpdate.class})
  @Length(max = 255, message = "Name length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
  private String name;

  @Schema(description = "User username(email)", example = "johndoe@gmail.com")
  @NotNull(message = "Username must be not null.", groups = {OnCreate.class, OnUpdate.class})
  @Length(max = 255, message = "Username length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
  private String username;

  @Schema(description = "User crypted password", example = "$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotNull(message = "Password must be not null.", groups = {OnUpdate.class})
  private String password;

  @Schema(description = "User password confirmation", example = "$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotNull(message = "Password confirmation must be not null.", groups = {OnCreate.class})
  private String passwordConfirmation;
}
