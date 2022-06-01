package com.phonedyguard.core.controller;

import com.phonedyguard.core.entity.Users;
import com.phonedyguard.core.jwt.JwtAuthenticationFilter;
import com.phonedyguard.core.jwt.JwtTokenProvider;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.board.BoardDto;
import com.phonedyguard.core.v1.dto.board.BoardListDto;
import com.phonedyguard.core.v1.dto.board.BoardPostDto;
import com.phonedyguard.core.v1.dto.board.BoardUpdateDto;
import com.phonedyguard.core.v1.dto.request.UserRequestDto;
import com.phonedyguard.core.v1.repository.BoardRepository;
import com.phonedyguard.core.v1.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class BoardController {
    @Autowired
    private final BoardService boardService;

    public BoardController(BoardService boardService, BoardRepository boardRepository, Response response, JwtTokenProvider jwtTokenProvider) {
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }

    @Autowired
    private final BoardRepository boardRepository;


//    @PostMapping(value = "/board")
//    public ResponseEntity<?> getMappingTest(@RequestBody BoardDto boardDto, HttpServletRequest request) {
//
//        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
//        if (!jwtTokenProvider.validateToken(token))
//        {
//            return response.fail("accessToken 검증 실패", HttpStatus.BAD_REQUEST);
//        }
//        // token 값으로 정보 추출
//        Authentication authentication = jwtTokenProvider.getAuthentication(token);
//
//
//        boardDto = BoardDto.builder()
//                .content(boardDto.getContent())
//                .title(boardDto.getTitle())
//                .build();
//        log.info("boardDto: " + boardDto);
//        boardService.savePost(boardDto, request);
//        return ResponseEntity.ok(boardDto);
//    }

    @PostMapping(value = "/board")
    public ResponseEntity<?> getMapping(HttpServletRequest request, @RequestBody BoardDto boardDto){
        return boardService.savePost(request, boardDto);
    }


    @GetMapping("/board")
    public ResponseEntity<List> boardinfo(){
        List<BoardListDto> boardList = boardService.getBoardlist();
        return new ResponseEntity<List>(boardList, HttpStatus.OK);
    }

    @GetMapping("/board/{number}")
    public BoardPostDto findById(@PathVariable Long number){
        return boardService.getPost(number);
    }

    @DeleteMapping("/board/{number}")
    public String delete(@PathVariable Long number){
        boardService.deletePost(number);
        return "delete";
    }

    @PutMapping("/board/{number}")
    public ResponseEntity<?> updateBoard(HttpServletRequest request, @RequestBody BoardUpdateDto boardUpdateDto, @PathVariable("number") Long number){
        return boardService.updateBoard(request, boardUpdateDto, number);
    }
}
