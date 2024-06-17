package ru.seminar.homework.hw7.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import ru.seminar.homework.hw7.model.Course;

import java.util.List;

@Repository
public interface CourseService {

    Course createCourse(Course course);

    List<Course> getAllCourses(int page, int size);

    Course getCourseById(Long id);

    Course updateCourse(Long id, Course course);
}
