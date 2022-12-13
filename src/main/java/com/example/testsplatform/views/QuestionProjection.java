package com.example.testsplatform.views;

import com.example.testsplatform.entity.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "questionPreview",
        types = {Question.class})
public interface QuestionProjection {
    @Value("#{target.id}")
    long getId();

    @Value("#{target.title}")
    String getTitle();
}
