package com.example.testsplatform.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class StudentTestKey implements Serializable {

    @Serial
    private static final long serialVersionUID = 6465803568458008259L;
    @Column(name = "test_id")
    Long testId;

    @Column(name = "user_id")
    Long userId;


}
