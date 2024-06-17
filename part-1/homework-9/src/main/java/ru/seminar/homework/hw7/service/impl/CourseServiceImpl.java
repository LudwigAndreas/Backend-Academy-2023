package ru.seminar.homework.hw7.service.impl;

import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.seminar.homework.hw7.exception.NotFoundException;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.repository.CourseRepository;
import ru.seminar.homework.hw7.service.CourseService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Course> getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findByOrderByName(pageable).stream().toList();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new NotFoundException("Course not found"));
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        int maxRetries = 3;
        for (int i = 0; i < maxRetries; ++i) {
            try {
                Course courseFromDB = courseRepository.findById(id).orElseThrow(() -> new NotFoundException("Course not found"));
                courseFromDB.setName(course.getName());
                courseFromDB.setDescription(course.getDescription());
                courseFromDB.setDuration(course.getDuration());
                courseFromDB.setStudents(course.getStudents());
                return courseRepository.save(courseFromDB);
            } catch (OptimisticLockException | OptimisticLockingFailureException e) {
                if (i == maxRetries - 1) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Data conflict after multiple retries.");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Data conflict after multiple retries");
    }
}
