package com.example.testsplatform.service;

import com.example.testsplatform.entity.Question;
import com.example.testsplatform.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {


    @Autowired
    private QuestionRepository questionRepository;

    public Question save(Question question)
    {
        return questionRepository.save(question);
    }
}
