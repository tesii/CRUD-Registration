package auca.registration.rw.AUCA.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.RegistrationModel;
import auca.registration.rw.AUCA.model.Semester;
import auca.registration.rw.AUCA.model.Student;
import auca.registration.rw.AUCA.repository.AcademicRepository; // Create this repository interface
import auca.registration.rw.AUCA.repository.RegistrationRepository;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository repository; // Create this repository interface

    public String save(RegistrationModel registration) {
        repository.save(registration);
        return "registration saved successfully";
    }

    public List<RegistrationModel> listRegistrations() {
        return repository.findAll();
    }

    public List<RegistrationModel> listStudentsBySemester(String semester_id) {
        return repository.findBySemester_id(semester_id);
    }

    public List<RegistrationModel> listStudentsBySemesters(String semester_id) {
        return repository.findBySemester_id(semester_id);
    }
    public List<RegistrationModel> listStudentsBySemesterAndDepartment(String semester_id, String academic_unit_id) {
        return repository.findBySemesterIdAndAcademicUnitCode(semester_id, academic_unit_id);
    }

    public List<RegistrationModel> listStudentsBySemesterAndDepartments(String semester_id, String academic_unit_id) {
        return repository.findBySemesterIdAndAcademicUnitCode(semester_id, academic_unit_id);
    }
    public List<RegistrationModel> listStudentsBySemesterAndCourse(String semester_id, String course_id) {
        return repository.findBySemesterIdAndCourseId(semester_id, course_id);
    }
    public List<RegistrationModel> listStudentsByCoursesAndSemester(String semester_id, String course_id) {
        return repository.findBySemesterIdAndCourseId(semester_id, course_id);
    }
	public List<Semester> listSemesters() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String saveregistration(RegistrationModel s) {
		if (s != null) {
			repository.save(s);
			return "saved successfully" ;
		}else{

			return null;
		}

    }
	public String update(RegistrationModel registration) {
	    // Check if the provided registration has a valid ID
	    if (registration != null && registration.getId() != null && !registration.getId().isEmpty()) {
	        // Check if the registration with the given ID exists
	        Optional<RegistrationModel> existingRegistrationOptional = repository.findById(registration.getId());

	        if (existingRegistrationOptional.isPresent()) {
	            // Extract the actual RegistrationModel from the Optional
	            RegistrationModel existingRegistration = existingRegistrationOptional.get();

	            // Update the fields of the existing RegistrationModel
	            existingRegistration.setAcademic_unit_id(registration.getAcademic_unit_id());
	            existingRegistration.setCourse_id(registration.getCourse_id());
	            existingRegistration.setSemester_id(registration.getSemester_id());
	            existingRegistration.setStudent_id(registration.getStudent_id());

	            // Save the updated RegistrationModel
	            repository.save(existingRegistration);

	            return "Registration updated successfully";
	        } else {
	            return "Registration with ID " + registration.getId() + " not found";
	        }
	    } else {
	        return "Invalid registration data";
	    }
	}
	public String delete(RegistrationModel registration) {
	    // Check if the provided Registration has a valid ID (or any other identifier)
	    if (registration != null && registration.getId() != null && !registration.getId().isEmpty()) {
	        // Check if the Registration with the given ID exists
	        Optional<RegistrationModel> existingRegistrationOptional = repository.findById(registration.getId());

	        if (existingRegistrationOptional.isPresent()) {
	            // Delete the Registration
	            repository.delete(existingRegistrationOptional.get());
	            return "Registration deleted successfully";
	        } else {
	            return "Registration with ID " + registration.getId() + " not found";
	        }
	    } else {
	        return "Invalid Registration data";
	    }
	}

	public List<RegistrationModel> getAllRegistrations() {
		return repository.findAll();
	}

}
