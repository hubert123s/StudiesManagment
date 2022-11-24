package com.example.studiesmanagment.mapper;

import com.example.studiesmanagment.model.Student;
import com.example.studiesmanagment.model.Teacher;
import com.example.studiesmanagment.model.dto.StudentDto;
import com.example.studiesmanagment.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentMapper {
    private final TeacherRepository teacherRepository;

    public Student toEntity(StudentDto studentDto) {
        List<Teacher> teachers = new ArrayList<>();
        if (studentDto.getTeachersId() != null) {
            teachers = teacherRepository.findAllById(studentDto.getTeachersId()).stream().toList();
        }
        return Student.builder()
                .id(studentDto.getId())
                .age(studentDto.getAge())
                .email(studentDto.getEmail())
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .teachers(teachers)
                .major(studentDto.getMajor())
                .build();
    }

    public StudentDto toDto(Student student) {
        List<Long> teachersId = student.getTeachers().stream().map(Teacher::getId).toList();
        return StudentDto.builder()
                .id(student.getId())
                .age(student.getAge())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .teachersId(teachersId)
                .email(student.getEmail())
                .major(student.getMajor())
                .build();

    }
}
