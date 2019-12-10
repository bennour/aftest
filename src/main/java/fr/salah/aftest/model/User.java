package fr.salah.aftest.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Document(collection = "user")
public class User {

    @Id
    private String id;

    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String country;

    private String username;
}
