package com.example.testsplatform.entity;


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

    //    @RestResource(exported = false)
    @ManyToMany(targetEntity = Question.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "test_question",
            joinColumns = {@JoinColumn(name = "test_id")},
            inverseJoinColumns = {@JoinColumn(name = "question_id")})
    private Set<Question> questions = new HashSet<>();


    @OneToOne(fetch = FetchType.EAGER)
    private Test previousTest;

}
