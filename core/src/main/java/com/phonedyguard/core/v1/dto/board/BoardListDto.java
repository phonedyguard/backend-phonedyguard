package com.phonedyguard.core.v1.dto.board;

import com.phonedyguard.core.entity.BoardEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardListDto {
    private String email;
    private String title;
    private long number;

    public BoardEntity toEntity(){
        BoardEntity board_Entity = BoardEntity.builder()
                .email(email)
                .title(title)
                .number(number)
                .build();
        return board_Entity;
    }

    @Builder
    public BoardListDto(String email, String title, long number) {
        this.email = email;
        this.title = title;
        this.number = number;
    }
}
