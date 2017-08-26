package com.example.demo.domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Entity
@Data
public class Justice {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date createdAt;
	@Column
	private Date updatedAt;
    @PrePersist
    protected void onPersist() {
        this.createdAt = this.updatedAt = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
	
	@Column
	private String no; // 사건 번호 
	@Column
	private String title; // 사건명
	@Column
	private String allegation; // 혐의
	@Column
	private String undertrial; // 피고인
	@Column
	private String plaintiff; // 원고
	@Column
	private String prosecution; // 기소
	@Column
	private String prosecutor; // 검사
	@Column
	private String attorney; // 변호사
	@Column
	private String judge; // 판사
	@Column
	private String judgment; // 판결
	@Column
	private String venue; // 법정
	@Column
	private String undertrialPhotoUrl; // 피고인 사진 URL
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name="member_id")
	private List<Pleading> pleadings;
}