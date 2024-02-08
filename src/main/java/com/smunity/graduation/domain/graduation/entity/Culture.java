package com.smunity.graduation.domain.graduation.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "graduations_culture")
@Entity
public class Culture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //이수구분
//    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private String type;

    //영역명
//    @Enumerated(EnumType.STRING)
    @Column(name = "domain", nullable = false)
    private String cultureType;

    //세부영역명
//    @Enumerated(EnumType.STRING)
    @Column(name = "subdomain", nullable = false)
    private String cultureSubType;

    @Column(name = "count")
    private Integer count;
    
    //과목
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;


}
