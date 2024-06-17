package ru.tinkoff.seminars.accounting.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UserDto {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("middleName")
    private String middleName;

//    TODO: add validation before saving email
    @JsonProperty("email")
    @Email
    @NotNull
    private String email;

//    TODO: add validation before saving phone number
    @JsonProperty("phone")
    @Pattern(regexp = "^\\+\\d{1,3}\\s?\\(?\\d{3}\\)?\\s?\\d{3}\\s?\\d{4}$", message = "Invalid phone number format")
    private String phone;

    @JsonProperty("login")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$", message = "Invalid login format")
    private String login;

    @JsonProperty("dateOfBirth")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    @JsonProperty("about")
    private String about;

    @JsonProperty("skills")
    @Valid
    @UniqueElements
    private List<String> skills = null;


}
