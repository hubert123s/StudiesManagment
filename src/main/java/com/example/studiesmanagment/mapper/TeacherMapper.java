package com.example.studiesmanagment.mapper;

import com.example.studiesmanagment.model.Student;
import com.example.studiesmanagment.model.Teacher;
import com.example.studiesmanagment.model.dto.StudentDto;
import com.example.studiesmanagment.model.dto.TeacherDto;
import com.example.studiesmanagment.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class TeacherMapper {
    private final StudentRepository studentRepository;
    public  TeacherDto toDto(Teacher teacher) {
        List<Long> studentsId = teacher.getStudents().stream().map(Student::getId).toList();
        return TeacherDto.builder()
                .id(teacher.getId())
                .age(teacher.getAge())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .email(teacher.getEmail())
                .subject(teacher.getSubject())
                .studentsId(studentsId)
                .build();

    }
    public  Teacher toEntity(TeacherDto teacherDto) {

        List<Student> students = new ArrayList<>();
        if(teacherDto.getStudentsId()!=null)
        {
            students=studentRepository.findAllById(teacherDto.getStudentsId()).stream().toList();
        }
        return Teacher.builder()
                .id(teacherDto.getId())
                .age(teacherDto.getAge())
                .firstName(teacherDto.getFirstName())
                .lastName(teacherDto.getLastName())
                .email(teacherDto.getEmail())
                .subject(teacherDto.getSubject())
                .students(students)
                .build();

    }

}
