package com.school.api.repository;

import com.school.api.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.school.api.model.Class;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    @Query("SELECT c FROM Class c WHERE " +
            "LOWER(c.name) LIKE %:search% OR " +
            "LOWER(c.level) LIKE %:search% ")
    Page<Class> searchAllFields(@Param("search") String search, Pageable pageable);
}
