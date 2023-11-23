package auca.registration.rw.AUCA.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.registration.rw.AUCA.model.Semester;
import auca.registration.rw.AUCA.model.TeacherModel;


public interface SemesterRepository extends JpaRepository<Semester, String>{

	
	 Optional<Semester> findById(String id);
}
