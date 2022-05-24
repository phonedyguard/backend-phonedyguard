package com.phonedygaurd.core.v1.service;

import com.phonedygaurd.core.entity.BoardEntity;
import com.phonedygaurd.core.v1.dto.request.BoardDto;
import com.phonedygaurd.core.v1.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public List<BoardDto> getBoardlist() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for ( BoardEntity boardEntity : boardEntities) {
            BoardDto boardDTO = BoardDto.builder()
                    .content(boardEntity.getContent())
                    .title(boardEntity.getTitle())
                    .number(boardEntity.getNumber())
                    .build();

            boardDtoList.add(boardDTO);
        }
        return boardDtoList;
    }

    @Transactional
    public BoardDto getPost(Long number){
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(number);
        BoardEntity boardEntity = boardEntityWrapper.get();

        BoardDto boardDto = BoardDto.builder()
                .content(boardEntity.getContent())
                .title(boardEntity.getTitle())
                .number(boardEntity.getNumber())
                .build();

        return boardDto;
    }

    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getNumber();
    }

}
