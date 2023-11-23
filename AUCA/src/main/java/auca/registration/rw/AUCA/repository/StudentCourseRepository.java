package auca.registration.rw.AUCA.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.CourseModel;
import auca.registration.rw.AUCA.model.Semester;
import auca.registration.rw.AUCA.model.StudentCourseModel;



public interface StudentCourseRepository extends JpaRepository<StudentCourseModel, Integer>{

	 Optional<StudentCourseModel> findByID(int ID);
}
