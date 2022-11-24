package com.example.studiesmanagment.service;

import com.example.studiesmanagment.exception.ResourceNotFoundException;
import com.example.studiesmanagment.mapper.StudentMapper;
import com.example.studiesmanagment.mapper.TeacherMapper;
import com.example.studiesmanagment.model.Student;
import com.example.studiesmanagment.model.Teacher;
import com.example.studiesmanagment.model.dto.StudentDto;
import com.example.studiesmanagment.model.dto.TeacherDto;
import com.example.studiesmanagment.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherService {
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;
    private final TeacherRepository teacherRepository;

    public TeacherDto saveTeacher(TeacherDto teacherDto) {
        Teacher student = teacherMapper.toEntity(teacherDto);
        Teacher savedStudent = teacherRepository.save(student);
        return teacherMapper.toDto(savedStudent);
    }

    public void deleteTeacherById(Long id) throws ResourceNotFoundException {
        if (!teacherRepository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        teacherRepository.deleteById(id);
    }

    public TeacherDto updateTeacherById(Long teacherId, TeacherDto teacherDto) throws ResourceNotFoundException {
        if (!teacherRepository.existsById(teacherId)) {
            throw new ResourceNotFoundException(teacherId);
        }
        Teacher teacher = teacherMapper.toEntity(teacherDto);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(savedTeacher);
    }

    public List<TeacherDto> findAllByFirstName(String firstName) {
        return teacherRepository
                .findAllByFirstName(firstName)
                .stream()
                .map(teacherMapper::toDto)
                .toList();
    }

    public List<TeacherDto> findAllByLastName(String lastName) {
        return teacherRepository
                .findAllByLastName(lastName)
                .stream()
                .map(teacherMapper::toDto)
                .toList();
    }

    public List<StudentDto> findStudentsByTeacherId(Long teacherId) throws ResourceNotFoundException {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException(teacherId))
                .getStudents()
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    public void deleteStudentByTeacher(Long teacherId, Long studentId) throws ResourceNotFoundException {
        Teacher teacher = teacherRepository
                .findById(teacherId).orElseThrow(() -> new ResourceNotFoundException(teacherId));
        List<Student> students = teacher.getStudents();
        Student student = students.stream().
                filter(t -> t.getId().equals(studentId)).findFirst().orElseThrow(() -> new ResourceNotFoundException(studentId));
        students.remove(student);
        teacher.builder().students(students);
        updateTeacherById(teacherId, teacherMapper.toDto(teacher));
    }

    public List<TeacherDto> findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Teacher> pagedTeacher = teacherRepository.findAll(pageable);
        return pagedTeacher.map(student -> teacherMapper.toDto(student))
                .stream()
                .toList();
    }
}
