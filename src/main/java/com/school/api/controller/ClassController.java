package com.school.api.controller;

import com.school.api.dto.ClassDTO;
import com.school.api.model.Class;
import com.school.api.model.School;
import com.school.api.repository.ClassRepository;
import com.school.api.repository.SchoolRepository;
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
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @GetMapping
    public ResponseEntity<Page<Class>> getAllClasses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Class> classesPage;

        if (search != null) {
            classesPage = classRepository.searchAllFields(search, pageable);
        } else {
            classesPage = classRepository.findAll(pageable);
        }

        return new ResponseEntity<>(classesPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Class> getClassById(@PathVariable("id") Long id) {
        Optional<Class> classOpt = classRepository.findById(id);
        return classOpt.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/school/{id}")
    public ResponseEntity<Page<Class>> getClassesBySchoolId(
            @PathVariable("id") Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        Optional<School> schoolOpt = schoolRepository.findById(id);
        if (schoolOpt.isPresent()) {
            School school = schoolOpt.get();
            Pageable pageable = PageRequest.of(page, limit);
            Page<Class> classesPage = classRepository.findAllBySchool(school, pageable);
            return new ResponseEntity<>(classesPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Class> deleteClass(@PathVariable("id") Long id) {
        Optional<Class> classOpt = classRepository.findById(id);
        if (classOpt.isPresent()) {
            classRepository.delete(classOpt.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Class> updateClass(@PathVariable("id") Long id, @RequestBody ClassDTO classDTO) {
        Optional<Class> optionalClass = classRepository.findById(id);
        if (optionalClass.isPresent()) {
            Class updatedClass = optionalClass.get();

            if (classDTO.getName() != null) {
                updatedClass.setName(classDTO.getName());
            }
            if (classDTO.getLevel() != null) {
                updatedClass.setLevel(classDTO.getLevel());
            }
            if (classDTO.getSchoolId() != null) {
                Optional<School> optionalSchool = schoolRepository.findById(classDTO.getSchoolId());
                if (optionalSchool.isPresent()) {
                    updatedClass.setSchool(optionalSchool.get());
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }

            Class savedClass = classRepository.save(updatedClass);
            return new ResponseEntity<>(savedClass, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<Class> createClass(@RequestBody ClassDTO classDTO) {
        Optional<School> schoolOpt = schoolRepository.findById(classDTO.getSchoolId());
        if (schoolOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        School school = schoolOpt.get();
        Class newClass = new Class(classDTO.getName(), classDTO.getLevel(), school);
        Class savedClass = classRepository.save(newClass);
        return new ResponseEntity<>(savedClass, HttpStatus.CREATED);
    }
}
