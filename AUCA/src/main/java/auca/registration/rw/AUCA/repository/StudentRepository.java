package auca.registration.rw.AUCA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.registration.rw.AUCA.model.Student;
import jakarta.transaction.Transactional;

public interface StudentRepository extends JpaRepository<Student, String>{

	 Student findByRegNo(String regNo);

}
