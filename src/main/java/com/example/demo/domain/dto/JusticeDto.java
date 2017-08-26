package com.example.demo.domain.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class JusticeDto {
	
	private String no; // 사건 번호 
	private String title; // 사건명
	private String allegation; // 혐의
	private String undertrial; // 피고인
	private String plaintiff; // 원고
	private String prosecution; // 기소
	private String prosecutor; // 검사
	private String attorney; // 변호사
	private String judge; // 판사
	private String judgment; // 판결
	private String venue; // 법정
}