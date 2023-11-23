package auca.registration.rw.AUCA.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.repository.AcademicRepository; // Create this repository interface

@Service
public class AcademicService {

    @Autowired
    private AcademicRepository repository; // Create this repository interface

    public String save(AcademicUnit academicUnit) {
        repository.save(academicUnit);
		return "Academic unit saved successfully";
    }

    public List<AcademicUnit> listAcademicUnits() {
        return repository.findAll();
    }
    public String saveAcademicUnit(AcademicUnit academicUnit) {

        if (academicUnit != null) {
            repository.save(academicUnit);
            return "academic unit saved successfully";
        } else {
            return null;
        }
    }
    public String update(AcademicUnit academicUnit) {
        // Check if the provided academic unit has a valid code
        if (academicUnit != null && academicUnit.getCode() != null && !academicUnit.getCode().isEmpty()) {
            // Check if the academic unit with the given code exists
            AcademicUnit existingAcademicUnit = repository.findByCode(academicUnit.getCode());

            if (existingAcademicUnit != null) {
                // Update the fields of the existing academic unit
                existingAcademicUnit.setAcademicUnitType(academicUnit.getAcademicUnitType());
                existingAcademicUnit.setName(academicUnit.getName());
                existingAcademicUnit.setParentId(academicUnit.getParentId());

                // Save the updated academic unit
                repository.save(existingAcademicUnit);

                return "AcademicUnit updated successfully";
            } else {
                return "AcademicUnit with code " + academicUnit.getCode() + " not found";
            }
        } else {
            return "Invalid academic unit data";
        }
    }
    public String delete(AcademicUnit academicUnit) {
        // Check if the provided academic unit has a valid ID (or any other identifier)
        if (academicUnit != null && academicUnit.getCode() != null && !academicUnit.getCode().isEmpty()) {
            // Check if the academic unit with the given ID exists
            Optional<AcademicUnit> existingAcademicUnitOptional = repository.findById(academicUnit.getCode());

            if (existingAcademicUnitOptional.isPresent()) {
                // Delete the academic unit
                repository.delete(existingAcademicUnitOptional.get());
                return "Academic unit deleted successfully";
            } else {
                return "Academic unit with ID " + academicUnit.getCode() + " not found";
            }
        } else {
            return "Invalid academic unit data";
        }
    }

	public List<AcademicUnit> getAllAcademicUnits() {
		return repository.findAll();
	}


}
