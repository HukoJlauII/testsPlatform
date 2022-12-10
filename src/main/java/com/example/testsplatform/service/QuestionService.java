package com.example.testsplatform.service;

import com.example.testsplatform.entity.Question;
import com.example.testsplatform.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {


    @Autowired
    private QuestionRepository questionRepository;

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> findAll() {
        return questionRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
    }
}
