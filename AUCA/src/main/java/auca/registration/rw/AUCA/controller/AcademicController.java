package auca.registration.rw.AUCA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.Semester;
import auca.registration.rw.AUCA.model.StudentCourseModel;
import auca.registration.rw.AUCA.model.TeacherModel;
import auca.registration.rw.AUCA.repository.AcademicRepository;
import auca.registration.rw.AUCA.repository.CourseRepository;
import auca.registration.rw.AUCA.service.AcademicService; // Make sure to create this service class

import java.util.List;

@Controller
public class AcademicController {

    @Autowired
    private AcademicService service; // Make sure to create this service class

    @Autowired
    private AcademicRepository academicRepository;
    
    @GetMapping("/academicForm")
    public String showAcademicForm(Model model) {
        model.addAttribute("academicModel", new AcademicUnit());
        model.addAttribute("academics", academicRepository.findAll());


        return "insertAcademicUnit"; // Assuming "courseForm" is the name of your Thymeleaf template
    }
    @GetMapping("/academicunit")
    public String insertAcademicUnit() {
        return "insertAcademicUnit"; // Create a Thymeleaf template for inserting academic units
    }

    @GetMapping("/listacademicunits")
    public ModelAndView listAcademicUnits() {
        List<AcademicUnit> list = service.listAcademicUnits();
        return new ModelAndView("listacademicunits", "academicunits", list); // Create a Thymeleaf template for listing academic units
    }

    @PostMapping("/saveacademicunit")
    public String addAcademicUnit(@ModelAttribute AcademicUnit academicUnit) {
        service.save(academicUnit);
        return "redirect:/listacademicunits";
    }
    @PostMapping(value = "/saveAcademicUnit")
    public ResponseEntity<?> saveAcademicUnit(@RequestBody AcademicUnit academicUnit) throws Exception {

        if (academicUnit != null) {
            String saveOneAcademicUnit = service.save(academicUnit);
            if (saveOneAcademicUnit != null) {
                return new ResponseEntity<>("academic unit saved successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("something is wrong!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("you are not allowed to send empty data!", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateAcademicUnit")
    public ResponseEntity<String> updateAcademicunit(@RequestBody AcademicUnit academicUnit) {
        String result = service.update(academicUnit);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Academicunit not found or could not be updated", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteAcademicUnit")
    public ResponseEntity<String> deleteAcademicUnit(@RequestBody AcademicUnit academicUnit) {
        String result = service.delete(academicUnit);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Academic unit not found or could not be deleted", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/listAllAcademicUnits")
    public ResponseEntity<List<AcademicUnit>> listAllAcademicUnits() {
        List<AcademicUnit> academicUnits = service.getAllAcademicUnits();

        if (!academicUnits.isEmpty()) {
            return new ResponseEntity<>(academicUnits, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
