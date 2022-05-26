package com.phonedyguard.core.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Data
@NoArgsConstructor
@Table(name = "board")
public class BoardEntity {

//    @Column(length = 100, nullable = false)
//    private String id;

    @Column(length = 300, nullable = false)
    private String content;

    @Column(length = 100, nullable = false)
    private String title;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long number;

    @Builder
    public BoardEntity(String content, String title, long number) {
//        this.id = id;
        this.content = content;
        this.title = title;
        this.number = number;
    }
}
