package com.example.testsplatform.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "student_tests", schema = "jpa")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentTest {
    @EmbeddedId
    private StudentTestKey id;

    @MapsId("user_id")
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @MapsId("test_id")
    @OneToOne(targetEntity = Test.class, fetch = FetchType.LAZY)
    private Test test;

    private int pointsCount;

    private boolean isPassed;

}
