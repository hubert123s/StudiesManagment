package com.example.studiesmanagment.controller;

import com.example.studiesmanagment.exception.ResourceNotFoundException;
import com.example.studiesmanagment.model.dto.StudentDto;
import com.example.studiesmanagment.model.dto.TeacherDto;
import com.example.studiesmanagment.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    TeacherDto saveTeacher(@RequestBody TeacherDto teacherDto) {
        return teacherService.saveTeacher(teacherDto);
    }

    @DeleteMapping("/{id}")
    void deleteTeacher(@PathVariable Long id) throws ResourceNotFoundException {
        teacherService.deleteTeacherById(id);
    }

    @PutMapping("/{id}")
    TeacherDto updateTeacher(@PathVariable Long id, @RequestBody TeacherDto teacherDto) throws ResourceNotFoundException {
        return teacherService.updateTeacherById(id, teacherDto);
    }
    @GetMapping("/{teacherid}/{studentid}")
    void deleteStudentByTeacher(@PathVariable Long studentid,
                                @PathVariable Long teacherid) throws ResourceNotFoundException {
        teacherService.deleteStudentByTeacher(teacherid,studentid);
    }
    @GetMapping("/{teacherId}")
    List<StudentDto> findStudentsByTeacherId(@PathVariable Long teacherId) throws ResourceNotFoundException {
        return teacherService.findStudentsByTeacherId(teacherId);
    }
    @GetMapping("/firstname")
    List<TeacherDto> findByFirstName(@RequestParam String firstName){

        return teacherService.findAllByFirstName(firstName);
    }
    @GetMapping("/lastname")
    List<TeacherDto> findByLastName(@RequestParam String lastName){

        return teacherService.findAllByLastName(lastName);
    }
    @GetMapping
    List<TeacherDto> findAllTeachers(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                 @RequestParam(defaultValue = "2", required = false) int pageSize,
                                 @RequestParam(defaultValue = "id", required = false) String sortBy,
                                 @RequestParam(defaultValue = "ASC", required = false) String sortDirection) {
        return teacherService.findAll(pageNumber, pageSize, sortBy, sortDirection);
    }
}
