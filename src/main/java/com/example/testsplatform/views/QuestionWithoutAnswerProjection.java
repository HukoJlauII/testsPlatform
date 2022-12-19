package com.example.testsplatform.views;

import com.example.testsplatform.entity.Media;
import com.example.testsplatform.entity.Question;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;
@Projection(
        name = "withoutAnswer",
        types = {Question.class})
public interface QuestionWithoutAnswerProjection {


    String getTitle();

    List<Media> getMedia() ;

    @Value("#{target.answers}")
    List<String> getAnswers();
}
