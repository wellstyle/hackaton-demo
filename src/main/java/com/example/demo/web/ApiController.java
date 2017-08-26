package com.example.demo.web;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Comment;
import com.example.demo.domain.CommentRepository;
import com.example.demo.domain.Judgement;
import com.example.demo.domain.JudgementRepository;
import com.example.demo.domain.Justice;
import com.example.demo.domain.JusticeRepository;
import com.example.demo.domain.dto.CommentDto;
import com.example.demo.domain.dto.JudgementDto;
import com.example.demo.domain.dto.JusticeDto;
import com.example.demo.domain.dto.JusticeStatisticDto;
import com.example.demo.service.CommentService;
import com.example.demo.service.JudgementService;
import com.example.demo.service.JusticeService;
import com.example.demo.storage.StorageService;

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
	CommentRepository commentRepository;
	@Autowired
	JusticeService justiceService;
	@Autowired
	JudgementService judgementService;
	@Autowired
	CommentService commentService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
    private StorageService storageService;
	
    @Value("${demo.file.rootLocation}")
    private String location;
	
	@ApiOperation(value="사건 등록")
	@RequestMapping(value="/justices", method=RequestMethod.POST)
	public ResponseEntity<?> addJustice(@Valid @RequestBody JusticeDto justiceDto) {
		Justice justice = modelMapper.map(justiceDto, Justice.class);
		Justice savedJustice = justiceRepository.save(justice);
		
		return new ResponseEntity<Justice>(savedJustice, HttpStatus.CREATED);
	}
	
	@ApiOperation(value="사건 리스트")
	@RequestMapping(value="/justices", method=RequestMethod.GET)
	public ResponseEntity<?> getJustices() {
		
		List<Justice> justices = justiceRepository.findAll();
		log.debug("### {}", justices);
		
		return new ResponseEntity<List<Justice>>(justices, HttpStatus.OK);
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
	@RequestMapping(value="/justices/judgements", method=RequestMethod.POST)
	public ResponseEntity<?> addJudgement(@Valid @RequestBody JudgementDto judgementDto) {
		log.debug("### {}", judgementDto);

		return new ResponseEntity<Judgement>(judgementService.save(judgementDto), HttpStatus.CREATED);
	}
	
	@ApiOperation(value="최근 판결 리스트")
	@RequestMapping(value="/justices/judgements", method=RequestMethod.GET)
	public ResponseEntity<?> getJudgements(@RequestParam Long justiceId
			, @PageableDefault(sort = { "createdAt" }, direction = Direction.DESC, size = 5) Pageable pageable) {
//		Page<Judgement> judgements = judgementRepository.findAll(pageable);
		Page<Judgement> judgements = judgementRepository.findByJustice(justiceRepository.findOne(justiceId), pageable);
		log.debug("### {}", judgements);
		
		return new ResponseEntity<Page<Judgement>>(judgements, HttpStatus.OK);
	}
	
	@ApiOperation(value="댓글 등록")
	@RequestMapping(value="/justices/comments", method=RequestMethod.POST)
	public ResponseEntity<?> addComment(@Valid @RequestBody CommentDto commentDto) {
		
		return new ResponseEntity<Comment>(commentService.save(commentDto), HttpStatus.CREATED);
	}
	
	@ApiOperation(value="최근 댓글 리스트")
	@RequestMapping(value="/justices/comments", method=RequestMethod.GET)
	public ResponseEntity<?> getComments(@RequestParam Long justiceId
			, @PageableDefault(sort = { "createdAt" }, direction = Direction.DESC, size = 5) Pageable pageable) {
//		Page<Comment> comments = commentRepository.findAll(pageable);
		Page<Comment> comments = commentRepository.findByJustice(justiceRepository.findOne(justiceId), pageable);
		log.debug("### {}", comments);
		
		return new ResponseEntity<Page<Comment>>(comments, HttpStatus.OK);
	}
	
    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(MultipartFile.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                log.debug("initBinder MultipartFile.class: {}; set null;", text);
                setValue(null);
            }

        });
    }
    
    @RequestMapping(value = "/justices/undertrial/photo/{id}", method = RequestMethod.GET)
    public void getReviewPhoto(@PathVariable Long id, HttpServletResponse response) throws IOException {
    	String path = location + "/" + id + ".jpg";
    	InputStream in = storageService.loadAsResource(path).getInputStream();
    	response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    	IOUtils.copy(in, response.getOutputStream());
    }
}
