package ru.seminar.homework.hw7.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.seminar.homework.hw7.exception.NotFoundException;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.model.Student;
import ru.seminar.homework.hw7.repository.CourseRepository;
import ru.seminar.homework.hw7.repository.StudentRepository;
import ru.seminar.homework.hw7.service.CourseService;
import ru.seminar.homework.hw7.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;

    public StudentServiceImpl(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    @Override
    public Student createStudent(Student student) {
        student.setCourses(List.of());
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Student not found"));
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student studentFromDB = studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Student not found"));
        studentFromDB.setLogin(student.getLogin());
        studentFromDB.setFirstName(student.getFirstName());
        studentFromDB.setLastName(student.getLastName());
        studentFromDB.setAge(student.getAge());
        return studentRepository.save(studentFromDB);
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Student> getAllStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findByOrderByLogin(pageable).stream().toList();
    }

    @Override
    public Student addStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        Course course = courseService.getCourseById(courseId);
        student.getCourses().add(course);
            return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
