package com.example.testsplatform.views;

import com.example.testsplatform.entity.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "testPreview",
        types = {Test.class})
public interface TestProjection {
    @Value("#{target.id}")
    long getId();

    @Value("#{target.title}")
    String getTitle();
}
