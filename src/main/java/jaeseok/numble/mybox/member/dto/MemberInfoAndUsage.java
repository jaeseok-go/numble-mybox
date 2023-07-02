package jaeseok.numble.mybox.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoAndUsage {
    private Long id;
    private String email;
    private Long byteUsage;
}
