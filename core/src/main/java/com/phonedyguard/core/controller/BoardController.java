package com.phonedyguard.core.controller;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class BoardController {
    @Autowired
    private final BoardService boardService;

    public BoardController(BoardService boardService, BoardRepository boardRepository) {
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }

    @Autowired
    private final BoardRepository boardRepository;

    @PostMapping(value = "/board")
    public ResponseEntity<?> getMappingTest(@RequestBody BoardDto boardDto) {
        boardDto = BoardDto.builder()
                .content(boardDto.getContent())
                .title(boardDto.getTitle())
                .build();
        log.info("boardDto: " + boardDto);
        boardService.savePost(boardDto);
        return ResponseEntity.ok(boardDto);
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
    public Map<String, Object> update(@RequestBody BoardUpdateDto boardUpdateDto, @PathVariable("number") Long number) {
        Map<String, Object> response = new HashMap<>();
        int res = boardService.update(number,boardUpdateDto);
        if(res>0){
            response.put("result", "Success");
        }
        else{
            response.put("result", "일치정보 x");
        }
        return response;
    }
}
