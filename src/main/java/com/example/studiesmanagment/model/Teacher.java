package com.example.studiesmanagment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min=2,message = "First name must have at least 2 characters")
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Min(value = 19, message = "Age must be at least 19")
    private int age;
    @Email(message = "Email is invalid")
    private String email;
    private Subject subject;
    @ManyToMany
    @JoinTable(name = "student_teacher",
    joinColumns = @JoinColumn(name = "teacher_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"))
    private List<Student> students ;

}
