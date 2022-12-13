package com.example.testsplatform.entity;

import com.example.testsplatform.views.QuestionView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions", schema = "jpa")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Question {

    @JsonView(QuestionView.QuestionPreview.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonView(QuestionView.QuestionPreview.class)
    @JsonProperty("title")
    private String title;

    @JsonView(QuestionView.FullQuestion.class)
    @RestResource(exported = false, path = "media", rel = "media")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "question_media",
            joinColumns = {@JoinColumn(name = "question_id")},
            inverseJoinColumns = {@JoinColumn(name = "media_id")})
    @JsonIgnoreProperties(value = "bytes", allowSetters = true)
    private List<Media> media = new ArrayList<>();

    @JsonView(QuestionView.FullQuestion.class)
    @JsonProperty("answers")
    @ElementCollection(fetch = FetchType.LAZY)
    @JoinColumn()
    private List<String> answers;

    @JsonView(QuestionView.FullQuestion.class)
    @JsonProperty("rightAnswer")
    private String rightAnswer;


}
