package com.school.api.controller;

import com.school.api.model.School;
import com.school.api.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schools")
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepository;

    @GetMapping
    public ResponseEntity<Page<School>> getAllSchools(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<School> schoolPage;

        if (search != null) {
            schoolPage = schoolRepository.searchAllFields(search, pageable);
        } else {
            schoolPage = schoolRepository.findAll(pageable);
        }

        return new ResponseEntity<>(schoolPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<School> createSchool(@RequestBody School school) {
        School newSchool = schoolRepository.save(school);
        return new ResponseEntity<>(newSchool, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable("id") Long id) {
        Optional<School> school = schoolRepository.findById(id);
        return school.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<School> updateSchool(@PathVariable("id") Long id, @RequestBody School schoolDetails) {
        Optional<School> schoolOptional = schoolRepository.findById(id);

        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();
            school.setName(schoolDetails.getName());
            school.setCity(schoolDetails.getCity());
            school.setType(schoolDetails.getType());
            School updatedSchool = schoolRepository.save(school);
            return new ResponseEntity<>(updatedSchool, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<School> deleteSchool(@PathVariable("id") Long id) {
        Optional<School> schoolOptional = schoolRepository.findById(id);
        if (schoolOptional.isPresent()) {
            schoolRepository.delete(schoolOptional.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
