package com.smunity.graduation.accounts.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "auth_user")
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false, unique = true)
	private String userName;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "is_superuser", nullable = false)
	private Boolean isSuperUser;

	@Column(name = "isStaff", nullable = false)
	private Boolean isStaff;

	@Column(name = "isActive", nullable = false)
	private Boolean isActive;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@Column(name = "date_joined")
	@CreatedDate
	private LocalDateTime dateJoined;

	// @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	// private Profile profile;

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

}
