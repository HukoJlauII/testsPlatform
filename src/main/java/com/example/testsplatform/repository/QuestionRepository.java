package com.example.testsplatform.repository;

import com.example.testsplatform.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "questions", path = "questions")
public interface QuestionRepository extends JpaRepository<Question, Long> {
}