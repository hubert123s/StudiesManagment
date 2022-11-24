package com.example.studiesmanagment.model.dto;

import com.example.studiesmanagment.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDto {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private Subject subject;
    private List<Long> studentsId;
}
