package com.example.studiesmanagment.service;

import com.example.studiesmanagment.exception.ResourceNotFoundException;
import com.example.studiesmanagment.mapper.StudentMapper;
import com.example.studiesmanagment.mapper.TeacherMapper;
import com.example.studiesmanagment.model.Student;
import com.example.studiesmanagment.model.Teacher;
import com.example.studiesmanagment.model.dto.StudentDto;
import com.example.studiesmanagment.model.dto.TeacherDto;
import com.example.studiesmanagment.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;


@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;

    public StudentDto saveStudent(StudentDto studentDto) {
        Student student =studentMapper.toEntity(studentDto);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDto(savedStudent);
    }
    public void deleteStudentById(Long id) throws ResourceNotFoundException {
        if(!studentRepository.existsById(id))
        {
            throw new ResourceNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }
    public StudentDto updateStudentById(Long studentId,StudentDto studentDto) throws ResourceNotFoundException {
        if(!studentRepository.existsById(studentId))
        {
            throw new ResourceNotFoundException(studentId);
        }
        Student student =studentMapper.toEntity(studentDto);
        Student savedStudent= studentRepository.save(student);
        return studentMapper.toDto(savedStudent);
    }
    public List<StudentDto> findAllByFirstName(String firstName){
       return studentRepository
               .findAllByFirstName(firstName)
               .stream()
               .map(studentMapper::toDto)
               .toList();
    }
    public List<StudentDto> findAllByLastName(String lastName){
        return studentRepository
                .findAllByLastName(lastName)
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }
    public List<TeacherDto> findTeachersByStudentId(Long studentId) throws ResourceNotFoundException {
        return studentRepository.findById(studentId)
                .orElseThrow(()->new ResourceNotFoundException(studentId))
                .getTeachers()
                .stream()
                .map(teacherMapper::toDto)
                .toList();
    }

    public void deleteTeacherByStudent(Long studentId,Long teacherId) throws ResourceNotFoundException {
        Student student = studentRepository
                .findById(studentId).orElseThrow(() -> new ResourceNotFoundException(studentId));
        List<Teacher> teachers = student.getTeachers();
        Teacher teacher=teachers.stream().
                filter(t->t.getId().equals(teacherId)).findFirst().orElseThrow(()->new ResourceNotFoundException(teacherId));
         teachers.remove(teacher);
        student.builder().teachers(teachers);
        updateStudentById(studentId,studentMapper.toDto(student));
    }
    public List<StudentDto> findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Student> pagedStudents = studentRepository.findAll(pageable);
        return pagedStudents.map(student -> studentMapper.toDto(student))
                .stream()
                .toList();
    }

}

