package com.example.examenrest.model;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserDto {
    private String id;

    @NotBlank(message = "name is required")
    @Size(max = 30, message = "name must be maximum 30 characters")
    @Pattern(regexp = "^[a-zA-Z]+(\\s*[a-zA-Z]*)*[a-zA-Z]+$", message = "name is not valid")
    private String name;

    @NotBlank(message = "lastname is required")
    @Size(max=30, message = "lastname must be maximum 30 characters")
    @Pattern(regexp = "^[a-zA-Z]+(\\s*[a-zA-Z]*)*[a-zA-Z]+$", message = "lastname is not valid")
    private String lastName;

    @NotBlank(message = "email is required")
    @Size(max = 50, message = "email must be maximum 50 characters")
    @Email(message = "email is not valid")
    private String email;

@NotNull(message = "Date of birth is required")
    private LocalDate dob;

    @Size(min = 8, message = "password must contain a minimum of 8 characters")
    private String password;

}