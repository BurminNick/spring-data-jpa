package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "select * from student join book b on student.id = b.student_id join student_id_card sic on student.id = sic.student_id",
    nativeQuery = true)
    List<Student> findAllCustom();

    Optional<Student> findStudentByEmail(String email);

    @Query(value = "select * from student where first_name=?1 and age>=?2", nativeQuery = true)
    List<Student> customSelect(String firstName, Integer age);

    @Modifying
    @Transactional
    void deleteStudentById(Long id);
}
