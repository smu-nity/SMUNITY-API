package com.smunity.graduation.domain.accounts.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "accounts_user")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String userName; // 학번

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_staff", nullable = false)
    @ColumnDefault("false")
    private Boolean isStaff;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("true")
    private Boolean isActive;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year_id")
    private Year year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "user")
    private List<Course> courses;

    // @OneToMany(mappedBy = "author")
    // private List<Respond> responds;
    //
    // @OneToMany(mappedBy = "author")
    // private List<Question> questions;
    //
    // @OneToMany(mappedBy = "author")
    // private List<Answer> answers;
    //
    // @ElementCollection(fetch = FetchType.EAGER)
    // @Builder.Default
    // private List<String> roles = new ArrayList<>();

    public void setYear(Year year) {
        this.year = year;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
