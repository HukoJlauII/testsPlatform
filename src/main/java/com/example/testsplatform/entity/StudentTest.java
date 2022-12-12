package com.example.testsplatform.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tests", schema = "jpa")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    private User user;

    @OneToOne(targetEntity = Test.class,fetch = FetchType.LAZY)
    private Test test;

    private boolean isPassed;

}
