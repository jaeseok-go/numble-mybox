package jaeseok.numble.mybox.member.domain;

import jaeseok.numble.mybox.common.entity.BaseEntity;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Builder
@Table(name="member")
public class Member extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "nickname", nullable = false, length = 30)
    private String nickname;

    @Override
    public boolean equals(Object object) {
        return object instanceof Member && this.id.equals(((Member) object).id);
    }

    public void validatePassword(String password) {
        if (!password.equals(this.password)) {
            throw new MyBoxException(ResponseCode.INVALID_PASSWORD);
        }
    }
}