package com.smunity.graduation.domain.graduation.entity;

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

    //학수 번호
    @Column(name = "number", nullable = false)
    private String number;

    //학년 -> 교양은 전체학년
    @Column(name = "grade", nullable = false)
    private String grade;

    //학년 -> 전공인 경우만, 교양은 0
    @Column(name = "semester", nullable = false)
    private String semester;

    //이름
    @Column(name = "name", nullable = false)
    private String name;

    //학점
    @Column(name = "credit", nullable = false)
    private Integer credit;

    //개설학부
    @Column(name = "dept", nullable = false)
    private String dept;

    //교양 : 영역명
    @Column(name = "domain")
    private String domain;

    //교양 : 세부 영역명
    @Column(name = "sub_domain")
    private String subDomain;

    //이수구분
//    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "type", nullable = false)
    private String type;


    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }
}
