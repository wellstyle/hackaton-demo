package com.example.demo.domain.dto;

import java.util.List;

import com.example.demo.domain.RatingCount;

import lombok.Data;

@Data
public class JusticeStatisticDto {
	private long totalCount; // 총 참여수
	private float averagePenalty; // 평균 형량
	private List<Object[]> totalCountByPenalty; // 형량별 총 참여수
	private long sumPenalty; // 총형량
}
