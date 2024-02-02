package com.smunity.graduation.domain.accounts.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smunity.graduation.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "accounts_user")
@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false, unique = true)
	private String userName; // 학번

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "is_staff", nullable = false)
	@ColumnDefault("false")
	private Boolean isStaff;

	@Column(name = "is_active", nullable = false)
	@ColumnDefault("true")
	private Boolean isActive;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "year_id")
	private Year year;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;

	// @OneToMany(mappedBy = "author")
	// private List<Respond> responds;
	//
	// @OneToMany(mappedBy = "author")
	// private List<Question> questions;
	//
	// @OneToMany(mappedBy = "author")
	// private List<Answer> answers;
	//
	// @ElementCollection(fetch = FetchType.EAGER)
	// @Builder.Default
	// private List<String> roles = new ArrayList<>();

	public void setYear(Year year) {
		this.year = year;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
