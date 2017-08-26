package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.Data;

@Entity
@Data
public class Pleading {
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private Date createdAt;
    @PrePersist
    protected void onPersist() {
        this.createdAt = new Date();
    }
    
    @Column
    private String issue; // 사안
	@Column
	private String prosecutorArgument; // 검사
	@Column
	private String[] attorneyArgument; // 변호사
	@Column
	private String judgment; // 판사
	@Column
	private String articleUrl; // 관련기사 URL
	
}
