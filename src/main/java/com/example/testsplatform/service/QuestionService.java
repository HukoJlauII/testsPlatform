package com.example.testsplatform.service;

import com.example.testsplatform.entity.Media;
import com.example.testsplatform.entity.Question;
import com.example.testsplatform.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public Question findQuestionByImageInMedia(Media media)
    {
        return questionRepository.findQuestionByImageInMedia(media);
    }

    public Question createQuestion(Question question, List<MultipartFile> multipartFiles) throws IOException {
        if (multipartFiles != null) {
            for (MultipartFile file :
                    multipartFiles) {
                question.getMedia().add(new Media(file.getOriginalFilename(), file.getSize(), file.getContentType(), file.getBytes()));
            }
        }
        return save(question);
    }
    public Question updateQuestion(Question question, List<MultipartFile> multipartFiles) throws IOException {
        Question questionFromDB=questionRepository.findById(question.getId()).orElse(null);
        if (questionFromDB!=null) {
//            questionFromDB.setTitle(question.getTitle());
//            questionFromDB.setAnswers(question.getAnswers());
//            questionFromDB.setRightAnswer(question.getRightAnswer());
            question.setMedia(questionFromDB.getMedia());
            if (multipartFiles != null) {
                for (MultipartFile file :
                        multipartFiles) {
                    question.getMedia().add(new Media(file.getOriginalFilename(), file.getSize(), file.getContentType(), file.getBytes()));
                }
            }
            return save(question);
        }else return null;
    }
}
