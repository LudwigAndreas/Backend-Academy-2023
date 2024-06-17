package ru.seminar.homework.hw7.repository;

import jakarta.persistence.LockModeType;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.model.Student;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course, Long>, PagingAndSortingRepository<Course, Long> {

    Page<Course> findByOrderByName(Pageable pageable);

    @Lock(LockModeType.OPTIMISTIC)
    @NonNull
    Optional<Course> findById(@NonNull Long id);
}
