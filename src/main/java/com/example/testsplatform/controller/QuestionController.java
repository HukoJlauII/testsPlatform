package com.example.testsplatform.controller;

import com.example.testsplatform.entity.Question;
import com.example.testsplatform.repository.QuestionRepository;
import com.example.testsplatform.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/create")
    public Question createQuestion(@RequestBody Question question, @RequestParam("formFile") List<MultipartFile> multipartFile)
    {
        return questionService.save(question);
    }
}
