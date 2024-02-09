package com.smunity.graduation.domain.course.entity;

import com.smunity.graduation.domain.accounts.entity.User;
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

    @Column(name = "subject_name", nullable = false)
    private String name;

    @Column(name = "subject_number", nullable = false, length = 8)
    private String number;

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
}
