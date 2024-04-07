package com.smunity.graduation.domain.course.entity;

import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.global.common.type.Category;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "core_standard")
public class Standard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year_id")
    private Year year;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private int total;
}
