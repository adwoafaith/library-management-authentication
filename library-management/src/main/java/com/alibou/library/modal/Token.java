package com.alibou.library.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity


public class Token {

    @Id
    @GeneratedValue
    private Integer id;

    private String token;
    private LocalDateTime expires;
    private LocalDateTime createdAt;
    private LocalDateTime validatedAt;

    @ManyToOne //many tokens to one user.
    @JoinColumn(name = "userId", nullable = false)  //@Join is used to indicate a foreign key colum so over here the user is a foreing key
    private User user;
}
