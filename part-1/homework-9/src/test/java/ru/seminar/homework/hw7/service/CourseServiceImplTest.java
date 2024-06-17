package ru.seminar.homework.hw7.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.repository.CourseRepository;
import ru.seminar.homework.hw7.service.impl.CourseServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CourseServiceImplTest {

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseServiceImpl courseService;

    @Test
    public void createCourse_whenValidInput_thenReturnsCourse() {
        Course course = new Course(1L, "Java", "Java course", 130, null);
        Course expectedCourse = new Course(1L, "Java", "Java course", 130, null);
        when(courseRepository.save(course)).thenReturn(expectedCourse);

        Course actualCourse = courseService.createCourse(course);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void getAllCourses_whenValidInput_thenReturnsCourse() {
        List<Course> courses = List.of(
                new Course(1L, "Java", "Java course", 130, null),
                new Course(2L, "Python", "Python course", 100, null),
                new Course(3L, "C++", "C++ course", 140, null)
        );
        Pageable pageable = PageRequest.of(0, 3);
        when(courseRepository.findByOrderByName(pageable)).thenReturn(new PageImpl<>(courses));

        List<Course> actualCourses = courseService.getAllCourses(0, 3);

        assertEquals(courses, actualCourses);
    }

    @Test
    public void getCourseById_whenValidInput_thenReturnsCourse() {
        Course course = new Course(1L, "Java", "Java course", 130, null);
        when(courseRepository.findById(1L)).thenReturn(java.util.Optional.of(course));

        Course actualCourse = courseService.getCourseById(1L);

        assertEquals(course, actualCourse);
    }

}
