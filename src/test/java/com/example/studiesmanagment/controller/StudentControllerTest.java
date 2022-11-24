package com.example.studiesmanagment.controller;

import com.example.studiesmanagment.mapper.StudentMapper;
import com.example.studiesmanagment.model.Major;
import com.example.studiesmanagment.model.Student;
import com.example.studiesmanagment.model.Teacher;
import com.example.studiesmanagment.model.dto.StudentDto;
import com.example.studiesmanagment.repository.StudentRepository;
import com.example.studiesmanagment.repository.TeacherRepository;
import com.example.studiesmanagment.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StudentControllerTest {
    @Autowired
    StudentService studentService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    TeacherRepository teacherRepository;
    @Test
    void shouldSaveStudent() throws Exception {
        StudentDto studentDto = StudentDto.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .age(25)
                .email("123@gmail.com")
                .major(Major.ELECTROTECHNICS)
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(studentDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().is(200)).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        StudentDto responseStudent = objectMapper.readValue(contentAsString, StudentDto.class);
        assertEquals(studentDto.getFirstName(), responseStudent.getFirstName());
        assertEquals(studentDto.getLastName(), responseStudent.getLastName());
        assertEquals(studentDto.getAge(), responseStudent.getAge());
        assertEquals(studentDto.getMajor(), responseStudent.getMajor());
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        Student student = studentRepository.save(Student.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .age(25)
                .email("123@gmail.com")
                .major(Major.ELECTROTECHNICS)
                .build());
        mockMvc.perform(delete("/student/" + student.getId()))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.name").doesNotExist())
                .andExpect(jsonPath("$.preperationTime").doesNotExist())
                .andExpect(jsonPath("$.description").doesNotExist())
                .andExpect(jsonPath("$.typeMeal").doesNotExist());
    }

    @Test
    void shouldReplaceStudent() throws Exception {
        Student student = studentRepository.save(Student.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .age(25)
                .email("123@gmail.com")
                .major(Major.ELECTROTECHNICS)
                .build());
        StudentDto studentDto = StudentDto.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .age(20)
                .email("1233456@gmail.com")
                .major(Major.ELECTROTECHNICS)
                .build();
        MvcResult mvcResult = mockMvc.perform(put("/student/" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(studentDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        StudentDto responseStudent = objectMapper.readValue(contentAsString, StudentDto.class);
        assertEquals(studentDto.getFirstName(), responseStudent.getFirstName());
        assertEquals(studentDto.getLastName(), responseStudent.getLastName());
        assertEquals(studentDto.getAge(), responseStudent.getAge());
        assertEquals(studentDto.getMajor(), responseStudent.getMajor());
    }

    @Test
    void shouldFindByFirstName() throws Exception {
        studentRepository.save(Student.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .age(25)
                .email("123@gmail.com")
                .major(Major.ELECTROTECHNICS)
                .teachers(List.of())
                .build());
        mockMvc.perform(get("/student/firstname")
                        .queryParam("firstName", "Jan"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].firstName").value("Jan"))
                .andReturn();

    }

    @Test
    void shouldFindByLastName() throws Exception {
        studentRepository.save(Student.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .age(25)
                .email("123@gmail.com")
                .major(Major.ELECTROTECHNICS)
                .teachers(List.of())
                .build());
        mockMvc.perform(get("/student/lastname")
                        .queryParam("lastName", "Nowak"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].lastName").value("Nowak"))
                .andReturn();

    }

    @Test
    void shouldFindTeacherByStudentId() throws Exception {
        Teacher teacher = teacherRepository.save(Teacher.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .age(30)
                .email("123@gmail.com")
                .students(List.of(new Student()))
                .build());

        Student student = studentRepository.save(Student.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .age(25)
                .email("123@gmail.com")
                .major(Major.ELECTROTECHNICS)
                .teachers(List.of(teacher))
                .build());
        System.out.println(student);

        mockMvc.perform(get("/student/" + student.getId()))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].lastName").value("Kowalski"))
                .andReturn();

    }

    @Test
    void shouldFindAll() throws Exception {
        studentRepository.save(Student.builder()
                .firstName("Jan")
                .lastName("Nowak")
                .age(25)
                .email("123@gmail.com")
                .major(Major.ELECTROTECHNICS)
                .teachers(List.of(new Teacher()))
                .build());
        mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].lastName").value("Nowak"))
                .andReturn();
    }
}