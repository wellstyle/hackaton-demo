package com.example.demo.domain.dto;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PleadingDto {

	@ApiModelProperty(value = "사안")
    private String issue; // 사안
	@ApiModelProperty(value = "검사측 주장")
	private String prosecutorArgument; // 검사측 주장
	@ApiModelProperty(value = "변호인측 주장")
	private String attorneyArgument; // 변호인측 주장
	@ApiModelProperty(value = "판결")
	private String judgment; // 판결
	@ApiModelProperty(value = "관련기사 URL")
	private String articleUrl; // 관련기사 URL
}
