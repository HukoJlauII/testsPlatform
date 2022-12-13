package com.example.testsplatform.handlers;

import com.example.testsplatform.entity.Question;
import com.example.testsplatform.entity.Test;
import com.example.testsplatform.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.util.List;

@RepositoryEventHandler(Question.class)
public class QuestionEventHandler {
    @Autowired
    private TestService testService;

    @HandleBeforeDelete
    public void handleAuthorBeforeDelete(Question question) {
        List<Test> tests = testService.findByQuestionInTest(question);
        for (Test test :
                tests) {
            test.getQuestions().remove(question);
        }
        testService.saveAll(tests);
    }
}
