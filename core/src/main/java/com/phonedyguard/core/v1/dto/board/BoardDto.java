package com.phonedyguard.core.v1.dto.board;

import com.phonedyguard.core.entity.BoardEntity;
import lombok.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private String content;
    private String title;
    private long number;

    public BoardEntity toEntity(){
        BoardEntity board_Entity = BoardEntity.builder()
                .content(content)
                .title(title)
                .number(number)
                .build();
        return board_Entity;
    }

    @Builder
    public BoardDto(String content, String title, long number) {
        this.content = content;
        this.title = title;
        this.number = number;
    }
}