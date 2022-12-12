package com.example.testsplatform.handlers;

import com.example.testsplatform.entity.Media;
import com.example.testsplatform.entity.Question;
import com.example.testsplatform.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler(Media.class)
public class MediaEventHandler {

    @Autowired
    private QuestionService questionService;

    @HandleBeforeDelete
    public void handleAuthorBeforeDelete(Media media) {
        Question question=questionService.findQuestionByImageInMedia(media);
        question.getMedia().remove(media);
        questionService.save(question);
    }


}
