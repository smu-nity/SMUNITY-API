package com.smunity.graduation.domain.course.entity;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.graduation.entity.Subject;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "core_course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private String semester;

    @Column(nullable = false)
    private String type;

    private String domain;

    @Column(nullable = false)
    private int credit;

    @Column(nullable = false)
    private boolean custom;

    public void setUser(User user) {
        this.user = user;
        user.getCourses().add(this);
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        subject.getCourses().add(this);
    }
}
