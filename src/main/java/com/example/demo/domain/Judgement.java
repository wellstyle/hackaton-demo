package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Judgement {
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
	private String userName; // 사용자 이름
	@Column
	private int penalty; // 형량
    @ManyToOne(optional = false)
    @JsonIgnore
    private Justice justice; // 재판
	
}
