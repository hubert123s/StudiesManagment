package com.example.studiesmanagment.model.dto;

import com.example.studiesmanagment.model.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private Major major;
    private List<Long> teachersId;
}
