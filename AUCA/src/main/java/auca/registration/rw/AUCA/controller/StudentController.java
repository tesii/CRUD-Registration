package auca.registration.rw.AUCA.controller;

//import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import auca.registration.rw.AUCA.model.Student;
import auca.registration.rw.AUCA.service.StudentService;
import java.util.*;
@Controller
public class StudentController {

	@Autowired
	private StudentService service;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/student")
	public String insertStudent() {
		return "insertStudent";
	}
	
	@GetMapping("/liststudent")
	public ModelAndView listStudent() {
		List<Student>list=service.listStudent();
	
		
		return new ModelAndView("liststudent", "student", list);
	}
	
	@PostMapping("/save")
	public String addStudent(@ModelAttribute Student s) {
		service.save(s);
		return "redirect:/liststudent";
	}
	@PostMapping(value = "/saveStudent")
	public ResponseEntity<?> saveStudent(@RequestBody Student student) throws Exception {

		if(student != null){
			String saveOneStudent = service.save(student);
			if(saveOneStudent != null){
				return new ResponseEntity<>("student saved successfully", HttpStatus.OK);
			}else {
				return new ResponseEntity<>("something is wrong!", HttpStatus.BAD_REQUEST);
			}
		}else{
			return new ResponseEntity<>("you are not allowed to send empty data!", HttpStatus.BAD_REQUEST);
		}
	}
	   @PutMapping("/updateStudent")
	    public ResponseEntity<String> updateStudent(@RequestBody Student student) {
	        String result = service.update(student);
	        if (result != null) {
	            return new ResponseEntity<>(result, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Student not found or could not be updated", HttpStatus.NOT_FOUND);
	        }
	    }
	   @DeleteMapping("/deleteStudent")
	   public ResponseEntity<String> deleteStudent(@RequestBody Student student) {
	       String result = service.delete(student);
	       if (result != null) {
	           return new ResponseEntity<>(result, HttpStatus.OK);
	       } else {
	           return new ResponseEntity<>("Student not found or could not be deleted", HttpStatus.NOT_FOUND);
	       }
	   }

	   @GetMapping("/listAllStudents")
	   public ResponseEntity<List<Student>> listAllStudents() {
	       List<Student> students = service.getAllStudents();

	       if (!students.isEmpty()) {
	           return new ResponseEntity<>(students, HttpStatus.OK);
	       } else {
	           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	       }
	   }

}
