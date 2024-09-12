package com.smunity.graduation.domain.accounts.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.global.common.entity.BaseEntity;
import com.smunity.graduation.global.common.type.Category;
import com.smunity.graduation.global.common.type.SubDomain;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.smunity.graduation.global.common.type.SubDomain.*;

@Getter
@Builder
@DynamicInsert
@DynamicUpdate
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
    private boolean isStaff;

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
    private List<Course> courses = new ArrayList<>();

    @Column(name = "current_year")
    private int currentYear;

    @Column(name = "completed_semester")
    private int completedSemesters;

    public void setYear(Year year) {
        this.year = year;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getCompletedCredits() {
        return courses.stream()
                .mapToInt(Course::getCredit)
                .sum();
    }

    public int getCompletedCredits(Category category) {
        return courses.stream()
                .filter(course -> course.getCategory().equals(category))
                .mapToInt(Course::getCredit)
                .sum();
    }

    public List<String> getCompletedNumbers() {
        return courses.stream()
                .map(Course::getNumber)
                .toList();
    }

    public SubDomain getSubDomain() {
        SubDomain subDomain = department.getSubDomain();
        return year.getValue() >= 2024 && (subDomain.equals(BALANCE_NATURAL) || subDomain.equals(BALANCE_ENGINEER)) ? BALANCE_NATURAL_ENGINEER : subDomain;
    }

    public boolean checkCompleted(SubDomain subDomain) {
        return courses.stream()
                .map(Course::getSubDomain)
                .anyMatch(subDomain::equals);
    }
}
