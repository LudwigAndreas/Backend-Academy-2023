package ru.seminar.homework.hw7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Page<Student> findByOrderByLogin(Pageable pageable);

}
