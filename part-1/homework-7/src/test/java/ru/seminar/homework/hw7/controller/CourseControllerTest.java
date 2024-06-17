package ru.seminar.homework.hw7.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.seminar.homework.hw7.dto.CourseDto;
import ru.seminar.homework.hw7.exception.NotFoundException;
import ru.seminar.homework.hw7.mapper.CourseMapper;
import ru.seminar.homework.hw7.model.Course;
import ru.seminar.homework.hw7.service.CourseService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseMapper courseMapper;

    @MockBean
    private CourseService courseService;

    List<CourseDto> courses = List.of(
            new CourseDto(1L, "Java", "Java course", 130, List.of()),
            new CourseDto(2L, "Python", "Python course", 100, List.of()),
            new CourseDto(3L, "C++", "C++ course", 140, List.of())
    );

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void createCourse_whenValidInput_thenReturns201() throws Exception {
        CourseDto course = new CourseDto(null, "Java", "Java course", 130, null);
        CourseDto expectedCourse = new CourseDto(1L, "Java", "Java course", 130, List.of());
        when(courseService.createCourse(courseMapper.CourseDtotoCourse(course))).thenReturn(courseMapper.CourseDtotoCourse(expectedCourse));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                .content(asJsonString(course))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Java"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertEquals(jsonResponse, asJsonString(expectedCourse));
    }

    @Test
    public void createCourse_whenInvalidInput_thenReturns400() throws Exception {
        CourseDto course = new CourseDto(null, "J", "Java course", 130, null);
        CourseDto expectedCourse = new CourseDto(1L, "Java", "Java course", 130, List.of());
        when(courseService.createCourse(courseMapper.CourseDtotoCourse(course))).thenReturn(courseMapper.CourseDtotoCourse(expectedCourse));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                .content(asJsonString(course))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getAllCourses_whenValidInput_thenReturns200() throws Exception {
        when(courseService.getAllCourses(0, 2)).thenReturn(courseMapper.courseDtoListToCourseList(courses.subList(0, 2)));

        MvcResult result = mockMvc.perform(get("/courses")
                .param("offset", "0")
                .param("limit", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Python"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertEquals(jsonResponse, asJsonString(courses.subList(0, 2)));
    }

//    @Test
//    public void getAllCourses_whenInvalidInput_thenReturns400() throws Exception {
//        when(courseService.getAllCourses(0, 2)).thenReturn(courseMapper.courseDtoListToCourseList(courses.subList(0, 2)));
//
//        MvcResult result = mockMvc.perform(get("/courses")
//                .param("offset", "-1")
//                .param("limit", "500")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//    }

    @Test
    public void getCourseById_whenValidInput_thenReturns200() throws Exception {
        when(courseService.getCourseById(1L)).thenReturn(courseMapper.CourseDtotoCourse(courses.get(0)));

        MvcResult result = mockMvc.perform(get("/courses/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Java"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertEquals(jsonResponse, asJsonString(courses.get(0)));
    }

    @Test
    public void getCourseById_whenInvalidInput_thenReturns404() throws Exception {
        when(courseService.getCourseById(1L)).thenThrow(new NotFoundException("Course not found"));

        MvcResult result = mockMvc.perform(get("/courses/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
