package com.phonedyguard.core.v1.service;

import com.phonedyguard.core.v1.dto.board.BoardDto;
import com.phonedyguard.core.v1.dto.board.BoardListDto;
import com.phonedyguard.core.v1.dto.board.BoardPostDto;
import com.phonedyguard.core.v1.dto.board.BoardUpdateDto;
import com.phonedyguard.core.entity.BoardEntity;
import com.phonedyguard.core.v1.dto.board.BoardDto;
import com.phonedyguard.core.v1.repository.BoardRepository;
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
    public List<BoardListDto> getBoardlist() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardListDto> boardDtoList = new ArrayList<>();

        for ( BoardEntity boardEntity : boardEntities) {
            BoardListDto boardListDto = BoardListDto.builder()
                            .title(boardEntity.getTitle())
                            .number(boardEntity.getNumber())
                            .build();
            boardDtoList.add(boardListDto);
        }
        return boardDtoList;
    }

    @Transactional
    public BoardPostDto getPost(Long number){
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(number);
        BoardEntity boardEntity = boardEntityWrapper.get();

        BoardPostDto boardPostDto = BoardPostDto.builder()
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .build();

        return boardPostDto;
    }


    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getNumber();
    }

    @Transactional
    public void deletePost(Long number){
        boardRepository.deleteById(number);
    }

    @Transactional
    public int update(long number, final BoardUpdateDto dto){
        Optional<BoardEntity> boardEntity = boardRepository.findById(number);
        BoardEntity boardUpdate = boardEntity.get();
        boardUpdate.setContent(dto.getContent());
        boardUpdate.setTitle(dto.getTitle());
        boardRepository.save(boardUpdate);
        return 1;
    }

}
