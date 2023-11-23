package auca.registration.rw.AUCA.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.RegistrationModel;
import auca.registration.rw.AUCA.model.StudentCourseModel;
import auca.registration.rw.AUCA.model.TeacherModel;
import auca.registration.rw.AUCA.repository.AcademicRepository; // Create this repository interface
import auca.registration.rw.AUCA.repository.RegistrationRepository;
import auca.registration.rw.AUCA.repository.StudentCourseRepository;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseRepository repository; // Create this repository interface

    public String save(StudentCourseModel studentcourse) {
        repository.save(studentcourse);
        return "student course saved successfully";
    }

    public List<StudentCourseModel> listStudentCourses() {
        return repository.findAll();
    }
    public String saveStudentCourse(StudentCourseModel studentcourse) {
        if (studentcourse != null) {
            repository.save(studentcourse);
            return "studentcourse saved successfully";
        } else {
            return null;
        }
    }
    public String update(StudentCourseModel studentCourse) {
        // Check if the provided studentCourse has a valid ID
        if (studentCourse != null && studentCourse.getID() != 0) {
            
            // Check if the studentCourse with the given ID exists
            Optional<StudentCourseModel> existingStudentCourseOptional = repository.findByID(studentCourse.getID());

            if (existingStudentCourseOptional.isPresent()) {
                // Extract the actual StudentCourseModel from the Optional
                StudentCourseModel existingStudentCourse = existingStudentCourseOptional.get();

                // Update the fields of the existing StudentCourseModel
                existingStudentCourse.setCourseID(studentCourse.getCourseID());
                existingStudentCourse.setCredits(studentCourse.getCredits());
                existingStudentCourse.setResults(studentCourse.getResults());
                existingStudentCourse.setStudentID(studentCourse.getStudentID());

                // Save the updated StudentCourseModel
                repository.save(existingStudentCourse);

                return "StudentCourse updated successfully";
            } else {
                return "StudentCourse with ID " + studentCourse.getID() + " not found";
            }
        } else {
            return "Invalid studentCourse data";
        }
    }
    public String delete(StudentCourseModel studentCourse) {
        // Check if the provided StudentCourse has a valid ID (or any other identifier)
    	 if (studentCourse != null && studentCourse.getID() != 0){
            // Check if the StudentCourse with the given ID exists
            Optional<StudentCourseModel> existingStudentCourseOptional = repository.findByID(studentCourse.getID());

            if (existingStudentCourseOptional.isPresent()) {
                // Delete the StudentCourse
                repository.delete(existingStudentCourseOptional.get());
                return "StudentCourse deleted successfully";
            } else {
                return "StudentCourse with ID " + studentCourse.getID() + " not found";
            }
        } else {
            return "Invalid StudentCourse data";
        }
    }

	public List<StudentCourseModel> getAllStudentCourses() {
		return repository.findAll();
	}



}
