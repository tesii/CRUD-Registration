package auca.registration.rw.AUCA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.CourseModel;
import auca.registration.rw.AUCA.model.TeacherModel;



public interface AcademicRepository extends JpaRepository<AcademicUnit, String>{

	AcademicUnit findByCode(String code);
}
