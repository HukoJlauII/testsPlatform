package com.example.testsplatform.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "questions", schema = "jpa")
@Getter
@Setter
@NoArgsConstructor
@ToString

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty("title")
    private String title;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "question_media",
            joinColumns = {@JoinColumn(name = "question_id")},
            inverseJoinColumns = {@JoinColumn(name = "media_id")})
    @JsonIgnoreProperties(value = "bytes", allowSetters = true)
    private List<Media> media = new ArrayList<>();

    @JsonProperty("answers")
    @ElementCollection(fetch = FetchType.LAZY)
    @JoinColumn()
    private List<String> answers;

    @JsonProperty("rightAnswer")
    private String rightAnswer;


}
