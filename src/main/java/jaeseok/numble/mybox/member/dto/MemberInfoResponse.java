package jaeseok.numble.mybox.member.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MemberInfoResponse implements Serializable {
    private Long id;
    private String email;
    private UsageResponse usage;

    public MemberInfoResponse(MemberInfoAndUsage memberInfoAndUsage) {
        this.id = memberInfoAndUsage.getId();
        this.email = memberInfoAndUsage.getEmail();
        this.usage = new UsageResponse(memberInfoAndUsage.getByteUsage());
    }
 }
