package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Judgement {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column
	private Date createdAt;
    @PrePersist
    protected void onPersist() {
        this.createdAt = new Date();
    }
    
	@Column
	private String name; // 사용자 이름
	@Column
	private int penalty; // 형량
//    @JsonIgnore
//    @ManyToOne(optional = true)
//    private Justice justice; // 재판
    @JsonIgnore
    @ManyToOne(targetEntity=Justice.class, fetch=FetchType.LAZY)
	@JoinColumn(name="justice_id")
	private Justice justice; // 재판
	
}
