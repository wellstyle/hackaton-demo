package com.example.demo.domain.dto;

import lombok.Data;

@Data
public class JudgementDto {
	
	private String userName; // 사용자 이름
	private int penalty; // 형량
    private Long justiceId; // 재판 아이디
}
