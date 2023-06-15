package jaeseok.numble.mybox.member.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MemberInfoResponse {
    private Long id;
    private String email;
    private UsageDto usage;
 }
