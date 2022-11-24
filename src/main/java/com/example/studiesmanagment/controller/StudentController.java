package com.example.studiesmanagment.controller;

import com.example.studiesmanagment.exception.ResourceNotFoundException;
import com.example.studiesmanagment.model.dto.StudentDto;
import com.example.studiesmanagment.model.dto.TeacherDto;
import com.example.studiesmanagment.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    List<StudentDto> findAllStudents(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                    @RequestParam(defaultValue = "2", required = false) int pageSize,
                                    @RequestParam(defaultValue = "id", required = false) String sortBy,
                                    @RequestParam(defaultValue = "ASC", required = false) String sortDirection) {
        return studentService.findAll(pageNumber, pageSize, sortBy, sortDirection);
    }

    @PostMapping
    StudentDto saveStudent(@RequestBody StudentDto studentDto) {
        return studentService.saveStudent(studentDto);
    }

    @DeleteMapping("/{id}")
    void deleteStudent(@PathVariable Long id) throws ResourceNotFoundException {
        studentService.deleteStudentById(id);
    }

    @PutMapping("/{id}")
    StudentDto updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) throws ResourceNotFoundException {
        return studentService.updateStudentById(id, studentDto);
    }

    @GetMapping("/{studentid}/{teacherid}")
    void deleteTeacherByStudent(@PathVariable Long studentid,
                                @PathVariable Long teacherid) throws ResourceNotFoundException {
        studentService.deleteTeacherByStudent(studentid, teacherid);
    }

    @GetMapping("/{studentId}")
    List<TeacherDto> findTeachersByStudentId(@PathVariable Long studentId) throws ResourceNotFoundException {
        return studentService.findTeachersByStudentId(studentId);
    }
    @GetMapping("/firstname")
    List<StudentDto> findByFirstName(@RequestParam String firstName){

        return studentService.findAllByFirstName(firstName);
    }
    @GetMapping("/lastname")
    List<StudentDto> findByLastName(@RequestParam String lastName){

        return studentService.findAllByLastName(lastName);
    }
}
