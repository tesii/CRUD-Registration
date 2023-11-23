package auca.registration.rw.AUCA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.Student;
import auca.registration.rw.AUCA.model.TeacherModel;



public interface TeacherRepository extends JpaRepository<TeacherModel, String>{

	 TeacherModel findByCode(String code);
}
