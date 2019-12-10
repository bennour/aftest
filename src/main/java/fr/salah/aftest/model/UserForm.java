package fr.salah.aftest.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static fr.salah.aftest.constant.Constant.DATE_FORMAT;

@Data
public class UserForm {

    private String username;

    @NotNull
    @Size(min = 2, max = 30, message = "FirstName must be between 2 and 30 characters")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "LastName must be between 2 and 30 characters")
    private String lastName;

    @NotNull
    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    private String country;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private LocalDate birthDate;
}
