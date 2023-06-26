package jaeseok.numble.mybox.member.dto;

import jaeseok.numble.mybox.common.util.ByteConvertor;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsageResponse {
    private Double GB;
    private Double MB;
    private Double KB;
    private Long B;

    public UsageResponse(Long bytes) {
        this.B = bytes;
        this.KB = ByteConvertor.toKiloBytes(bytes);
        this.MB = ByteConvertor.toMegaBytes(bytes);
        this.GB = ByteConvertor.toGigaBytes(bytes);
    }
}
