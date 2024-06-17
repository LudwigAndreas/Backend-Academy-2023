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
import ru.seminar.homework.hw7.model.Student;
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

//    @Transactional
    @Override
    public Student updateStudent(Long id, Student student) {
        int maxRetries = 3;
        for (int i = 0; i < maxRetries; ++i) {
            try {
                Student studentFromDB = studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Student not found"));
                studentFromDB.setLogin(student.getLogin());
                studentFromDB.setFirstName(student.getFirstName());
                studentFromDB.setLastName(student.getLastName());
                studentFromDB.setAge(student.getAge());

                return studentRepository.save(studentFromDB);
            } catch (OptimisticLockException | OptimisticLockingFailureException e) {
                if (i == maxRetries - 1) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Data conflict after multiple retries.");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Data conflict after multiple retries");
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

    @Transactional
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

    @Transactional
    @Override
    public Student removeStudentFromCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        Course course = courseService.getCourseById(courseId);
        if (!student.getCourses().contains(course)) {
            throw new NotFoundException("Student not subscribed to this course");
        }
        student.getCourses().remove(course);
        return studentRepository.save(student);
    }
}
