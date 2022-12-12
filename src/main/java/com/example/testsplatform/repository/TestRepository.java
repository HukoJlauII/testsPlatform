package com.example.testsplatform.repository;

import com.example.testsplatform.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "tests", path = "tests")
public interface TestRepository extends JpaRepository<Test, Long> {

}