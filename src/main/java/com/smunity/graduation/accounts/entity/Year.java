package com.smunity.graduation.accounts.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(name = "accounts_year")
@Entity
public class Year {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "year", nullable = false)
	private String year;

	@Column(name = "major_i")
	private int majorI;

	@Column(name = "major_s")
	private int majorS;

	@Column(name = "culture")
	private int culture;

	@Column(name = "culture_cnt")
	private int cultureCnt;

	// TODO MySQL의 keyword와 충돌. 컬럼명 수정 필요
	// @Column(name = "all")
	// private int all;

	@OneToMany(mappedBy = "year")
	private List<Profile> profiles;
}
