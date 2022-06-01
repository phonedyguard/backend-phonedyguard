package com.phonedyguard.core.v1.service;

import com.phonedyguard.core.entity.Users;
import com.phonedyguard.core.jwt.JwtAuthenticationFilter;
import com.phonedyguard.core.jwt.JwtTokenProvider;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.board.BoardDto;
import com.phonedyguard.core.v1.dto.board.BoardListDto;
import com.phonedyguard.core.v1.dto.board.BoardPostDto;
import com.phonedyguard.core.v1.dto.board.BoardUpdateDto;
import com.phonedyguard.core.entity.BoardEntity;
import com.phonedyguard.core.v1.dto.request.UserRequestDto;
import com.phonedyguard.core.v1.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final Response response;
    private final JwtTokenProvider jwtTokenProvider;


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
    public ResponseEntity<?> savePost(HttpServletRequest request, BoardDto boardDto) {

        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
        if (!jwtTokenProvider.validateToken(token))
        {
            return response.fail("accessToken 검증 실패", HttpStatus.BAD_REQUEST);
        }
        // token 값으로 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        String email = authentication.getName();

        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setEmail(email);
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setTitle(boardDto.getTitle());
        boardRepository.save(boardEntity);
        return response.success("게시글 작성 성공");
    }

    @Transactional
    public void deletePost(Long number){
        boardRepository.deleteById(number);
    }


    public ResponseEntity<?> updateBoard(HttpServletRequest request, BoardUpdateDto boardUpdateDto, long number){
        // Header에서 token 값 추출
        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
        if (!jwtTokenProvider.validateToken(token))
        {
            return response.fail("accessToken 검증 실패", HttpStatus.BAD_REQUEST);
        }

        // token 값으로 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        String email = authentication.getName();

        Optional<BoardEntity> boardEntity = boardRepository.findById(number);
        BoardEntity boardUpdate = boardEntity.get();
        String my_email = boardUpdate.getEmail();
        if (email == my_email){
            boardUpdate.setTitle(boardUpdateDto.getTitle());
            boardUpdate.setContent(boardUpdateDto.getContent());
            return response.success("게시판 수정");
        }
        else{
            return response.fail("게시판 실패",HttpStatus.BAD_REQUEST);
        }
    }

//    @Transactional
//    public int update(long number, final BoardUpdateDto dto){
//        Optional<BoardEntity> boardEntity = boardRepository.findById(number);
//        BoardEntity boardUpdate = boardEntity.get();
//        boardUpdate.setContent(dto.getContent());
//        boardUpdate.setTitle(dto.getTitle());
//        boardRepository.save(boardUpdate);
//        return 1;
//    }

}