package ru.seminar.homework.hw7.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.model.Student;
import ru.seminar.homework.hw7.repository.CourseRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseServiceRepositoryTest {

    @Autowired
    private CourseService courseService;

    @SpyBean
    private CourseRepository courseRepository;

    private final List<String> names = List.of("Java", "Python");

    @BeforeEach
    void setUp() {
//        courseRepository.deleteAll();
    }

    @Test
    void updateCourse_shouldUpdateCourse_withoutConcurrency() throws Exception {
        // given
        Course course = courseRepository.save(new Course(null, "Java", "Java Course", 150, List.of()));
        assertEquals(0, course.getVersion());

        // when
        for (final String name : names) {
            courseService.updateCourse(course.getId(), new Course(null, name, "Java Course", 150, List.of()));
        }

        Course updatedCourse = courseRepository.findById(course.getId()).get();

        assertAll(
                () -> assertEquals(2, updatedCourse.getVersion()),
                () -> assertEquals("C++", updatedCourse.getName())
//                () -> verify(courseRepository, times(3)).save(any(Course.class))
        );

        courseRepository.deleteById(course.getId());
    }

    @Test
    void updateCourse_shouldUpdateCourse_withOptimisticLockHandling() throws Exception {
        // given
        Course course = courseRepository.save(new Course(null, "Java", "Java Course", 150, List.of()));
        assertEquals(0, course.getVersion());

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> courseService.updateCourse(course.getId(), new Course(null, "Python", "Python Course", 150, List.of())));
        executorService.execute(() -> courseService.updateCourse(course.getId(), new Course(null, "C++", "C++ Course", 150, List.of())));
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // then
        Course updatedCourse = courseRepository.findById(course.getId()).get();

        assertAll(
                () -> assertEquals(2, updatedCourse.getVersion()),
                () -> assertEquals("C++", updatedCourse.getName()),
                () -> verify(courseRepository, atLeast(3)).save(any(Course.class))
        );

        courseRepository.deleteById(course.getId());
    }

}
