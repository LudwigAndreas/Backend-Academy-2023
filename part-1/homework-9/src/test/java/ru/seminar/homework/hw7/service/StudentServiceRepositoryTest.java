package ru.seminar.homework.hw7.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.seminar.homework.hw7.model.Student;
import ru.seminar.homework.hw7.repository.StudentRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StudentServiceRepositoryTest {

    @Autowired
    private StudentService studentService;

    @SpyBean
    private StudentRepository studentRepository;

    private final List<Integer> ages = List.of(21, 22);

    @BeforeEach
    void setUp() {
//        studentRepository.deleteAll();
    }

    @Test
    void updateStudent_shouldUpdateStudent_withoutConcurrency() throws Exception {
        // given
        Student student = studentRepository.save(new Student(null, "login", "firstName", "lastName", 20, List.of()));
        assertEquals(0, student.getVersion());

        // when
        for (final int age : ages) {
            studentService.updateStudent(student.getId(), new Student(null, "login", "firstName", "lastName", age, List.of()));
        }

        // then
        Student updatedStudent = studentRepository.findById(student.getId()).get();

        assertAll(
                () -> assertEquals(2, updatedStudent.getVersion()),
                () -> assertEquals(22, updatedStudent.getAge())
//                () -> verify(studentRepository, times(3)).save(any(Student.class));
        );

        studentRepository.deleteById(updatedStudent.getId());
    }

    @Test
    void updateStudent_shouldUpdateStudent_withOptimisticLockHandling() throws Exception {
        // given
        Student student = studentRepository.save(new Student(null, "login", "firstName", "lastName", 20, List.of()));
        assertEquals(0, student.getVersion());

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (final int age : ages) {
            executorService.execute(() ->
                    studentService.updateStudent(
                            student.getId(),
                            new Student(null, "login", "firstName", "lastName", age, List.of())));
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        // then
        Student updatedStudent = studentRepository.findById(student.getId()).get();

        assertAll(
                () -> assertEquals(2, updatedStudent.getVersion(), "Expected version to be 2"),
                () -> assertEquals(22, updatedStudent.getAge(), "Expected age to be 22"),
                () -> verify(studentRepository, atLeast(3)).save(any(Student.class))
        );

        studentRepository.deleteById(updatedStudent.getId());
    }
}
