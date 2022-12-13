package com.example.testsplatform.repository;

import com.example.testsplatform.entity.StudentTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "studtests", path = "studtests")
public interface StudentTestRepository extends JpaRepository<StudentTest, Long> {
}