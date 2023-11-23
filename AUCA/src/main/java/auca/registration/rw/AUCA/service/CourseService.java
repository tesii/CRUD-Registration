package auca.registration.rw.AUCA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.registration.rw.AUCA.model.CDefinitionModel;
import auca.registration.rw.AUCA.model.CourseModel;
import auca.registration.rw.AUCA.model.RegistrationModel;
import auca.registration.rw.AUCA.model.TeacherModel;
import auca.registration.rw.AUCA.repository.CourseRepository; // Create this repository interface

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository; // Create this repository interface

    public String save(CourseModel course) {
        repository.save(course);
        return "Courses saved successfully";
    }
    public List<CourseModel> listCoursesBySemesterAndDepartment(String semester, String academic_unit_id) {
        return repository.findBySemesterAndAcademicUnitCode(semester, academic_unit_id);
    }
    public List<CourseModel> listCourseByDepartmentAndSemester(String semester, String academic_unit_id) {
        return repository.findBySemesterAndAcademicUnitCode(semester, academic_unit_id);
    }
    public List<CourseModel> listCoursesByStudent(String student_id) {
        return repository.findByStudentRegNo(student_id);
    }
    public List<CourseModel> listCourseByStudent(String student_id) {
        return repository.findByStudentRegNo(student_id);
    }
    public List<CourseModel> listCourses() {
        return repository.findAll();
    }
    public String saveCourse(CourseModel course) {
        if (course != null) {
            repository.save(course);
            return "course saved successfully";
        } else {
            return null;
        }
    }
    public String update(CourseModel course) {
        // Check if the provided course has a valid ID
        if (course != null && course.getId() != null && !course.getId().isEmpty()) {
            // Check if the course with the given ID exists
            Optional<CourseModel> existingCourseOptional = repository.findById(course.getId());

            if (existingCourseOptional.isPresent()) {
                // Extract the actual CourseModel from the Optional
                CourseModel existingCourse = existingCourseOptional.get();

                // Update the fields of the existing CourseModel
                existingCourse.setAcademic_unit_id(course.getAcademic_unit_id());
                existingCourse.setCoursedefinitionid(course.getCoursedefinitionid());
                existingCourse.setSemester(course.getSemester());
                existingCourse.setStudent_id(course.getStudent_id());
                existingCourse.setTeacherID(course.getTeacherID());

                // Save the updated CourseModel
                repository.save(existingCourse);

                return "Course updated successfully";
            } else {
                return "Course with ID " + course.getId() + " not found";
            }
        } else {
            return "Invalid course data";
        }
    }
    public String delete(CourseModel course) {
        // Check if the provided Course has a valid ID (or any other identifier)
        if (course != null && course.getId() != null && !course.getId().isEmpty()) {
            // Check if the Course with the given ID exists
            Optional<CourseModel> existingCourseOptional = repository.findById(course.getId());

            if (existingCourseOptional.isPresent()) {
                // Delete the Course
                repository.delete(existingCourseOptional.get());
                return "Course deleted successfully";
            } else {
                return "Course with ID " + course.getId() + " not found";
            }
        } else {
            return "Invalid Course data";
        }
    }
	public List<CourseModel> getAllCourseModels() {
		return repository.findAll();
	}

}
