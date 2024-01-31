package com.smunity.graduation.domain.graduation.temporary;

//import com.smunity.graduation.domain.graduation.entity.Subject;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.graduation.entity.Subject;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "core_course")
public class CourseTemporary {

    @Id
    private Long id;

    @Column
    private Integer year;

    @Column
    private String semester;

    @Column
    private Integer credit;

    //    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private String type;

    @Column
    private String domain;

    //과목
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
