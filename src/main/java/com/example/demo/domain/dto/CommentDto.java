package com.example.demo.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentDto {
	
	@ApiModelProperty(value = "사용자 이름")
	private String name; // 사용자 이름
	@ApiModelProperty(value = "내용")
	private String content; // 내용
	@ApiModelProperty(value = "형량")
	private int penalty; // 형량
	@ApiModelProperty(value = "재판 아이디")
    private Long justiceId; // 재판 아이디
}
