package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Comment;
import com.example.demo.domain.CommentRepository;
import com.example.demo.domain.Judgement;
import com.example.demo.domain.JudgementRepository;
import com.example.demo.domain.JusticeRepository;
import com.example.demo.domain.dto.CommentDto;
import com.example.demo.domain.dto.JudgementDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CommentService {
	@Autowired
	JusticeRepository justiceRepository;
	@Autowired
	CommentRepository commentRepository;

	public Comment save(CommentDto commentDto) {
		Comment comment = new Comment();
		comment.setName(commentDto.getName());
		comment.setPenalty(commentDto.getPenalty());
		comment.setContent(commentDto.getContent());
		comment.setJustice(justiceRepository.findOne(commentDto.getJusticeId()));
		return commentRepository.save(comment);
	}
}
