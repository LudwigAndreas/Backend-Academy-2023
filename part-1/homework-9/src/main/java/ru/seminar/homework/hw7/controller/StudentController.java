package ru.seminar.homework.hw7.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.seminar.homework.hw7.dto.StudentDto;
import ru.seminar.homework.hw7.mapper.StudentMapper;
import ru.seminar.homework.hw7.model.Student;
import ru.seminar.homework.hw7.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    private final StudentMapper studentMapper;

    @Autowired
    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> createStudent(@RequestBody @Valid StudentDto student) {
        Student createdStudent = studentService.createStudent(studentMapper.studentDtoToStudent(student));
        return buildResponseEntity(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping(produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentDto>> getAllStudents(
            @RequestParam(defaultValue = "0") @Min(0) int offset,
            @RequestParam(defaultValue = "10") @Min(1) @Max(10) int limit) {
        List<Student> students = studentService.getAllStudents(offset, limit);
        return buildResponseEntityList(students, HttpStatus.OK);
    }

    @GetMapping(value = "/{studentId}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long studentId) {
        Student student = studentService.getStudentById(studentId);
        return buildResponseEntity(student, HttpStatus.OK);
    }

    @PostMapping(value = "/{studentId}/courses/{courseId}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> addStudentToCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        Student updatedStudent = studentService.addStudentToCourse(studentId, courseId);
        return buildResponseEntity(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{studentId}/courses/{courseId}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> removeStudentFromCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        Student updatedStudent = studentService.removeStudentFromCourse(studentId, courseId);
        return buildResponseEntity(updatedStudent, HttpStatus.OK);
    }

    @PutMapping(value = "/{studentId}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> updateStudent(
            @PathVariable Long studentId,
            @RequestBody @Valid StudentDto updatedStudent) {
        Student student = studentService.updateStudent(studentId, studentMapper.studentDtoToStudent(updatedStudent));
        return buildResponseEntity(student, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<StudentDto> buildResponseEntity(Student student, HttpStatus httpStatus) {
        if (student == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(studentMapper.studentToStudentDto(student), httpStatus);
    }

    private ResponseEntity<List<StudentDto>> buildResponseEntityList(List<Student> student, HttpStatus httpStatus) {
        return new ResponseEntity<>(studentMapper.studentListToStudentDtoList(student), httpStatus);
    }
}