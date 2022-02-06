package com.spring.jpa;

import com.github.javafaker.Faker;
import com.spring.jpa.entity.Book;
import com.spring.jpa.entity.Student;
import com.spring.jpa.entity.StudentIdCard;
import com.spring.jpa.repo.StudentIdCardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.spring.jpa.repo.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository){
		return args -> {
			Faker faker = new Faker();
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@gmail.com", firstName, lastName);
			Student student = new Student(
					firstName,
					lastName,
					email,
					faker.number().numberBetween(16, 62));

			student.addBook(new Book("Clean Code", LocalDateTime.now()));

			student.addBook(new Book("Think and Grow Rich", LocalDateTime.now().minusDays(4)));

			student.addBook(new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

			StudentIdCard studentIdCard= new StudentIdCard(
					"123456789",
					student);

			studentIdCardRepository.save(studentIdCard);

			studentRepository.findById(1L).ifPresent(s -> {
				System.out.println("fetch book lazy..");
				List<Book> books = student.getBooks();
				books.forEach(book -> {
					System.out.println(s.getFirstName() + " borrowed " + book.getBookName());
				});
			});

			studentIdCardRepository.findById(1L).ifPresent(System.out::println);

			studentRepository.deleteById(1L);
			//generateRandomStudent(studentRepository);
			//SortByNameAndAge(studentRepository);

			/*PageRequest pageRequest = PageRequest.of(0,5, Sort.by("firstName").ascending());
			Page<Student> page = studentRepository.findAll(pageRequest);
			System.out.println(page);*/

		};

			/*
			// Without using Faker:
			Student maria = new Student(
					"Maria", "Mutawe", "Maria.Mutawe@gmail.com", 16
			);

			Student taku = new Student(
					"Taku", "Mutawe", "Taku.Mutawe@gmail.com", 20
			);
			Student takuJohns = new Student(
					"Taku", "Johns", "Taku.Johns@gmail.com", 25
			);

			Student mariaJohns = new Student(
					"Maria", "Johns", "Maria.Johns@gmail.com", 28
			);
			studentRepository.saveAll(List.of(maria,taku,takuJohns,mariaJohns));

			System.out.println("Students number: "+studentRepository.count());

			studentRepository.findById(2L).ifPresentOrElse(System.out::println, () -> {
				System.out.println("Student with id 2 not found");
			});

			studentRepository.findById(3L).ifPresentOrElse(System.out::println, () -> {
				System.out.println("Student with id 3 not found");
			});

			List<Student> students = studentRepository.findAll();
			students.forEach(System.out::println);

			System.out.println("find student by id:- ");
			studentRepository.findStudentByEmail("Maria.Mutawe@gmail.com").ifPresentOrElse(System.out::println, () -> System.out.println("Student Not Found"));

			studentRepository.findStudentsByFirstNameEqualsAndAgeGreaterThan(
					"Taku",
					19
			).forEach(System.out::println);

			studentRepository.findStudentsByFirstNameEqualsAndAgeGreaterThanEquals(
					"Maria",
					16
			).forEach(System.out::println);

			studentRepository.findStudentsByFirstNameEqualsAndAgeGreaterThanEqualsNative(
					"Maria",
					16
			).forEach(System.out::println);

			studentRepository.findStudentsByFirstNameEqualsAndAgeGreaterThanEqualsNativeWithNamedParam(
					"Maria",
					16
			).forEach(System.out::println);

			System.out.println("Deleting Maria 2");
			System.out.println(studentRepository.CustomDeleteStudentById(4L));
			*/

	}

	/*private void SortByNameAndAge(StudentRepository studentRepository) {
		Sort sort = Sort.by("firstName").ascending().and(Sort.by("age").descending());
		studentRepository.findAll(sort).forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
	}*/

	//Saving random students using Faker:
	private void generateRandomStudent(StudentRepository studentRepository) {
		Faker faker = new Faker();
		for(int i=0; i<20; i++){
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@gmail.com", firstName, lastName);
			Student student = new Student(
					firstName,
					lastName,
					email,
					faker.number().numberBetween(16, 62));
			studentRepository.save(student);
		}
	}
}
