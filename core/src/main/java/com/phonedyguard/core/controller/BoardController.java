package com.phonedyguard.core.controller;

import com.phonedyguard.core.jwt.JwtTokenProvider;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.board.BoardDto;
import com.phonedyguard.core.v1.dto.board.BoardListDto;
import com.phonedyguard.core.v1.dto.board.BoardPostDto;
import com.phonedyguard.core.v1.dto.board.BoardUpdateDto;
import com.phonedyguard.core.v1.repository.BoardRepository;
import com.phonedyguard.core.v1.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResponseEntity<?> deleteBoard(HttpServletRequest request, @RequestBody BoardUpdateDto boardUpdateDto, @PathVariable("number") Long number){
        return boardService.deleteBoard(request, boardUpdateDto, number);
    }

    @PutMapping("/board/{number}")
    public ResponseEntity<?> updateBoard(HttpServletRequest request, @RequestBody BoardUpdateDto boardUpdateDto, @PathVariable("number") Long number){
        return boardService.updateBoard(request, boardUpdateDto, number);
    }
}
