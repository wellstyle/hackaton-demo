package com.example.demo.web;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.domain.Judgement;
import com.example.demo.domain.JudgementRepository;
import com.example.demo.domain.Justice;
import com.example.demo.domain.JusticeRepository;
import com.example.demo.domain.dto.JudgementDto;
import com.example.demo.domain.dto.JusticeDto;
import com.example.demo.domain.dto.JusticeStatisticDto;
import com.example.demo.service.JusticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Controller
@Api(tags="Demo")
@Slf4j
public class ApiController {
	
	@Autowired
	JusticeRepository justiceRepository;
	@Autowired
	JudgementRepository judgementRepository;
	@Autowired
	JusticeService justiceService;
	@Autowired
    private ModelMapper modelMapper;
	
	@ApiOperation(value="사건 등록")
	@RequestMapping(value="/justices", method=RequestMethod.POST)
	public ResponseEntity<?> addJustice(@Valid @RequestBody JusticeDto justiceDto) {
		Justice justice = modelMapper.map(justiceDto, Justice.class);

		Justice savedJustice = justiceRepository.save(justice);
		log.debug("### {}", savedJustice);
		
		return new ResponseEntity<Justice>(savedJustice, HttpStatus.CREATED);
	}

	@ApiOperation(value="사건 상세조회")
	@RequestMapping(value="/justices/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getJusticeDetail(@PathVariable Long id) {
		
		Justice savedJustice = justiceRepository.findOne(id);
		log.debug("### {}", savedJustice);
		
		return new ResponseEntity<Justice>(savedJustice, HttpStatus.OK);
	}
	
	@ApiOperation(value="사건 판결 통계")
	@RequestMapping(value="/justices/statistic/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getJusticeStatistic(@PathVariable Long id) {
		
		return new ResponseEntity<JusticeStatisticDto>(justiceService.getStatistic(id), HttpStatus.OK);
	}
	
	@ApiOperation(value="판결 등록")
	@RequestMapping(value="/judgements", method=RequestMethod.POST)
	public ResponseEntity<?> addJudgement(@Valid @RequestBody JudgementDto judgementDto) {
		Judgement judgement = modelMapper.map(judgementDto, Judgement.class);
		judgement.setJustice(justiceRepository.findOne(judgementDto.getJusticeId()));
		Judgement savedJudgement = judgementRepository.save(judgement);
		log.debug("### {}", savedJudgement);
		
		return new ResponseEntity<JudgementDto>(judgementDto, HttpStatus.CREATED);
	}
}
