package com.phonedyguard.core.v1.dto.board;

import com.phonedyguard.core.entity.BoardEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardPostDto {
    private String email;
    private String title;
    private String content;
    private String check;

//    public BoardEntity toEntity(){
//        BoardEntity board_Entity = BoardEntity.builder()
//                .email(email)
//                .title(title)
//                .content(content)
//                .build();
//        return board_Entity;
//    }

    @Builder
    public BoardPostDto(String email, String title, String content, String check) {
        this.email = email;
        this.title = title;
        this.content = content;
        this.check = check;
    }
}
