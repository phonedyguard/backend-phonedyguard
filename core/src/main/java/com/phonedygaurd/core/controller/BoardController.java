package com.phonedygaurd.core.controller;

import com.phonedygaurd.core.v1.dto.request.BoardDto;
import com.phonedygaurd.core.v1.repository.BoardRepository;
import com.phonedygaurd.core.v1.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/board")
    public String getMappingTest(@RequestParam String content, @RequestParam String title) {
        System.out.println(content);
        System.out.println(title);
        BoardDto boardDto = BoardDto.builder()
                .content(content)
                .title(title)
                .build();
        log.info("test: " + boardDto);
        boardService.savePost(boardDto);
        return "X : " + content + ", Y : " + title;
    }
}
