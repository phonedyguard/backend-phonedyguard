package com.phonedyguard.core.controller;

import com.phonedyguard.core.v1.dto.request.BoardDto;
import com.phonedyguard.core.v1.repository.BoardRepository;
import com.phonedyguard.core.v1.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class BoardController {
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
}
