package jaeseok.numble.mybox.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberSignUpDto {
    private String id;
    private String password;
    private String nickname;
}
