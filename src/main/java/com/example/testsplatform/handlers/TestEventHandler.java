package com.example.testsplatform.handlers;

import com.example.testsplatform.entity.Test;
import com.example.testsplatform.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.util.List;

@RepositoryEventHandler(Test.class)
public class TestEventHandler {
    @Autowired
    private TestService testService;

    @HandleBeforeDelete
    public void changePreviousTest(Test test) {

            List<Test> tests = testService.findByPreviousTest(test);
            tests.forEach(t -> {
                t.setPreviousTest(test.getPreviousTest());
            });
            testService.saveAll(tests);

    }

}
