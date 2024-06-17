package ru.seminar.homework.hw7.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.seminar.homework.hw7.dto.CourseDto;
import ru.seminar.homework.hw7.model.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CourseMapperTest {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void courseDtoToCourse_whenValidInput_thenReturnsCourse() {
        CourseDto courseDto = new CourseDto(1L, "Java", "Java course", 130, null);
        Course course = courseMapper.CourseDtotoCourse(courseDto);
        assertEquals(courseDto.getId(), course.getId());
        assertEquals(courseDto.getName(), course.getName());
        assertEquals(courseDto.getDescription(), course.getDescription());
        assertEquals(courseDto.getDuration(), course.getDuration());
        assertEquals(List.of(), course.getStudents());
    }

    @Test
    public void courseToCourseDto_whenValidInput_thenReturnsCourseDto() {
        Course course = new Course(1L, "Java", "Java course", 130, null);
        CourseDto courseDto = courseMapper.courseToCourseDto(course);
        assertEquals(course.getId(), courseDto.getId());
        assertEquals(course.getName(), courseDto.getName());
        assertEquals(course.getDescription(), courseDto.getDescription());
        assertEquals(course.getDuration(), courseDto.getDuration());
        assertEquals(List.of(), courseDto.getStudents());
    }

    @Test
    public void courseDtoListToCourseList_whenValidInput_thenReturnsCourseList() {
        List<CourseDto> courseDtos = List.of(
                new CourseDto(1L, "Java", "Java course", 130, null),
                new CourseDto(2L, "Python", "Python course", 100, null),
                new CourseDto(3L, "C++", "C++ course", 140, null)
        );
        List<Course> courses = courseMapper.courseDtoListToCourseList(courseDtos);
        assertEquals(courseDtos.get(0).getId(), courses.get(0).getId());
        assertEquals(courseDtos.get(0).getName(), courses.get(0).getName());
        assertEquals(courseDtos.get(0).getDescription(), courses.get(0).getDescription());
        assertEquals(courseDtos.get(0).getDuration(), courses.get(0).getDuration());
        assertEquals(List.of(), courses.get(0).getStudents());
        assertEquals(courseDtos.get(1).getId(), courses.get(1).getId());
        assertEquals(courseDtos.get(1).getName(), courses.get(1).getName());
        assertEquals(courseDtos.get(1).getDescription(), courses.get(1).getDescription());
        assertEquals(courseDtos.get(1).getDuration(), courses.get(1).getDuration());
        assertEquals(List.of(), courses.get(1).getStudents());
        assertEquals(courseDtos.get(2).getId(), courses.get(2).getId());
        assertEquals(courseDtos.get(2).getName(), courses.get(2).getName());
        assertEquals(courseDtos.get(2).getDescription(), courses.get(2).getDescription());
        assertEquals(courseDtos.get(2).getDuration(), courses.get(2).getDuration());
        assertEquals(List.of(), courses.get(2).getStudents());
    }

    @Test
    public void courseListToCourseDtoList_whenValidInput_thenReturnsCourseDtoList() {
        List<Course> courses = List.of(
                new Course(1L, "Java", "Java course", 130, null),
                new Course(2L, "Python", "Python course", 100, null),
                new Course(3L, "C++", "C++ course", 140, null)
        );
        List<CourseDto> courseDtos = courseMapper.courseListToCourseDtoList(courses);
        assertEquals(courses.get(0).getId(), courseDtos.get(0).getId());
        assertEquals(courses.get(0).getName(), courseDtos.get(0).getName());
        assertEquals(courses.get(0).getDescription(), courseDtos.get(0).getDescription());
        assertEquals(courses.get(0).getDuration(), courseDtos.get(0).getDuration());
        assertEquals(List.of(), courseDtos.get(0).getStudents());
        assertEquals(courses.get(1).getId(), courseDtos.get(1).getId());
        assertEquals(courses.get(1).getName(), courseDtos.get(1).getName());
        assertEquals(courses.get(1).getDescription(), courseDtos.get(1).getDescription());
        assertEquals(courses.get(1).getDuration(), courseDtos.get(1).getDuration());
        assertEquals(List.of(), courseDtos.get(1).getStudents());
        assertEquals(courses.get(2).getId(), courseDtos.get(2).getId());
        assertEquals(courses.get(2).getName(), courseDtos.get(2).getName());
        assertEquals(courses.get(2).getDescription(), courseDtos.get(2).getDescription());
        assertEquals(courses.get(2).getDuration(), courseDtos.get(2).getDuration());
        assertEquals(List.of(), courseDtos.get(2).getStudents());
    }
}
