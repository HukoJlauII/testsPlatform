package com.example.testsplatform.repository;

import com.example.testsplatform.entity.StudentTest;
import com.example.testsplatform.entity.Test;
import com.example.testsplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "studtests", path = "studtests")
public interface StudentTestRepository extends JpaRepository<StudentTest, Long> {

    @Query("select s.test from StudentTest s where s.user = ?1 and s.isPassed = true")
    List<Test> findStudentTestsByUser(User user);
}