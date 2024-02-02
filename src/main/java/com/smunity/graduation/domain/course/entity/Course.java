package com.smunity.graduation.domain.course.entity;

import com.smunity.graduation.domain.accounts.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "core_course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	//    TODO Subject 엔티티가 생성된 후 FK 연결
	//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//    @JoinColumn(name = "subject_id")
	//    private Subject subject;

	// @Column(nullable = false)
	// private Long subjectId;

	@Column(nullable = false)
	private String year;

	@Column(nullable = false)
	private String semester;

	@Column(nullable = false)
	private String type;

	private String domain;

	@Column(nullable = false)
	private int credit;

	@Column(nullable = false)
	private boolean custom;
}
