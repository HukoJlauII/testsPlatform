package com.example.testsplatform.repository;

import com.example.testsplatform.entity.Media;
import com.example.testsplatform.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "questions", path = "questions")
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select q from Question q join q.media m where m = ?1")
    Question findQuestionByImageInMedia(Media media);

}