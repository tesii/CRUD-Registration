package auca.registration.rw.AUCA.controller;

import auca.registration.rw.AUCA.model.Semester;
import auca.registration.rw.AUCA.model.TeacherModel;
import auca.registration.rw.AUCA.service.SemesterService;
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

import java.util.List;

@Controller
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

   

    @GetMapping("/semester")
    public String insertSemester() {
        return "insertSemester"; // Update the view name to match your actual view
    }

    @GetMapping("/listsemesters")
    public ModelAndView listSemesters() {
        List<Semester> semesterList = semesterService.listSemesters();
        return new ModelAndView("listsemesters", "semesters", semesterList); // Update the view name to match your actual view
    }

    @PostMapping("/saveSemester")
    public String addSemester(@ModelAttribute Semester semester) {
        semesterService.save(semester);
        return "redirect:/listsemesters"; // Update the redirect URL to match your actual view
    }
    @PostMapping(value = "/savesemester")
    public ResponseEntity<?> saveSemester(@RequestBody Semester semester) throws Exception {

        if (semester != null) {
            String saveOneSemester = semesterService.save(semester);
            if (saveOneSemester != null) {
                return new ResponseEntity<>("semester saved successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("something is wrong!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("you are not allowed to send empty data!", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateSemester")
    public ResponseEntity<String> updateSemester(@RequestBody Semester semester) {
        String result = semesterService.update(semester);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Semester not found or could not be updated", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteSemester")
    public ResponseEntity<String> deleteSemester(@RequestBody Semester semester) {
        String result = semesterService.delete(semester);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Semester not found or could not be deleted", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/listAllSemesters")
    public ResponseEntity<List<Semester>> listAllSemesters() {
        List<Semester> semesters = semesterService.getAllSemesters();

        if (!semesters.isEmpty()) {
            return new ResponseEntity<>(semesters, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
