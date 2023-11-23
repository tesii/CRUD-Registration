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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auca.registration.rw.AUCA.model.CDefinitionModel;
import auca.registration.rw.AUCA.model.CourseModel;
import auca.registration.rw.AUCA.model.RegistrationModel;
import auca.registration.rw.AUCA.model.Student;
import auca.registration.rw.AUCA.model.TeacherModel;
import auca.registration.rw.AUCA.repository.AcademicRepository;
import auca.registration.rw.AUCA.repository.CDefinitionRepository;
import auca.registration.rw.AUCA.repository.CourseRepository;
import auca.registration.rw.AUCA.repository.SemesterRepository;
import auca.registration.rw.AUCA.repository.StudentRepository;
import auca.registration.rw.AUCA.repository.TeacherRepository;
import auca.registration.rw.AUCA.service.CourseService; // Make sure to create this service class
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseService service; // Make sure to create this service class

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private AcademicRepository academicUnitRepository;

    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private CDefinitionRepository cdefinitionRepository;
    
    @GetMapping("/courseForm")
    public String showCourseForm(Model model) {
        model.addAttribute("courseModel", new CourseModel());
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("semesters", semesterRepository.findAll());
        model.addAttribute("academicUnits", academicUnitRepository.findAll());
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("cdefinitions", cdefinitionRepository.findAll());
        return "insertCourse"; // Assuming "courseForm" is the name of your Thymeleaf template
    }
    @GetMapping("/course")
    public String insertCourse() {
        return "insertCourse"; // Create a Thymeleaf template for inserting courses
    }

    @GetMapping("/listcourses")
    public ModelAndView listCourses() {
        List<CourseModel> list = service.listCourses();
        return new ModelAndView("listcourses", "courses", list); // Create a Thymeleaf template for listing courses
    }

    @PostMapping("/savecourse")
    public String addCourse(@ModelAttribute CourseModel course) {
        service.save(course);
        return "redirect:/listcourses";
    }

    @GetMapping("/listCoursesByDepartmentAndSemesterForm")
    public String getCoursesByDepartmentAndSemesterForm(Model model) {
        model.addAttribute("courseModel", new CourseModel());
        model.addAttribute("semesters", semesterRepository.findAll());
        model.addAttribute("academicUnits", academicUnitRepository.findAll());
        return "Question4";
    }


    @PostMapping("/listCoursesByDepartmentAndSemester")
    public String showCoursesByDepartmentAndSemester(@ModelAttribute CourseModel courseModel, Model model) {
        String selectedSemesterId = courseModel.getSemester();
        String selectedDepartmentId = courseModel.getAcademic_unit_id(); // Assuming you have a department_id field in RegistrationModel

        // Use the selected semesterId and departmentId to fetch data from the repository
        List<CourseModel> courses = courseRepository.findBySemesterAndAcademicUnitCode(selectedSemesterId, selectedDepartmentId );


        // Create a list of DTOs (Data Transfer Objects) to hold combined information
        List<CDefinitionModel> cdefinitions = new ArrayList<>();
        List<TeacherModel> teachers = new ArrayList<>();
        // Iterate through registrations and extract relevant information
        for (CourseModel course : courses) {
            CDefinitionModel courseDefinition = course.getCourseDefinition();
            cdefinitions.add(new CDefinitionModel(course.getId(), courseDefinition.getCourse_code(), courseDefinition.getName(), courseDefinition.getDescription()));
            
            TeacherModel teacher = course.getTeacher();
            teachers.add(new TeacherModel(course.getId(), teacher.getName(), teacher.getTutor(), teacher.getAssistant_tutor(), teacher.getQualification()));
        }

        // Add the list of DTOs to the model
        model.addAttribute("cdefinitions", cdefinitions);
        model.addAttribute("teachers", teachers);
        return "Question4";
    }
    
    @GetMapping("/listCoursesByStudentForm")
    public String getCoursesByStudentForm(Model model) {
        model.addAttribute("courseModel", new CourseModel());
        model.addAttribute("students", studentRepository.findAll());

        return "Question5";
    }


    @PostMapping("/listCoursesByStudent")
    public String showCoursesByStudent(@ModelAttribute CourseModel courseModel, Model model) {
        String selectedStudentId = courseModel.getStudent_id();


        List<CourseModel> courses = courseRepository.findByStudentRegNo(selectedStudentId );


        // Create a list of DTOs (Data Transfer Objects) to hold combined information
        List<CDefinitionModel> cdefinitions = new ArrayList<>();
        List<TeacherModel> teachers = new ArrayList<>();
        // Iterate through registrations and extract relevant information
        for (CourseModel course : courses) {
            CDefinitionModel courseDefinition = course.getCourseDefinition();
            cdefinitions.add(new CDefinitionModel(course.getId(), courseDefinition.getCourse_code(), courseDefinition.getName(), courseDefinition.getDescription()));
            
            TeacherModel teacher = course.getTeacher();
            teachers.add(new TeacherModel(course.getId(), teacher.getName(), teacher.getTutor(), teacher.getAssistant_tutor(), teacher.getQualification()));
        }

        // Add the list of DTOs to the model
        model.addAttribute("cdefinitions", cdefinitions);
        model.addAttribute("teachers", teachers);
        return "Question5";
    }
    @GetMapping("/listCourseByDepartmentAndSemesterForm")
    public ResponseEntity<Map<String, Object>> getStudentsForm() {
        Map<String, Object> response = new HashMap<>();
        response.put("courseModel", new CourseModel());
        response.put("semesters", semesterRepository.findAll());
        response.put("academicUnits", academicUnitRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
  
    @PostMapping("/listCourseByDepartmentAndSemester")
    public ResponseEntity<Map<String, List<?>>> showStudentBySemesterAndDepartment(@RequestBody CourseModel courseModel) {
        String selectedSemesterId = courseModel.getSemester();
        String selectedDepartmentId = courseModel.getAcademic_unit_id();

        // Use the selected semesterId and departmentId to fetch data from the repository
        List<CourseModel> courses = courseRepository.findBySemesterAndAcademicUnitCode(selectedSemesterId, selectedDepartmentId);

        // Create lists to hold combined information
        
        List<TeacherModel> teachers = new ArrayList<>();
        List<CDefinitionModel> cdefinitions = new ArrayList<>();

        // Iterate through registrations and extract relevant information
        for (CourseModel course : courses) {
           
   

            TeacherModel teacher = course.getTeacher();
            CDefinitionModel courseDefinition = course.getCourseDefinition();
            teachers.add(new TeacherModel(course.getId(), course.getTeacher().getName(), course.getTeacher().getTutor(), course.getTeacher().getAssistant_tutor(), course.getTeacher().getQualification()));
            cdefinitions.add(new CDefinitionModel(course.getId(), courseDefinition.getCourse_code(), courseDefinition.getName(), courseDefinition.getDescription()));
        }

        // Create a map to hold the lists
        Map<String, List<?>> responseMap = new HashMap<>();
        responseMap.put("teachers", teachers);
        responseMap.put("cdefinitions", cdefinitions);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @GetMapping("/listCourseByStudentForm")
    public ResponseEntity<Map<String, Object>> getCoursesByStudentForm() {
        Map<String, Object> response = new HashMap<>();
        response.put("courseModel", new CourseModel());
        response.put("students", studentRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/listCourseByStudent")
    public ResponseEntity<Map<String, List<?>>> showCoursesByStudent(@RequestBody CourseModel courseModel) {
        String selectedStudentId = courseModel.getStudent_id();

        // Use the selected studentId to fetch data from the repository
        List<CourseModel> courses = courseRepository.findByStudentRegNo(selectedStudentId);

        // Create lists to hold combined information
        List<TeacherModel> teachers = new ArrayList<>();
        List<CDefinitionModel> cdefinitions = new ArrayList<>();

        // Iterate through courses and extract relevant information
        for (CourseModel course : courses) {
            TeacherModel teacher = course.getTeacher();
            CDefinitionModel courseDefinition = course.getCourseDefinition();
            teachers.add(new TeacherModel(course.getId(), teacher.getName(), teacher.getTutor(), teacher.getAssistant_tutor(), teacher.getQualification()));
            cdefinitions.add(new CDefinitionModel(course.getId(), courseDefinition.getCourse_code(), courseDefinition.getName(), courseDefinition.getDescription()));
        }

        // Create a map to hold the lists
        Map<String, List<?>> responseMap = new HashMap<>();
        responseMap.put("teachers", teachers);
        responseMap.put("cdefinitions", cdefinitions);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
    @PostMapping(value = "/saveCourses")
    public ResponseEntity<?> saveCourse(@RequestBody CourseModel course) throws Exception {

        if (course != null) {
            String saveOneCourse = service.save(course);
            if (saveOneCourse != null) {
                return new ResponseEntity<>("courses saved successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("something is wrong!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("you are not allowed to send empty data!", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateCourse")
    public ResponseEntity<String> updateCourse(@RequestBody CourseModel course) {
        String result = service.update(course);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("course  not found or could not be updated", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteCourse")
    public ResponseEntity<String> deleteCourse(@RequestBody CourseModel course) {
        String result = service.delete(course);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Course not found or could not be deleted", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/listAllCourses")
    public ResponseEntity<List<CourseModel>> listAllCourseModels() {
        List<CourseModel> courseModels = service.getAllCourseModels();

        if (!courseModels.isEmpty()) {
            return new ResponseEntity<>(courseModels, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
