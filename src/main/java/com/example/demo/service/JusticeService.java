package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.JudgementRepository;
import com.example.demo.domain.Justice;
import com.example.demo.domain.JusticeRepository;
import com.example.demo.domain.dto.JusticeStatisticDto;

@Service
public class JusticeService {

	@Autowired
	JusticeRepository justiceRepository;
	@Autowired
	JudgementRepository judgementRepository;
	
	// 사건 판결에 대한 참여 통계 정보
	public JusticeStatisticDto getStatistic(Long id) {
		JusticeStatisticDto justiceStatisticDto = new JusticeStatisticDto();
		Justice justice = justiceRepository.findOne(id);
		List<Object[]> ratingCount = justiceRepository.countByPenalty(justice);
		long averagePenalty = justiceRepository.averagePenalty(justice);
		long totalCount = justiceRepository.totalCount(justice);
		justiceStatisticDto.setTotalCountByPenalty(ratingCount);
		justiceStatisticDto.setAveragePenalty(averagePenalty);
		justiceStatisticDto.setTotalCount(totalCount);
		
		return justiceStatisticDto;
	}
	
}
