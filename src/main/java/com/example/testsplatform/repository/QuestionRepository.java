package com.example.testsplatform.repository;

import com.example.testsplatform.entity.Media;
import com.example.testsplatform.entity.Question;
import com.example.testsplatform.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "questions", path = "questions")
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select q from Question q join q.media m where m = ?1")
    Question findQuestionByImageInMedia(Media media);

    @RestResource(path = "/inTest")
    @Query("select t.questions from Test t where t.id=?1")
    List<Question> findQuestionsInTest(Long id);
}