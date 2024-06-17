package ru.tinkoff.seminars.accounting.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
@DynamicInsert
public class UserModel {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String login;

    private String email;

    private String phone;

    private String password;

    private Date birthDate;

    private String about;

//    private List<?> skills;
//
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "registered")
    private Timestamp registrationDate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "last_login")
    private Timestamp lastLoginDate;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean active;

}
