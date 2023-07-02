package jaeseok.numble.mybox.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResponse {
    private Long id;
    private String email;
}
