package auca.registration.rw.AUCA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.CDefinitionModel;
import auca.registration.rw.AUCA.model.Semester;
import auca.registration.rw.AUCA.repository.CDefinitionRepository; // Create this repository interface

import java.util.List;
import java.util.Optional;

@Service
public class CDefinitionService {

    @Autowired
    private CDefinitionRepository repository; // Create this repository interface

    public String save(CDefinitionModel cDefinition) {
        repository.save(cDefinition);
        return "course definition saved successfully";
    }

    public List<CDefinitionModel> listCDefinitions() {
        return repository.findAll();
    }
    public String saveCDefinition(CDefinitionModel cdefinition) {
        if (cdefinition != null) {
            repository.save(cdefinition);
            return "course definition saved successfully";
        } else {
            return null;
        }
    }
    public String update(CDefinitionModel cDefinition) {
        // Check if the provided CDefinitionModel has a valid ID
        if (cDefinition != null && cDefinition.getId() != null && !cDefinition.getId().isEmpty()) {
            // Check if the CDefinitionModel with the given ID exists
            Optional<CDefinitionModel> existingCDefinitionModelOptional = repository.findById(cDefinition.getId());

            if (existingCDefinitionModelOptional.isPresent()) {
                // Extract the actual CDefinitionModel from the Optional
                CDefinitionModel existingCDefinitionModel = existingCDefinitionModelOptional.get();

                // Update the fields of the existing CDefinitionModel
                existingCDefinitionModel.setCourse_code(cDefinition.getCourse_code());
                existingCDefinitionModel.setName(cDefinition.getName());
                existingCDefinitionModel.setDescription(cDefinition.getDescription());

                // Save the updated CDefinitionModel
                repository.save(existingCDefinitionModel);

                return "CDefinitionModel updated successfully";
            } else {
                return "CDefinitionModel with ID " + cDefinition.getId() + " not found";
            }
        } else {
            return "Invalid CDefinitionModel data";
        }
    }

    public String delete(CDefinitionModel cDefinition) {
        // Check if the provided CDefinition has a valid ID (or any other identifier)
        if (cDefinition != null && cDefinition.getId() != null && !cDefinition.getId().isEmpty()) {
            // Check if the CDefinition with the given ID exists
            Optional<CDefinitionModel> existingCDefinitionOptional = repository.findById(cDefinition.getId());

            if (existingCDefinitionOptional.isPresent()) {
                // Delete the CDefinition
                repository.delete(existingCDefinitionOptional.get());
                return "CDefinition deleted successfully";
            } else {
                return "CDefinition with ID " + cDefinition.getId() + " not found";
            }
        } else {
            return "Invalid CDefinition data";
        }
    }

	public List<CDefinitionModel> getAllCDefinitions() {
		return repository.findAll();
	}

}
