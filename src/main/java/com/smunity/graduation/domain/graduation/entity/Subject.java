package com.smunity.graduation.domain.graduation.entity;

import com.smunity.graduation.domain.graduation.converter.*;
import com.smunity.graduation.domain.graduation.entity.type.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "graduations_subject")
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private String year;

    //학수 번호
    @Column(name = "number", nullable = false)
    private String number;

    //학년 -> 교양은 전체학년
    @Convert(converter = GradePersistConverter.class)
    @Column(name = "grade")
    private Grade grade;

    //학기 -> 교양은 전체학기
    @Convert(converter = SemesterPersistConverter.class)
    @Column(name = "semester")
    private Semester semester;

    //과목명
    @Column(name = "name", nullable = false)
    private String name;

    //학점
    @Column(name = "credit", nullable = false)
    private Integer credit;

    //개설학부
    @Column(name = "dept", nullable = false)
    private String dept;

    //교양 : 영역명
    @Convert(converter = CultureTypePersistConverter.class)
    @Column(name = "domain")
    private CultureType domain;

    //교양 : 세부 영역명
    @Convert(converter = CultureSubTypePersistConverter.class)
    @Column(name = "sub_domain")
    private CultureSubType subDomain;

    @Column(name = "count")
    private Integer count;

    //이수구분
    @Convert(converter = SubjectTypePersistConverter.class)
    @Column(name = "type", nullable = false)
    private SubjectType type;

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public void setDomain(CultureType domain) {
        this.domain = domain;
    }

    public void setSubDomain(CultureSubType subDomain) {
        this.subDomain = subDomain;
    }
}
