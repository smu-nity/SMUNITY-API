package com.smunity.graduation.domain.graduation.entity;

import com.smunity.graduation.domain.accounts.entity.Department;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "graduations_major")
@Entity
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //학년
//    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false)
    private String grade;

    //학기
//    @Enumerated(EnumType.STRING)
    @Column(name = "semester", nullable = false)
    private String semester;

    //이수구분
//    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private String type;

    //학과
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    //과목
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;


}
