package com.phonedyguard.core.v1.dto.board;

import com.phonedyguard.core.entity.BoardEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardListDto {
//    private  String id;
    private String title;
    private long number;

    public BoardEntity toEntity(){
        BoardEntity board_Entity = BoardEntity.builder()
                .title(title)
                .number(number)
                .build();
        return board_Entity;
    }

    @Builder
    public BoardListDto(String title, long number) {
        this.title = title;
        this.number = number;
    }
}
