package ru.seminar.homework.hw7.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.seminar.homework.hw7.dto.CourseDto;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.model.Student;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {


    @AfterMapping
    default void setStudents(@MappingTarget CourseDto courseDto, Course course) {
        if (course.getStudents() != null)
            courseDto.setStudents(course.getStudents().stream().map(Student::getId).toList());
        else
            courseDto.setStudents(List.of());
    }

    @Mapping(target = "students", ignore = true)
    CourseDto courseToCourseDto(Course model);

    @Mapping(target = "students", ignore = true)
    Course CourseDtotoCourse(CourseDto model);

    @Mapping(target = "students", ignore = true)
    List<Course> courseDtoListToCourseList(List<CourseDto> modelList);

    @Mapping(target = "students", ignore = true)
    List<CourseDto> courseListToCourseDtoList(List<Course> modelList);

}
