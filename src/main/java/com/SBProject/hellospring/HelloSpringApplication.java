package com.SBProject.hellospring;

import com.SBProject.hellospring.dao.StudentDAO;
import com.SBProject.hellospring.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

//@SpringBootApplication(
//		scanBasePackages = {"com.SBProject.hellospring",
//		"com.SBProject.util"})
@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO){
		return runner -> {
//			createStudent(studentDAO);
//			createMultipleStudents(studentDAO);

//			readStudent(studentDAO);

//			queryForStudents(studentDAO);

//			updateStudent(studentDAO);

//			deleteStudent(studentDAO);
		};
	}

	private void deleteStudent(StudentDAO studentDAO) {
		int studentId = 6;
		System.out.println("Deleting student id: "+studentId);

		studentDAO.delete(studentId);
	}

	private void updateStudent(StudentDAO studentDAO) {
		//retrieve student based on the id
		int studentId = 3;
		System.out.println("Getting the student with id: " + studentId);
		Student myStudent = studentDAO.findById(studentId);

		//change lastname
		System.out.println("Updating the student");
		myStudent.setLastName("James");

		//update the student
		studentDAO.update(myStudent);

		//display the updated student
		System.out.println("Updated Student: "+ myStudent);


	}

	private void queryForStudents(StudentDAO studentDAO) {
		//get a list of students
		List<Student> theStudents = studentDAO.findAll();

		// display list of students
		for(Student tempStudent : theStudents){
			System.out.println(tempStudent);
		}
	}

	private void readStudent(StudentDAO studentDAO) {
		// create  a student object
		System.out.println("Creating new student");
		Student tempStudent = new Student("Krupa", "Patel", "krupa@gmail.com");

		// save the student
		System.out.println("Saving the student...");
		studentDAO.save(tempStudent);

		// display id of the saved student
		int theId = tempStudent.getId();
		System.out.println("Saved student. Generated id: "+ theId);

		// retrieve student based on the id: primary key
		System.out.println("Retrieving student. Generated id: "+ theId);
		Student myStudent = studentDAO.findById(theId);

		// display the student
		System.out.println("Find the student : "+ myStudent);
	}

	private void createMultipleStudents(StudentDAO studentDAO) {
		System.out.println("Creating 3 student object....");
		Student tempStudent1 = new Student("Yash", "Patel", "yash@gmail.com");
		Student tempStudent2 = new Student("Vishwa", "Patel", "vishwa@gmail.com");
		Student tempStudent3 = new Student("Jainil", "Patel", "jainil@gmail.com");

		//saving the students
		System.out.println("Saving student object....");
		studentDAO.save(tempStudent1);
		studentDAO.save(tempStudent2);
		studentDAO.save(tempStudent3);

	}

	private void createStudent(StudentDAO studentDAO) {

		// create the student object
		System.out.println("Creating new student object....");
		Student tempStudent = new Student("Krupa", "Patel", "krupa@gmail.com");

		//save the student object
		System.out.println("Saving student object....");
		studentDAO.save(tempStudent);

		//display id of the saved student
		System.out.println("Saved student. Generated id: "+tempStudent.getId());
	}
}
