package jaeseok.numble.mybox.member.dto;

import jaeseok.numble.mybox.member.dto.UsageDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MemberInfoDto {
    private String id;
    private String nickname;
    private UsageDto usage;
 }
