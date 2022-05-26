package com.phonedygaurd.core.v1.dto.board;

import lombok.*;
import phonedyguard.core.entity.BoardEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardUpdateDto {
    //    private String id;
    private String content;
    private String title;
//    private long number;

    public BoardEntity toEntity(){
        BoardEntity board_Entity = BoardEntity.builder()
//                .id(id)
                .content(content)
                .title(title)
//                .number(number)
                .build();
        return board_Entity;
    }

    @Builder
    public BoardUpdateDto(String content, String title) {
//        this.id = id;
        this.content = content;
        this.title = title;
//        this.number = number;
    }
}
