package com.smunity.graduation.domain.accounts.entity;

import com.smunity.graduation.global.common.enums.SubDomain;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "accounts_department")
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String college;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SubDomain subDomain;

    private String url;
}
