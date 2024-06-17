package ru.seminar.homework.hw7.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    @Null
    private Long id;

    @NotNull
    @Size(min = 3, max = 255, message = "Login must be between 3 and 255 characters")
    private String login;

    @NotNull
    @Size(min = 1, max = 255, message = "First name must be between 1 and 255 characters")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 255, message = "Last name must be between 1 and 255 characters")
    private String lastName;

    @NotNull
    @Min(1)
    @Max(150)
    private Integer age;

    @Null
    List<Long> courses;
}
