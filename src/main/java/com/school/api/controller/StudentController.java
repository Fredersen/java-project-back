package com.school.api.controller;

import com.school.api.dto.ClassDTO;
import com.school.api.dto.StudentDTO;
import com.school.api.model.Class;
import com.school.api.model.School;
import com.school.api.model.Student;
import com.school.api.repository.ClassRepository;
import com.school.api.repository.SchoolRepository;
import com.school.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page, limit);
        Page<Student> students;

        if (search != null) {
            students = studentRepository.searchAllFields(search, pageable);
        } else {
            students = studentRepository.findAll(pageable);
        }

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        return studentOpt.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/class/{id}")
    public ResponseEntity<Page<Student>> getStudentsByClassId(
            @PathVariable("id") Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        Optional<Class> classOpt = classRepository.findById(id);
        if (classOpt.isPresent()) {
            Class classObj = classOpt.get();
            Pageable pageable = PageRequest.of(page, limit);
            Page<Student> students = studentRepository.findAllByClassRoom(classObj, pageable);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") Long id) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isPresent()) {
            studentRepository.delete(studentOpt.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDTO studentDTO) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isPresent()) {
            Student updatedStudent = optionalStudent.get();
            if(studentDTO.getFirstName() != null) {
                updatedStudent.setFirstName(studentDTO.getFirstName());
            }
            if(studentDTO.getLastName() != null) {
                updatedStudent.setLastName(studentDTO.getLastName());
            }
            if(studentDTO.getClassId() != null) {
                Optional<Class> classOpt = classRepository.findById(studentDTO.getClassId());
                if(classOpt.isPresent()) {
                    updatedStudent.setClassRoom(classOpt.get());
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            Student savedStudent = studentRepository.save(updatedStudent);
            return new ResponseEntity<>(savedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
        Optional<Class> classOpt = classRepository.findById(studentDTO.getClassId());
        if(classOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Class clazz = classOpt.get();
        Student student = new Student(studentDTO.getFirstName(), studentDTO.getLastName(), clazz);
        Student savedStudent = studentRepository.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

}
