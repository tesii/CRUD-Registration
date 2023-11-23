package auca.registration.rw.AUCA.controller;

import auca.registration.rw.AUCA.model.CourseModel;
import auca.registration.rw.AUCA.model.RegistrationModel;
import auca.registration.rw.AUCA.model.Student;
import auca.registration.rw.AUCA.model.StudentCourseModel;
import auca.registration.rw.AUCA.model.TeacherModel;
import auca.registration.rw.AUCA.repository.AcademicRepository;
import auca.registration.rw.AUCA.repository.CDefinitionRepository;
import auca.registration.rw.AUCA.repository.CourseRepository;
import auca.registration.rw.AUCA.repository.RegistrationRepository;
import auca.registration.rw.AUCA.repository.StudentCourseRepository;
import auca.registration.rw.AUCA.repository.TeacherRepository;
import auca.registration.rw.AUCA.service.RegistrationService;
import auca.registration.rw.AUCA.service.StudentCourseService;

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

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentCourseController {

    @Autowired
    private StudentCourseService studentcourseService; // Make sure to create this service class

    @Autowired
    private StudentCourseRepository studentcourseRepository;

    @Autowired
    private RegistrationRepository registrationRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @GetMapping("/studentcourseForm")
    public String showCourseForm(Model model) {
        model.addAttribute("studentcourseModel", new StudentCourseModel());
        model.addAttribute("registrations", registrationRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());

        return "insertStudentCourse"; // Assuming "courseForm" is the name of your Thymeleaf template
    }
    
    @ModelAttribute("studentcourse")
    public StudentCourseModel studentCourseModel() {
        return new StudentCourseModel();
    }

    
  
    @GetMapping("/studentcourse")
    public String insertStudentCourse() {
        return "insertStudentCourse"; // Create a Thymeleaf template for the registration form
    }

    @GetMapping("/liststudentcourses")
    public ModelAndView listStudentCourses() {
        List<StudentCourseModel> studentcourseList = studentcourseService.listStudentCourses();
        return new ModelAndView("liststudentcourses", "studentcourses", studentcourseList);
        // Create a Thymeleaf template for listing registrations
    }

    @PostMapping("/savestudentcourses")
    public String saveStudentCourse(@ModelAttribute StudentCourseModel studentcourse) {
        studentcourseService.save(studentcourse);
        return "redirect:/liststudentcourses";
    }
    @PostMapping(value = "/saveStudentCourse")
    public ResponseEntity<?> saveStudentCourses(@RequestBody StudentCourseModel studentcourse) throws Exception {

        if (studentcourse != null) {
            String saveOneStudentcourse = studentcourseService.save(studentcourse);
            if (saveOneStudentcourse != null) {
                return new ResponseEntity<>("studentcourse saved successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("something is wrong!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("you are not allowed to send empty data!", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateStudentCourse")
    public ResponseEntity<String> updateStudentCourse(@RequestBody StudentCourseModel studentCourse) {
        String result = studentcourseService.update(studentCourse);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("StudentCourse not found or could not be updated", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteStudentCourse")
    public ResponseEntity<String> deleteStudentCourse(@RequestBody StudentCourseModel studentCourse) {
        String result = studentcourseService.delete(studentCourse);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("StudentCourse not found or could not be deleted", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/listAllStudentCourses")
    public ResponseEntity<List<StudentCourseModel>> listAllStudentCourses() {
        List<StudentCourseModel> studentCourses = studentcourseService.getAllStudentCourses();

        if (!studentCourses.isEmpty()) {
            return new ResponseEntity<>(studentCourses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
