package com.phonedygaurd.core.v1.dto.board;

import com.phonedygaurd.core.entity.BoardEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardPostDto {
//    private  String id;
    private String title;
    private String content;

    public BoardEntity toEntity(){
        BoardEntity board_Entity = BoardEntity.builder()
//                .id(id)
                .title(title)
                .content(content)
                .build();
        return board_Entity;
    }

    @Builder
    public BoardPostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
