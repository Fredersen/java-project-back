package com.school.api.repository;

import com.school.api.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.school.api.model.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    @Query("SELECT s FROM School s WHERE " +
            "LOWER(s.name) LIKE %:search% OR " +
            "LOWER(s.city) LIKE %:search% OR " +
            "LOWER(s.type) LIKE %:search% ")
    Page<School> searchAllFields(@Param("search") String search, Pageable pageable);
}
