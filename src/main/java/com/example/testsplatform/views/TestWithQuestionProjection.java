package com.example.testsplatform.views;

import com.example.testsplatform.entity.Question;
import com.example.testsplatform.entity.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;
import java.util.Set;

@Projection(
        name = "fullTest",
        types = {Test.class})
public interface TestWithQuestionProjection {
    @Value("#{target.id}")
    long getId();

    @Value("#{target.title}")
    String getTitle();


    Set<Question> getQuestions();


}
