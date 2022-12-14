package com.example.testsplatform.entity;


import com.example.testsplatform.views.TestView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tests", schema = "jpa")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Test {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(TestView.TestForMainPage.class)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonView(TestView.TestForMainPage.class)
    private String title;

    @Transient
    @JsonView(TestView.TestForMainPage.class)
    private boolean available = false;

    //    @RestResource(exported = false)
    @ManyToMany(targetEntity = Question.class, fetch = FetchType.LAZY)
    @JoinTable(name = "test_question",
            joinColumns = {@JoinColumn(name = "test_id")},
            inverseJoinColumns = {@JoinColumn(name = "question_id")})
    private Set<Question> questions = new HashSet<>();

    @JsonIgnoreProperties(value = "previousTest", allowSetters = true)
    @JsonView(TestView.TestForMainPage.class)
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Test previousTest;

}
