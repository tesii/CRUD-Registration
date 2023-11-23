package auca.registration.rw.AUCA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.registration.rw.AUCA.model.TeacherModel;
import auca.registration.rw.AUCA.repository.TeacherRepository;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repository;

    public String save(TeacherModel teacher) {
        repository.save(teacher);
        return "teacher saved successfully";
    }

    public List<TeacherModel> listTeachers() {
        return repository.findAll();
    }

    public String saveTeacher(TeacherModel teacher) {
        if (teacher != null) {
            repository.save(teacher);
            return "teacher saved successfully";
        } else {
            return null;
        }
    }

    public String update(TeacherModel teacher) {
        // Check if the provided teacher has a valid registration number
        if (teacher != null && teacher.getCode() != null && !teacher.getCode().isEmpty()) {
            // Check if the teacher with the given registration number exists
            TeacherModel existingTeacher = repository.findByCode(teacher.getCode());

            if (existingTeacher != null) {
                // Update the fields of the existing teacher
                existingTeacher.setAssistant_tutor(teacher.getAssistant_tutor());
                existingTeacher.setName(teacher.getAssistant_tutor());
                existingTeacher.setQualification(teacher.getQualification());
                existingTeacher.setTutor(teacher.getTutor());

                // Save the updated teacher
                repository.save(existingTeacher);

                return "Teacher updated successfully";
            } else {
                return "Teacher with regNo " + teacher.getCode() + " not found";
            }
        } else {
            return "Invalid teacher data";
        }
    }
    public String delete(TeacherModel teacher) {
        // Check if the provided teacher has a valid teacher ID (or any other identifier)
        if (teacher != null && teacher.getCode() != null && !teacher.getCode().isEmpty()) {
            // Check if the teacher with the given ID exists
            TeacherModel existingTeacher = repository.findByCode(teacher.getCode());

            if (existingTeacher != null) {
                // Delete the teacher
                repository.delete(existingTeacher);
                return "Teacher deleted successfully";
            } else {
                return "Teacher with ID " + teacher.getCode() + " not found";
            }
        } else {
            return "Invalid teacher data";
        }
    }

	public List<TeacherModel> getAllTeachers() {
		return repository.findAll();
	}

}
