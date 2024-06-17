package ru.seminar.homework.hw7.service;

import ru.seminar.homework.hw7.model.Student;

import java.util.List;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudentById(Long id);

    Student updateStudent(Long id, Student student);

    void deleteStudentById(Long id);

    List<Student> getAllStudents(int page, int size);

    Student addStudentToCourse(Long studentId, Long courseId);

    void deleteStudent(Long id);
}
