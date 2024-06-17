package ru.seminar.homework.hw7.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    @Null
    Long id;

    @NotNull
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    String name;

    @NotNull
    @Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters")
    String description;

    @NotNull
    @Min(1)
    Integer duration;

    @Null
    List<Long> students;
}
