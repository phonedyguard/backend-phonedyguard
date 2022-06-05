package com.phonedyguard.core.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@ToString
public class Tokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
//    @ManyToOne
//    @JoinColumn(name = "email", referencedColumnName = "email")
//    private Users users;
    private String token;
}
