package com.fithub.smart.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fithub.smart.model.dto.Board;
import com.fithub.smart.model.dto.Like;
import com.fithub.smart.model.dto.User;
import com.fithub.smart.model.service.BoardService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api-board")
@ApiOperation(value = "등록된 모든 게시글 정보를 반환한다.", response = Board.class)
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("boards")
	public ResponseEntity<?> boardList() {
		List<Board> boards = boardService.boardList();
		if (boards.size() == 0 || boards == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
	}

	private static String uploadPath = "src/main/resources/static/image/board";
	
	@PostMapping(value = "regist", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> boardRegist(@RequestParam("file") MultipartFile file, @RequestParam("userId") int userId,
            @RequestParam("content") String content, @RequestParam("hashTags") String hashTags) {
		
		DateTimeFormatter simpleDateFormat=DateTimeFormatter.ofPattern("yyMMddhhmmss");
        LocalDateTime now=LocalDateTime.now();
        String currentTime=now.format(simpleDateFormat);
		
        if (file.isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        // 만약에 등록하려고 하는 upload 폴더가 없을 수도 있다.
        File folder = new File(uploadPath);// 폴더
        if (!folder.exists()) // 존재하지 않는다면
            folder.mkdir(); // 만들어

        // 실제 파일이름을 가져와
        String fileName = currentTime+file.getOriginalFilename();
        File target = new File(uploadPath, fileName);
        // 파일을 해당 폴더에 저장을 하자.
        // 저장하는 방법은 크게 2가지
        // 1. FileCopyUtiles
        // 2. File 인스턴스를 직접 활용

        try {
            FileCopyUtils.copy(file.getBytes(), target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /////////////////////////////////////////////////////////////////////////////////////

        // 해시태그
        // Java 언어로 바꾸는 과정
        // JSON으로 바꿔진 것을 JAVA언어로 바뀌기 위해 쓰는 클래스
        // 역직렬화
        // jackson에서 주어지는 인터페이스 클래스
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> hashTagList = new ArrayList<String>();
        try {
            hashTagList = objectMapper.readValue(hashTags, new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //////////////////////////////////////////////////////////////////////////////////////
        Board board = new Board();
        board.setImg(fileName);
        board.setContent(content);
        board.setUserId(userId);

        boolean result = boardService.boardRegist(board, hashTagList);
        if (result) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(result, HttpStatus.NO_CONTENT);

    }

    @GetMapping("detail/{id}")
    public ResponseEntity<?> boardDetail(@PathVariable int id, @RequestParam int userId) {
        Like likeDto = new Like(id, userId);
        Board board = boardService.boardDetail(likeDto);
        if (board == null)
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        System.out.println(board.getIslike());
        return new ResponseEntity<Board>(board, HttpStatus.OK);
    }

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> boardDelete(@PathVariable int id) {
		boolean result = boardService.boardDelete(id);
		if (result)
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		return new ResponseEntity<Boolean>(result, HttpStatus.NOT_ACCEPTABLE);
	}

	@PutMapping("update")
	public ResponseEntity<?> boardUpdate(@RequestBody Board board) {
		
		boolean result = boardService.boardUpdate(board);
		if (result)
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		return new ResponseEntity<Boolean>(result, HttpStatus.NOT_ACCEPTABLE);
	}
	
	// 해시태그로 글조회
    @GetMapping("search")
    public ResponseEntity<?> boardSearch(String tag) {
        List<Board> boards = boardService.boardSearch(tag);
        if (boards.size() == 0 || boards == null)
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
    }
    
    @GetMapping("hashTags/{boardId}")
    public ResponseEntity<?> boardHashTags(@PathVariable int boardId){
        List<String> hashTags=boardService.boardHashTags(boardId);
         if (hashTags.size() == 0 || hashTags == null)
             return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
         return new ResponseEntity<List<String>>(hashTags, HttpStatus.OK);
    }
	
}
