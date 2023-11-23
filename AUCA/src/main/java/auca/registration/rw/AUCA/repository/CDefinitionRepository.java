package auca.registration.rw.AUCA.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.CDefinitionModel;
import auca.registration.rw.AUCA.model.Semester;



public interface CDefinitionRepository extends JpaRepository<CDefinitionModel, String>{

	
	 Optional<CDefinitionModel> findById(String id);
}
