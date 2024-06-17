package ru.seminar.homework.hw7.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "course")
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String description;

    Integer duration;

    @Version
    Integer version;

    @ManyToMany(mappedBy = "courses")
    List<Student> students;

    public Course() {
        students = new ArrayList<>();
    }

    public Course(Long id, String name, String description, Integer duration, List<Student> students) {
        version = 0;
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.students = students;
    }
}
