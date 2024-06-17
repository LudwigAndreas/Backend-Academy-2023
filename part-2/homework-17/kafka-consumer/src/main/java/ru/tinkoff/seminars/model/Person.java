package ru.tinkoff.seminars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private Long id;
    private String name;
    private String aboutMe;
    private LocalDate birthDate;
    private Set<Person> children;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", birthDate=" + birthDate +
                ", childrenCount=" + children.size() +
                '}';
    }

}