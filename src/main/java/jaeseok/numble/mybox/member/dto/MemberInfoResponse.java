package jaeseok.numble.mybox.member.dto;

import jaeseok.numble.mybox.member.domain.Member;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MemberInfoResponse {
    private Long id;
    private String email;
    private UsageResponse usage;

    public MemberInfoResponse(Member member, Long byteUsage) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.usage = new UsageResponse(byteUsage);
    }
 }
