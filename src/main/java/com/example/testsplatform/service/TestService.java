package com.example.testsplatform.service;

import com.example.testsplatform.entity.Question;
import com.example.testsplatform.entity.Test;
import com.example.testsplatform.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public Test save(Test test) {
        return testRepository.save(test);
    }

    public List<Test> saveAll(List<Test> tests) {
        return testRepository.saveAll(tests);
    }

    public List<Test> findByQuestionInTest(Question question) {
        return testRepository.findByQuestionInTest(question);
    }
    public List<Test> findByPreviousTest(Test previousTest){
        return testRepository.findByPreviousTest(previousTest);
    }
}
