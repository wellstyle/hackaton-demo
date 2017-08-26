package com.example.demo.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Judgement;
import com.example.demo.domain.JudgementRepository;
import com.example.demo.domain.JusticeRepository;
import com.example.demo.domain.dto.JudgementDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class JudgementService {
	
	@Autowired
	JusticeRepository justiceRepository;
	@Autowired
	JudgementRepository judgementRepository;

	public Judgement save(JudgementDto judgementDto) {
		Judgement judgement = new Judgement();
		judgement.setName(judgementDto.getName());
		judgement.setPenalty(judgementDto.getPenalty());
		judgement.setJustice(justiceRepository.findOne(judgementDto.getJusticeId()));
		return judgementRepository.save(judgement);
	}
}
