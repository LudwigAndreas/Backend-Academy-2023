package ru.seminar.homework.hw7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.seminar.homework.hw7.model.Course;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long>, PagingAndSortingRepository<Course, Long> {

    Page<Course> findByOrderByName(Pageable pageable);
}
