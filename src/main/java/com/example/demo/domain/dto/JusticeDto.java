package com.example.demo.domain.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JusticeDto {
	
	@ApiModelProperty(value = "사건번호")
	private String no; // 사건 번호 
	@ApiModelProperty(value = "사건명")
	private String title; // 사건명
	@ApiModelProperty(value = "혐의")
	private String allegation; // 혐의
	@ApiModelProperty(value = "피고인")
	private String undertrial; // 피고인
	@ApiModelProperty(value = "원고")
	private String plaintiff; // 원고
	@ApiModelProperty(value = "기소")
	private String prosecution; // 기소
	@ApiModelProperty(value = "검사")
	private String prosecutor; // 검사
	@ApiModelProperty(value = "변호사")
	private String attorney; // 변호사
	@ApiModelProperty(value = "판사")
	private String judge; // 판사
	@ApiModelProperty(value = "판결")
	private String judgment; // 판결
	@ApiModelProperty(value = "법정")
	private String venue; // 법정
	@ApiModelProperty(value = "피고인 사진")
    @JsonIgnore
    private MultipartFile undertrialPhoto; // 피고인 사진
	@ApiModelProperty(value = "피고인 사진 URL")
	private String undertrialPhotoUrl; // 피고인 사진 URL
	@ApiModelProperty(value = "사안별 변론내용")
	private List<PleadingDto> pleadings; // 사안별 변론내용
}