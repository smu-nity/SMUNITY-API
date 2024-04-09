package com.smunity.graduation.domain.course.entity;

import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.global.common.type.Domain;
import com.smunity.graduation.global.common.type.SubDomain;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "core_curriculum")
public class Curriculum implements SubDomainHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year_id")
    private Year year;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Domain domain;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubDomain subDomain;
}
