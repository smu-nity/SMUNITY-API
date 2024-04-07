package com.smunity.graduation.domain.accounts.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "accounts_year")
@Entity
public class Year {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String year;

    @Column(name = "major_i")
    private Integer majorI;

    @Column(name = "major_s")
    private Integer majorS;

    private Integer culture;

    private Integer cultureCnt;

    private Integer total;
}
