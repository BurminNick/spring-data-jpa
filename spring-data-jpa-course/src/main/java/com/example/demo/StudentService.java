package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDTO> findAllStudents() {

        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOList =
                students.stream().
                        map(s -> new StudentDTO(s.getId(), s.getFirstName() + " " + s.getLastName())).
                        collect(Collectors.toList());
        return studentDTOList;
    }
}
