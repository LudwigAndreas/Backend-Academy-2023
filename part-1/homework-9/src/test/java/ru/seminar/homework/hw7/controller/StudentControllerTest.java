package ru.seminar.homework.hw7.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.seminar.homework.hw7.dto.StudentDto;
import ru.seminar.homework.hw7.exception.NotFoundException;
import ru.seminar.homework.hw7.mapper.StudentMapper;
import ru.seminar.homework.hw7.service.StudentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentMapper studentMapper;

    @MockBean
    private StudentService studentService;

    List<StudentDto> studentDtos = List.of(
            new StudentDto(1L, "Username", "Ivan", "Ivanov", 1, List.of()),
            new StudentDto(2L, "Username2", "Petr", "Petrov", 2, List.of()),
            new StudentDto(3L, "Username3", "Sidor", "Sidorov", 3, List.of())
    );

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void createStudent_whenValidInput_thenReturns201() throws Exception {
        StudentDto student = new StudentDto(null, "Username", "Ivan", "Ivanov", 1, null);
        StudentDto expectedStudent = new StudentDto(1L, "Username", "Ivan", "Ivanov", 1, List.of());
        when(studentService.createStudent(studentMapper.studentDtoToStudent(student))).thenReturn(studentMapper.studentDtoToStudent(expectedStudent));

        MvcResult result = mockMvc.perform(post("/students")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.login").value("Username"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.age").value(1))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertEquals(jsonResponse, asJsonString(expectedStudent));
    }


    @Test
    public void getAllStudents_whenValidInput_thenReturns200() throws Exception {
        when(studentService.getAllStudents(0, 10)).thenReturn(studentMapper.studentDtoListToStudentList(studentDtos));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/students")
                .param("offset", "0")
                .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].login").value("Username"))
                .andExpect(jsonPath("$[0].firstName").value("Ivan"))
                .andExpect(jsonPath("$[0].lastName").value("Ivanov"))
                .andExpect(jsonPath("$[0].age").value(1))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].login").value("Username2"))
                .andExpect(jsonPath("$[1].firstName").value("Petr"))
                .andExpect(jsonPath("$[1].lastName").value("Petrov"))
                .andExpect(jsonPath("$[1].age").value(2))
                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].login").value("Username3"))
                .andExpect(jsonPath("$[2].firstName").value("Sidor"))
                .andExpect(jsonPath("$[2].lastName").value("Sidorov"))
                .andExpect(jsonPath("$[2].age").value(3))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertEquals(jsonResponse, asJsonString(studentDtos));
    }

    @Test
    public void getStudentById_whenValidInput_thenReturns200() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(studentMapper.studentDtoToStudent(studentDtos.get(0)));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.login").value("Username"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.age").value(1))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertEquals(jsonResponse, asJsonString(studentDtos.get(0)));
    }

    @Test
    public void getStudentById_whenInvalidInput_thenReturns404() throws Exception {
        when(studentService.getStudentById(1L)).thenThrow(new NotFoundException("Student not found"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void addStudentToCourse_whenValidInput_thenReturns200() throws Exception {
        when(studentService.addStudentToCourse(1L, 1L)).thenReturn(studentMapper.studentDtoToStudent(studentDtos.get(0)));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/students/1/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.login").value("Username"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.age").value(1))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertEquals(jsonResponse, asJsonString(studentDtos.get(0)));
    }

    @Test
    public void updateStudent_whenValidInput_thenReturns200() throws Exception {
        StudentDto student = new StudentDto(null, "Username", "Ivan", "Ivanov", 1, null);
        StudentDto expectedStudent = new StudentDto(1L, "Username", "Ivan", "Ivanov", 1, List.of());
        when(studentService.updateStudent(1L, studentMapper.studentDtoToStudent(student))).thenReturn(studentMapper.studentDtoToStudent(expectedStudent));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/students/1")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.login").value("Username"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.age").value(1))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        assertEquals(jsonResponse, asJsonString(expectedStudent));
    }

    @Test
    public void deleteStudent_whenValidInput_thenReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
