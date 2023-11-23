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

import auca.registration.rw.AUCA.model.Student;
import auca.registration.rw.AUCA.model.TeacherModel;
import auca.registration.rw.AUCA.service.TeacherService; // Create this service class

import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService service; // Create this service class

    @GetMapping("/teacher")
    public String insertTeacher() {
        return "insertTeacher"; // Create a Thymeleaf template for inserting teachers
    }

    @GetMapping("/listteachers")
    public ModelAndView listTeachers() {
        List<TeacherModel> list = service.listTeachers();
        return new ModelAndView("listteachers", "teachers", list); // Create a Thymeleaf template for listing teachers
    }

    @PostMapping("/saveteacher")
    public String addTeacher(@ModelAttribute TeacherModel teacher) {
        service.save(teacher);
        return "redirect:/listteachers";
    }
    @PostMapping(value = "/saveTeacher")
    public ResponseEntity<?> saveTeacher(@RequestBody TeacherModel teacher) throws Exception {

        if (teacher != null) {
            String saveOneTeacher = service.save(teacher);
            if (saveOneTeacher != null) {
                return new ResponseEntity<>("teacher saved successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("something is wrong!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("you are not allowed to send empty data!", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateTeacher")
    public ResponseEntity<String> updateTeacher(@RequestBody TeacherModel teacher) {
        String result = service.update(teacher);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Teacher not found or could not be updated", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteTeacher")
    public ResponseEntity<String> deleteTeacher(@RequestBody TeacherModel teacher) {
        String result = service.delete(teacher);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Teacher not found or could not be deleted", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/listAllTeachers")
	   public ResponseEntity<List<TeacherModel>> listAllTeachers() {
	       List<TeacherModel> teachers = service.getAllTeachers();

	       if (!teachers.isEmpty()) {
	           return new ResponseEntity<>(teachers, HttpStatus.OK);
	       } else {
	           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	       }
	   }
}
