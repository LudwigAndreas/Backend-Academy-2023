package ru.seminar.homework.hw7.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.seminar.homework.hw7.dto.StudentDto;
import ru.seminar.homework.hw7.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void studentDtoToStudent_whenValidInput_thenReturnsStudent() {
        StudentDto studentDto = new StudentDto(1L, "Username", "Ivan", "Ivanov", 1, null);
        Student student = studentMapper.studentDtoToStudent(studentDto);
        assertEquals(studentDto.getId(), student.getId());
        assertEquals(studentDto.getLogin(), student.getLogin());
        assertEquals(studentDto.getFirstName(), student.getFirstName());
        assertEquals(studentDto.getLastName(), student.getLastName());
        assertEquals(studentDto.getAge(), student.getAge());
        assertEquals(List.of(), student.getCourses());
    }

    @Test
    public void studentToStudentDto_whenValidInput_thenReturnsStudentDto() {
        Student student = new Student(1L, "Username", "Ivan", "Ivanov", 1, null);
        StudentDto studentDto = studentMapper.studentToStudentDto(student);
        assertEquals(student.getId(), studentDto.getId());
        assertEquals(student.getLogin(), studentDto.getLogin());
        assertEquals(student.getFirstName(), studentDto.getFirstName());
        assertEquals(student.getLastName(), studentDto.getLastName());
        assertEquals(student.getAge(), studentDto.getAge());
        assertEquals(List.of(), studentDto.getCourses());
    }

    @Test
    public void studentDtoListToStudentList_whenValidInput_thenReturnsStudentList() {
        List<StudentDto> studentDtos = List.of(
                new StudentDto(1L, "Username", "Ivan", "Ivanov", 1, null),
                new StudentDto(2L, "Username2", "Petr", "Petrov", 2, null),
                new StudentDto(3L, "Username3", "Sidor", "Sidorov", 3, null)
        );
        List<Student> students = studentMapper.studentDtoListToStudentList(studentDtos);
        assertEquals(studentDtos.get(0).getId(), students.get(0).getId());
        assertEquals(studentDtos.get(0).getLogin(), students.get(0).getLogin());
        assertEquals(studentDtos.get(0).getFirstName(), students.get(0).getFirstName());
        assertEquals(studentDtos.get(0).getLastName(), students.get(0).getLastName());
        assertEquals(studentDtos.get(0).getAge(), students.get(0).getAge());
        assertEquals(List.of(), students.get(0).getCourses());
        assertEquals(studentDtos.get(1).getId(), students.get(1).getId());
        assertEquals(studentDtos.get(1).getLogin(), students.get(1).getLogin());
        assertEquals(studentDtos.get(1).getFirstName(), students.get(1).getFirstName());
        assertEquals(studentDtos.get(1).getLastName(), students.get(1).getLastName());
        assertEquals(studentDtos.get(1).getAge(), students.get(1).getAge());
        assertEquals(List.of(), students.get(1).getCourses());
        assertEquals(studentDtos.get(2).getId(), students.get(2).getId());
        assertEquals(studentDtos.get(2).getLogin(), students.get(2).getLogin());
        assertEquals(studentDtos.get(2).getFirstName(), students.get(2).getFirstName());
        assertEquals(studentDtos.get(2).getLastName(), students.get(2).getLastName());
        assertEquals(studentDtos.get(2).getAge(), students.get(2).getAge());
        assertEquals(List.of(), students.get(2).getCourses());
    }

    @Test
    public void studentListToStudentDtoList_whenValidInput_thenReturnsStudentDtoList() {
        List<Student> students = List.of(
                new Student(1L, "Username", "Ivan", "Ivanov", 1, null),
                new Student(2L, "Username2", "Petr", "Petrov", 2, null),
                new Student(3L, "Username3", "Sidor", "Sidorov", 3, null)
        );
        List<StudentDto> studentDtos = studentMapper.studentListToStudentDtoList(students);
        assertEquals(students.get(0).getId(), studentDtos.get(0).getId());
        assertEquals(students.get(0).getLogin(), studentDtos.get(0).getLogin());
        assertEquals(students.get(0).getFirstName(), studentDtos.get(0).getFirstName());
        assertEquals(students.get(0).getLastName(), studentDtos.get(0).getLastName());
        assertEquals(students.get(0).getAge(), studentDtos.get(0).getAge());
        assertEquals(List.of(), studentDtos.get(0).getCourses());
        assertEquals(students.get(1).getId(), studentDtos.get(1).getId());
        assertEquals(students.get(1).getLogin(), studentDtos.get(1).getLogin());
        assertEquals(students.get(1).getFirstName(), studentDtos.get(1).getFirstName());
        assertEquals(students.get(1).getLastName(), studentDtos.get(1).getLastName());
        assertEquals(students.get(1).getAge(), studentDtos.get(1).getAge());
        assertEquals(List.of(), studentDtos.get(1).getCourses());
        assertEquals(students.get(2).getId(), studentDtos.get(2).getId());
        assertEquals(students.get(2).getLogin(), studentDtos.get(2).getLogin());
        assertEquals(students.get(2).getFirstName(), studentDtos.get(2).getFirstName());
        assertEquals(students.get(2).getLastName(), studentDtos.get(2).getLastName());
        assertEquals(students.get(2).getAge(), studentDtos.get(2).getAge());
        assertEquals(List.of(), studentDtos.get(2).getCourses());
    }


}
