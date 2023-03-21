package jaeseok.numble.mybox.member.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name="member")
public class Member {
    @Id
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "nickname", nullable = false, length = 30)
    private String nickname;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}