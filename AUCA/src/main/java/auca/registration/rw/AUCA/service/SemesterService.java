package auca.registration.rw.AUCA.service;

import auca.registration.rw.AUCA.model.Semester;
import auca.registration.rw.AUCA.model.Student;
import auca.registration.rw.AUCA.model.TeacherModel;
import auca.registration.rw.AUCA.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    public String saveSemester(Semester semester) {
        if (semester != null) {
            semesterRepository.save(semester);
            return "semester saved successfully";
        } else {
            return null;
        }
    }

    public List<Semester> listSemesters() {
        return semesterRepository.findAll();
    }

	public String save(Semester s) {
		if (s != null) {
			semesterRepository.save(s);
			return "saved successfully" ;
		}else{

			return null;
		}
    }
	

	public String update(Semester semester) {
	    // Check if the provided semester has a valid id
	    if (semester != null && semester.getId() != null && !semester.getId().isEmpty()) {
	        // Check if the semester with the given id exists
	        Optional<Semester> optionalExistingSemester = semesterRepository.findById(semester.getId());

	        if (optionalExistingSemester.isPresent()) {
	            // Update the fields of the existing semester
	            Semester existingSemester = optionalExistingSemester.get();
	            existingSemester.setName(semester.getName());
	            existingSemester.setEndDate(semester.getEndDate());
	            existingSemester.setStartDate(semester.getStartDate());

	            // Save the updated semester
	            semesterRepository.save(existingSemester);

	            return "Semester updated successfully";
	        } else {
	            return "Semester with id " + semester.getId() + " not found";
	        }
	    } else {
	        return "Invalid semester data";
	    }
	}
	public String delete(Semester semester) {
	    // Check if the provided semester has a valid semester ID (or any other identifier)
	    if (semester != null && semester.getId() != null && !semester.getId().isEmpty()) {
	        // Check if the semester with the given ID exists
	        Optional<Semester> existingSemesterOptional = semesterRepository.findById(semester.getId());

	        if (existingSemesterOptional.isPresent()) {
	            // Delete the semester
	            semesterRepository.delete(existingSemesterOptional.get());
	            return "Semester deleted successfully";
	        } else {
	            return "Semester with ID " + semester.getId() + " not found";
	        }
	    } else {
	        return "Invalid semester data";
	    }
	}

	public List<Semester> getAllSemesters() {
		return semesterRepository.findAll();
	}


}
