package com.example.testsplatform.controller;

import com.example.testsplatform.entity.Media;
import com.example.testsplatform.entity.Question;
import com.example.testsplatform.service.QuestionService;
import com.example.testsplatform.views.QuestionView;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping(value = "/create")
    public Question createQuestion(@RequestParam("question") String jsonString, @RequestParam(value = "file", required = false) List<MultipartFile> multipartFiles) throws IOException {
        Question question = new ObjectMapper().readValue(jsonString, Question.class);
        return questionService.createQuestion(question,multipartFiles);
    }

    @PutMapping(value = "/change")
    public Question changeQuestion(@RequestParam("question") String jsonString, @RequestParam(value = "file", required = false) List<MultipartFile> multipartFiles) throws IOException {
        Question question = new ObjectMapper().readValue(jsonString, Question.class);
        return questionService.updateQuestion(question,multipartFiles);
    }

    @JsonView(QuestionView.QuestionPreview.class)
    @GetMapping("/all")
    public List<Question> showAllQuestions() {
        return questionService.findAll();
    }
}
