package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Justice {
	
	@Id
	private Long id;
	
	@Column
	private String no; // 사건 번호 
	@Column
	private String title; // 사건명
	@Column
	private String undertrial; // 피고인
	@Column
	private String prosecutor; // 검사
	@Column
	private String attorney; // 변호사

}