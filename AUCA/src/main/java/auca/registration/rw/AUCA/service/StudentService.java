package auca.registration.rw.AUCA.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import auca.registration.rw.AUCA.model.Student;
import auca.registration.rw.AUCA.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository bRepo;
	
	/*
	 * public String save(Student s) { bRepo.save(s); }
	 */
	public List<Student> listStudent(){
		return bRepo.findAll();
	}
	public String save(Student s) {
		if (s != null) {
			bRepo.save(s);
			return "saved successfully" ;
		}else{

			return null;
		}

    }
	  public String update(Student student) {
	        // Check if the provided student has a valid registration number
	        if (student != null && student.getRegNo() != null && !student.getRegNo().isEmpty()) {
	            // Check if the student with the given registration number exists
	            Student existingStudent = bRepo.findByRegNo(student.getRegNo());

	            if (existingStudent != null) {
	                // Update the fields of the existing student
	                existingStudent.setFirstName(student.getFirstName());
	                existingStudent.setDateOfBirth(student.getDateOfBirth());

	                // Save the updated student
	                bRepo.save(existingStudent);

	                return "Student updated successfully";
	            } else {
	                return "Student with regNo " + student.getRegNo() + " not found";
	            }
	        } else {
	            return "Invalid student data";
	        }
	    }
	  public String delete(Student student) {
		    // Check if the provided student has a valid registration number
		    if (student != null && student.getRegNo() != null && !student.getRegNo().isEmpty()) {
		        // Check if the student with the given registration number exists
		        Student existingStudent = bRepo.findByRegNo(student.getRegNo());

		        if (existingStudent != null) {
		            // Delete the student
		            bRepo.delete(existingStudent);
		            return "Student deleted successfully";
		        } else {
		            return "Student with regNo " + student.getRegNo() + " not found";
		        }
		    } else {
		        return "Invalid student data";
		    }
		}
	   public List<Student> getAllStudents() {
	        // Assuming studentRepository.findAll() returns a List<Student>
	        return bRepo.findAll();
	    }

	}