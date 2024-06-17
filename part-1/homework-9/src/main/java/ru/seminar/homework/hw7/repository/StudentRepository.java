package ru.seminar.homework.hw7.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.NamedQuery;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Page<Student> findByOrderByLogin(Pageable pageable);

    @Lock(LockModeType.OPTIMISTIC)
    @NonNull
    Optional<Student> findById(@NonNull Long id);

}
