package auca.registration.rw.AUCA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.CDefinitionModel; // Change the import to the correct model
import auca.registration.rw.AUCA.model.Semester;
import auca.registration.rw.AUCA.service.CDefinitionService; // Change the import to the correct service

import java.util.List;

@Controller
public class CDefinitionController {

    @Autowired
    private CDefinitionService service; // Change the service type to the correct service class

    @GetMapping("/cdefinition")
    public String insertCDefinition() {
        return "insertCDefinition"; // Create a Thymeleaf template for inserting CDefinitions
    }

    @GetMapping("/listcdefinitions")
    public ModelAndView listCDefinitions() {
        List<CDefinitionModel> list = service.listCDefinitions();
        return new ModelAndView("listcdefinitions", "cdefinitions", list); // Create a Thymeleaf template for listing CDefinitions
    }

    @PostMapping("/savecdefinition")
    public String addCDefinition(@ModelAttribute CDefinitionModel cDefinition) {
        service.save(cDefinition);
        return "redirect:/listcdefinitions";
    }
    @PostMapping(value = "/saveCDefinition")
    public ResponseEntity<?> saveCDefinition(@RequestBody CDefinitionModel cDefinition) throws Exception {

        if (cDefinition != null) {
            String saveOnecDefinition = service.save(cDefinition);
            if (saveOnecDefinition != null) {
                return new ResponseEntity<>("coursedefinition saved successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("something is wrong!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("you are not allowed to send empty data!", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateCDefinition")
    public ResponseEntity<String> updateCDefinition(@RequestBody CDefinitionModel cdefinition) {
        String result = service.update(cdefinition);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("course definition not found or could not be updated", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteCDefinition")
    public ResponseEntity<String> deleteCDefinition(@RequestBody CDefinitionModel cDefinition) {
        String result = service.delete(cDefinition);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("CDefinition not found or could not be deleted", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/listAllCDefinitions")
    public ResponseEntity<List<CDefinitionModel>> listAllCDefinitions() {
        List<CDefinitionModel> cDefinitions = service.getAllCDefinitions();

        if (!cDefinitions.isEmpty()) {
            return new ResponseEntity<>(cDefinitions, HttpStatus.OK);
        }  else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    }


