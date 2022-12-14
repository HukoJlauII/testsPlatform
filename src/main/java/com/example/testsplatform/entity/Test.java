package com.example.testsplatform.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    @Transient
    private boolean available = false;

    //    @RestResource(exported = false)
    @ManyToMany(targetEntity = Question.class, fetch = FetchType.LAZY)
    @JoinTable(name = "test_question",
            joinColumns = {@JoinColumn(name = "test_id")},
            inverseJoinColumns = {@JoinColumn(name = "question_id")})
    private Set<Question> questions = new HashSet<>();

    @JsonIgnoreProperties(value = "previousTest", allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Test previousTest;

}
