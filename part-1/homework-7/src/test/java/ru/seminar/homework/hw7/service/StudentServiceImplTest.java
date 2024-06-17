package ru.seminar.homework.hw7.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.model.Student;
import ru.seminar.homework.hw7.repository.StudentRepository;
import ru.seminar.homework.hw7.service.impl.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentServiceImplTest {

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private CourseService courseService;

    @Autowired
    private StudentServiceImpl studentService;


    @Test
    public void createStudent_whenValidInput_thenReturnsStudent() {
        Student student = new Student(1L, "Username", "Ivan", "Ivanov", 1, null);
        Student expectedStudent = new Student(1L, "Username", "Ivan", "Ivanov", 1, null);
        when(studentRepository.save(student)).thenReturn(expectedStudent);

        Student actualStudent = studentService.createStudent(student);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void getStudentById_whenValidInput_thenReturnsStudent() {
        Student student = new Student(1L, "Username", "Ivan", "Ivanov", 1, null);
        when(studentRepository.findById(1L)).thenReturn(java.util.Optional.of(student));

        Student actualStudent = studentService.getStudentById(1L);

        assertEquals(student, actualStudent);
    }

    @Test
    public void updateStudent_whenValidInput_thenReturnsStudent() {
        Student student = new Student(1L, "Username", "Ivan", "Ivanov", 1, null);
        Student expectedStudent = new Student(1L, "Username", "Ivan", "Ivanov", 1, null);
        when(studentRepository.save(student)).thenReturn(expectedStudent);
        when(studentRepository.findById(1L)).thenReturn(java.util.Optional.of(student));

        Student actualStudent = studentService.updateStudent(1L, student);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void deleteStudentById_whenValidInput_thenReturnsStudent() {
        Student student = new Student(1L, "Username", "Ivan", "Ivanov", 1, null);
        when(studentRepository.findById(1L)).thenReturn(java.util.Optional.of(student));

        studentService.deleteStudentById(1L);

        verify(studentRepository).deleteById(1L);
    }

    @Test
    public void getAllStudents_whenValidInput_thenReturnsStudent() {
        List<Student> students = List.of(
                new Student(1L, "Username", "Ivan", "Ivanov", 1, null),
                new Student(2L, "Username2", "Petr", "Petrov", 2, null),
                new Student(3L, "Username3", "Sidor", "Sidorov", 3, null)
        );
        Pageable pageable = PageRequest.of(0, 3);
        when(studentRepository.findByOrderByLogin(pageable)).thenReturn(new PageImpl<>(students));

        studentService.getAllStudents(0, 3);

        verify(studentRepository).findByOrderByLogin(pageable);

        assertEquals(students, studentService.getAllStudents(0, 3));
    }

    @Test
    public void addStudentToCourse_whenValidInput_thenReturnsStudent() {
        Student student = new Student(1L, "Username", "Ivan", "Ivanov", 1, new ArrayList<>());
        Course course = new Course(1L, "Java", "Java course", 130, new ArrayList<>());

        Student expectedStudent = new Student(1L, "Username", "Ivan", "Ivanov", 1, List.of(course));
        when(studentRepository.findById(1L)).thenReturn(java.util.Optional.of(student));
        when(courseService.getCourseById(1L)).thenReturn(course);
        when(studentRepository.save(student)).thenReturn(student);

        Student actualStudent = studentService.addStudentToCourse(1L, 1L);

        verify(studentRepository).save(student);
        verify(courseService).getCourseById(1L);
        verify(studentRepository).findById(1L);
        assertEquals(expectedStudent, actualStudent);
    }

}
