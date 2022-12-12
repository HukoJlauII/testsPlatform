package com.example.testsplatform.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "test_question",
            joinColumns = {@JoinColumn(name = "test_id")},
            inverseJoinColumns = {@JoinColumn(name = "question_id")})
    private List<Question> questions = new ArrayList<>();


    @OneToOne(fetch = FetchType.EAGER)
    private Test previousTest;
}
