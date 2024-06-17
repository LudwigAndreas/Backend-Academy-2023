package ru.tinkoff.seminars.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {

    @Id
    private Long id;
    private String name;
    private String aboutMe;
    @Column(name = "birthdate")
    private LocalDate birthDate;

    //    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "child",
//            joinColumns = @JoinColumn(name = "parent_id"),
//            inverseJoinColumns = @JoinColumn(name = "child_id"))
    @Transient
    private Set<Person> children;

}

