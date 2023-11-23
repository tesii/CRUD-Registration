package auca.registration.rw.AUCA.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.CDefinitionModel;
import auca.registration.rw.AUCA.model.CourseModel;



public interface CourseRepository extends JpaRepository<CourseModel, String>{

	List<CourseModel> findBySemesterAndAcademicUnitCode(String selectedSemesterId, String selectedDepartmentId);

	List<CourseModel> findByStudentRegNo(String selectedStudentId);
	 Optional<CourseModel> findById(String id);
}
