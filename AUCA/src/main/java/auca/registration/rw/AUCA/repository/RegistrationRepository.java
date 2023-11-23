package auca.registration.rw.AUCA.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.CourseModel;
import auca.registration.rw.AUCA.model.RegistrationModel;



public interface RegistrationRepository extends JpaRepository<RegistrationModel, String>{

	 List<RegistrationModel> findBySemester_id(String semester_id);



	 List<RegistrationModel> findBySemesterIdAndAcademicUnitCode(String semesterId, String academicUnitCode);

	 List<RegistrationModel> findBySemesterIdAndCourseId(String semesterId, String courseId);

	 Optional<RegistrationModel> findById(String id);


}
