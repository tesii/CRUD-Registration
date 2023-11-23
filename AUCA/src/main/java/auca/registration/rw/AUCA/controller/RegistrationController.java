package auca.registration.rw.AUCA.controller;

import auca.registration.rw.AUCA.model.AcademicUnit;
import auca.registration.rw.AUCA.model.CourseModel;
import auca.registration.rw.AUCA.model.RegistrationModel;
import auca.registration.rw.AUCA.model.Semester;
import auca.registration.rw.AUCA.model.Student;
import auca.registration.rw.AUCA.repository.AcademicRepository;
import auca.registration.rw.AUCA.repository.CourseRepository;
import auca.registration.rw.AUCA.repository.RegistrationRepository;
import auca.registration.rw.AUCA.repository.SemesterRepository;
import auca.registration.rw.AUCA.repository.StudentRepository;
import auca.registration.rw.AUCA.service.RegistrationService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService; // Make sure to create this service class

    @Autowired
    private RegistrationRepository registrationRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private AcademicRepository academicUnitRepository;

    @GetMapping("/registrationForm")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationModel", new RegistrationModel());
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("semesters", semesterRepository.findAll());
        model.addAttribute("academicUnits", academicUnitRepository.findAll());
        return "showRegistrationForm";
    }
    @GetMapping("/registration")
    public String insertRegistration() {
        return "insertRegistration"; // Create a Thymeleaf template for the registration form
    }



    @GetMapping("/listregistrations")
    public ModelAndView listRegistrations() {
        List<RegistrationModel> registrationList = registrationService.listRegistrations();
        return new ModelAndView("listregistrations", "registrations", registrationList);
        // Create a Thymeleaf template for listing registrations
    }
    @PostMapping("/saveregistration")
    public String saveRegistration(@ModelAttribute RegistrationModel registration) {
        registrationService.save(registration);
        return "redirect:/listregistrations";
    }

    @GetMapping("/listStudentBySemesterForm")
    public String getStudentForm(Model model) {
        model.addAttribute("registrationModel", new RegistrationModel());
        model.addAttribute("semesters", semesterRepository.findAll());
        return "Question1";
    }


    @PostMapping("/listStudentsBySemester")
    public String showStudentsBySemester(@ModelAttribute RegistrationModel registrationModel, Model model) {
        String selectedSemesterId = registrationModel.getSemester_id();
        
        // Use the selected semesterId to fetch data from the repository
        List<RegistrationModel> registrations = registrationRepository.findBySemester_id(selectedSemesterId);

        // Create a list of DTOs (Data Transfer Objects) to hold combined information
        List<Student> students = new ArrayList<>();

        // Iterate through registrations and extract relevant information
        for (RegistrationModel registration : registrations) {
            Student student = registration.getStudent();
            students.add(new Student(registration.getStudent_id(), student.getFirstName(), student.getDateOfBirth()));
        }

        // Add the list of DTOs to the model
        model.addAttribute("students", students);

        return "Question1";
    }

    @GetMapping("/listStudentBySemestersForm")
    public ResponseEntity<List<Semester>> getStudentForm() {
        List<Semester> semesters = semesterRepository.findAll();
        return new ResponseEntity<>(semesters, HttpStatus.OK);
    }

    @PostMapping("/listStudentsBySemesters")
    public ResponseEntity<List<Student>> showStudentsBySemester(@RequestBody RegistrationModel registrationModel) {
        String selectedSemesterId = registrationModel.getSemester_id();
        
        // Use the selected semesterId to fetch data from the repository
        List<RegistrationModel> registrations = registrationRepository.findBySemester_id(selectedSemesterId);

        // Create a list of DTOs (Data Transfer Objects) to hold combined information
        List<Student> students = new ArrayList<>();

        // Iterate through registrations and extract relevant information
        for (RegistrationModel registration : registrations) {
            Student student = registration.getStudent();
            students.add(new Student(registration.getStudent_id(), student.getFirstName(), student.getDateOfBirth()));
        }

        return new ResponseEntity<>(students, HttpStatus.OK);
    }


    @GetMapping("/listStudentBySemesterAndDepartmentForm")
    public String getStudentsForm(Model model) {
        model.addAttribute("registrationModel", new RegistrationModel());
        model.addAttribute("semesters", semesterRepository.findAll());
        model.addAttribute("academicUnits", academicUnitRepository.findAll());
        return "Question2";
    }


    @PostMapping("/listStudentsBySemesterAndDepartment")
    public String showStudentBySemester(@ModelAttribute RegistrationModel registrationModel, Model model) {
        String selectedSemesterId = registrationModel.getSemester_id();
        String selectedDepartmentId = registrationModel.getAcademic_unit_id(); // Assuming you have a department_id field in RegistrationModel

        // Use the selected semesterId and departmentId to fetch data from the repository
        List<RegistrationModel> registrations = registrationRepository.findBySemesterIdAndAcademicUnitCode(selectedSemesterId, selectedDepartmentId );


        // Create a list of DTOs (Data Transfer Objects) to hold combined information
        List<Student> students = new ArrayList<>();

        // Iterate through registrations and extract relevant information
        for (RegistrationModel registration : registrations) {
            Student student = registration.getStudent();
            students.add(new Student(registration.getStudent_id(), student.getFirstName(), student.getDateOfBirth()));
        }

        // Add the list of DTOs to the model
        model.addAttribute("students", students);

        return "Question2";
    }
    
    @GetMapping("/listStudentBySemesterAndDepartmentsForm")
    public ResponseEntity<Map<String, Object>> getStudentsForm() {
        Map<String, Object> response = new HashMap<>();
        response.put("registrationModel", new RegistrationModel());
        response.put("semesters", semesterRepository.findAll());
        response.put("academicUnits", academicUnitRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
  
    @PostMapping("/listStudentsBySemesterAndDepartments")
    public ResponseEntity<List<Student>> showStudentBySemesterAndDepartment(@RequestBody RegistrationModel registrationModel) {
        String selectedSemesterId = registrationModel.getSemester_id();
        String selectedDepartmentId = registrationModel.getAcademic_unit_id();

        // Use the selected semesterId and departmentId to fetch data from the repository
        List<RegistrationModel> registrations = registrationRepository.findBySemesterIdAndAcademicUnitCode(selectedSemesterId, selectedDepartmentId);

        // Create a list of DTOs (Data Transfer Objects) to hold combined information
        List<Student> students = new ArrayList<>();

        // Iterate through registrations and extract relevant information
        for (RegistrationModel registration : registrations) {
            Student student = registration.getStudent();
            students.add(new Student(registration.getStudent_id(), student.getFirstName(), student.getDateOfBirth()));
        }

        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("/listStudentByCourseAndSemesterForm")
    public String getStudentsByCourseAndSemesterForm(Model model) {
        model.addAttribute("registrationModel", new RegistrationModel());
        model.addAttribute("semesters", semesterRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());
        return "Question3";
    }


    @PostMapping("/listStudentsByCourseAndSemester")
    public String showStudentByCourseAndSemester(@ModelAttribute RegistrationModel registrationModel, Model model) {
        String selectedSemesterId = registrationModel.getSemester_id();
        String selectedCourseId = registrationModel.getCourse_id(); // Assuming you have a department_id field in RegistrationModel

        // Use the selected semesterId and departmentId to fetch data from the repository
        List<RegistrationModel> registrations = registrationRepository.findBySemesterIdAndCourseId(selectedSemesterId, selectedCourseId );


        // Create a list of DTOs (Data Transfer Objects) to hold combined information
        List<Student> students = new ArrayList<>();

        // Iterate through registrations and extract relevant information
        for (RegistrationModel registration : registrations) {
            Student student = registration.getStudent();
            students.add(new Student(registration.getStudent_id(), student.getFirstName(), student.getDateOfBirth()));
        }

        // Add the list of DTOs to the model
        model.addAttribute("students", students);

        return "Question3";
    }

    @GetMapping("/listStudentByCoursesAndSemesterForm")
    public ResponseEntity<Map<String, Object>> getStudentssForm() {
        Map<String, Object> response = new HashMap<>();
        response.put("registrationModel", new RegistrationModel());
        response.put("semesters", semesterRepository.findAll());
        response.put("courses", courseRepository.findAll()); // Assuming you have a repository for courses.
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/listStudentsByCoursesAndSemester")
    public ResponseEntity<List<Student>> showStudentByCoursesAndSemester(@RequestBody RegistrationModel registrationModel) {
        String selectedSemesterId = registrationModel.getSemester_id();
        String selectedCourseId = registrationModel.getCourse_id(); // Assuming "course_ID" is the course ID.

        // Use the selected semesterId and courseId to fetch data from the repository
        List<RegistrationModel> registrations = registrationRepository.findBySemesterIdAndCourseId(selectedSemesterId, selectedCourseId);

        // Create a list of DTOs (Data Transfer Objects) to hold combined information
        List<Student> students = new ArrayList<>();

        // Iterate through registrations and extract relevant information
        for (RegistrationModel registration : registrations) {
            Student student = registration.getStudent();
            students.add(new Student(registration.getStudent_id(), student.getFirstName(), student.getDateOfBirth()));
        }

        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @PostMapping(value = "/saveRegistrations")
    public ResponseEntity<?> saveRegistrations(@RequestBody RegistrationModel registration) throws Exception {

        if (registration != null) {
            String saveOneRegistration = registrationService.save(registration);
            if (saveOneRegistration != null) {
                return new ResponseEntity<>("registrations saved successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("something is wrong!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("you are not allowed to send empty data!", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateRegistration")
    public ResponseEntity<String> updateRegistration(@RequestBody RegistrationModel registration) {
        String result = registrationService.update(registration);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Registration not found or could not be updated", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteRegistration")
    public ResponseEntity<String> deleteRegistration(@RequestBody RegistrationModel registration) {
        String result = registrationService.delete(registration);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Registration not found or could not be deleted", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/listAllRegistrations")
    public ResponseEntity<List<RegistrationModel>> listAllRegistrations() {
        List<RegistrationModel> registrations = registrationService.getAllRegistrations();

        if (!registrations.isEmpty()) {
            return new ResponseEntity<>(registrations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
