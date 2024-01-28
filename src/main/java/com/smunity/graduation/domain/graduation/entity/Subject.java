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

    //이름
    @Column(name = "name", nullable = false)
    private String name;

    //학점
    @Column(name = "credit", nullable = false)
    private Integer credit;

    //개설학부
    @Column(name = "dept", nullable = false)
    private String dept;

    //이수구분
//    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "type", nullable = false)
    private String type;


}
