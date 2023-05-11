package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository,
            StudentIdCardRepository studentIdCardRepository,
            BookRepository bookRepository) {
        return args -> {

            Student maria = new Student("Maria", "Ivanova", "maria@gmail.com", 35);
            StudentIdCard studentIdCard = new StudentIdCard("1234567890", maria);
            Book book = new Book("Idiot", LocalDateTime.now());
            Book book1 = new Book("We", LocalDateTime.now());

            maria.addBook(book);
            maria.addBook(book1);

            maria.setStudentIdCard(studentIdCard);

            /*maria.addCourse(new Course("Banking", "Economy"));
            maria.addCourse(new Course("java", "IT"));
            maria.addCourse(new Course("Painting", "Art"));*/

            maria.addEnrolment(new Enrolment(new EnrolmentId(1L,1L),maria, new Course("Banking", "Economy"), LocalDateTime.now()));
            maria.addEnrolment(new Enrolment(new EnrolmentId(1L,2L),maria, new Course("java", "IT"), LocalDateTime.now()));
            maria.addEnrolment(new Enrolment(new EnrolmentId(1L,3L),maria, new Course("Painting", "Art"), LocalDateTime.now()));


            studentRepository.save(maria);

            //generateRandomStudent(studentRepository);


            /*Student maria = new Student("Maria", "Ivanova", "maria@gmail.com", 35);
            Student anna = new Student("Anna", "Petrova", "anna@gmail.com", 21);
            studentRepository.save(maria);
            studentRepository.save(anna);

            studentRepository.findStudentByEmail("maria@gmail.com").ifPresentOrElse(
                    System.out::println, () -> System.out.println("Not found"));

            studentRepository.customSelect("Maria", 21).forEach(System.out::println);

            studentRepository.deleteStudentById(2L);*/

        };
    }

    private void paging(StudentRepository studentRepository) {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("firstName").ascending());
        Page<Student> page = studentRepository.findAll(pageRequest);
    }

    private void sortingStudents(StudentRepository studentRepository) {
        studentRepository.findAll(Sort.by("firstName").ascending()).
                forEach(student -> System.out.println(student.getFirstName()));
    }

    private void generateRandomStudent(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i <20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            Student student = new Student(firstName, lastName, email, faker.number().numberBetween(18, 35));
            studentRepository.save(student);
        }
    }
}
