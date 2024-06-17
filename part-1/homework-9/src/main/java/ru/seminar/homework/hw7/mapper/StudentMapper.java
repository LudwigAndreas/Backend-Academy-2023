package ru.seminar.homework.hw7.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.seminar.homework.hw7.dto.StudentDto;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.model.Student;

import java.util.List;


@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface StudentMapper {

    @AfterMapping
    default void setCourses(@MappingTarget StudentDto studentDto, Student student) {
        if (student.getCourses() != null)
            studentDto.setCourses(student.getCourses().stream().map(Course::getId).toList());
        else
            studentDto.setCourses(List.of());
    }
    @Mapping(target = "courses", ignore = true)
    Student studentDtoToStudent(StudentDto model);

    @Mapping(target = "courses", ignore = true)
    StudentDto studentToStudentDto(Student model);

    @Mapping(target = "courses", ignore = true)
    List<Student> studentDtoListToStudentList(List<StudentDto> modelList);

    @Mapping(target = "courses", ignore = true)
    List<StudentDto> studentListToStudentDtoList(List<Student> modelList);

}
