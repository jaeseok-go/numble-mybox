package jaeseok.numble.mybox.member.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsageDto {
    private Double GB;
    private Double MB;
    private Double KB;
    private Long B;
}
