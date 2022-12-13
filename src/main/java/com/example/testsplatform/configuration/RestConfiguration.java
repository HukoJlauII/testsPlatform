package com.example.testsplatform.configuration;

import com.example.testsplatform.entity.Media;
import com.example.testsplatform.entity.Question;
import com.example.testsplatform.entity.Test;
import com.example.testsplatform.entity.User;
import com.example.testsplatform.handlers.MediaEventHandler;
import com.example.testsplatform.handlers.QuestionEventHandler;
import com.example.testsplatform.handlers.TestEventHandler;
import com.example.testsplatform.views.QuestionProjection;
import com.example.testsplatform.views.TestProjection;
import com.example.testsplatform.views.TestWithQuestionProjection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Bean
    MediaEventHandler mediaEventHandler() {
        return new MediaEventHandler();
    }

    @Bean
    QuestionEventHandler questionEventHandler() {
        return new QuestionEventHandler();
    }

    @Bean
    TestEventHandler testEventHandler() {
        return new TestEventHandler();
    }

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Media.class);
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(Test.class);
        config.exposeIdsFor(Question.class);
        config.getProjectionConfiguration().addProjection(QuestionProjection.class);
        config.getProjectionConfiguration().addProjection(TestProjection.class).addProjection(TestWithQuestionProjection.class);
    }
}
