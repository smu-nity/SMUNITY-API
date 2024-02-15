package com.smunity.graduation.domain.course.entity;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.graduation.entity.Subject;
import jakarta.persistence.*;
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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "subject_id")
	private Subject subject;
}
