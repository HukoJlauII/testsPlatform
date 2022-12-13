package com.example.testsplatform.repository;

import com.example.testsplatform.entity.Question;
import com.example.testsplatform.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "tests", path = "tests")
public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("select t from Test t join t.questions q where ?1 in q")
    List<Test> findByQuestionInTest(Question question);

    @Query("select t from Test t where t.previousTest = ?1")
    List<Test> findByPreviousTest(Test previousTest);




}