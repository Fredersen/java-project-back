package com.school.api.repository;

import com.school.api.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE " +
            "LOWER(s.firstName) LIKE %:search% OR " +
            "LOWER(s.lastName) LIKE %:search% ")
    Page<Student> searchAllFields(@Param("search") String search, Pageable pageable);
}