package ru.seminar.homework.hw7.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.seminar.homework.hw7.dto.CourseDto;
import ru.seminar.homework.hw7.mapper.CourseMapper;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.service.CourseService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @Autowired
    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseDto> createCourse(@RequestBody @Valid CourseDto course) {
        Course createdCourse = courseService.createCourse(courseMapper.CourseDtotoCourse(course));
        return buildResponseEntity(Objects.requireNonNull(createdCourse), HttpStatus.CREATED);
    }

    @GetMapping(produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseDto>> getAllCourses(
            @RequestParam(defaultValue = "0") @Min(0) int offset,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int limit) {
        List<Course> courses = courseService.getAllCourses(offset, limit);
        return buildResponseEntityList(courses, HttpStatus.OK);
    }

    @GetMapping(value = "/{courseId}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        return buildResponseEntity(course, HttpStatus.OK);
    }

    private ResponseEntity<CourseDto> buildResponseEntity(Course course, HttpStatus httpStatus) {
        if (course == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(courseMapper.courseToCourseDto(course), httpStatus);
    }

    private ResponseEntity<List<CourseDto>> buildResponseEntityList(List<Course> course, HttpStatus httpStatus) {
        return new ResponseEntity<>(courseMapper.courseListToCourseDtoList(course), httpStatus);
    }

}