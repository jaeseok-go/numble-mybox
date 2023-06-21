package jaeseok.numble.mybox.member.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String jwt;
    private Long rootFolderId;
}
