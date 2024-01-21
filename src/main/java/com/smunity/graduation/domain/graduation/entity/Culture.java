package com.smunity.graduation.domain.graduation.entity;

import com.smunity.graduation.domain.graduation.entity.type.CultureSubType;
import com.smunity.graduation.domain.graduation.entity.type.CultureType;
import com.smunity.graduation.domain.graduation.entity.type.SubjectType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "graduations_culture")
@Entity //교양 엔티티
public class Culture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //이수구분
    @Column(name = "type", nullable = false)
    private SubjectType type;

    //영역명
    @Column(name = "domain", nullable = false)
    private CultureType cultureType;

    //세부영역명
    @Column(name = "subdomain", nullable = false)
    private CultureSubType cultureSubType;

    //과목
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;


}
