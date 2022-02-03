package com.spring.jpa;

import com.spring.jpa.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.spring.jpa.repo.StudentRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository){
		return args -> {
			Student maria = new Student(
					"Maria", "Mutawe", "Maria.Mutawe@gmail.com", 16
			);
			studentRepository.save(maria);
		};
	}

}
