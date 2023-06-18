package jaeseok.numble.mybox.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ByteConvertorTest")
class ByteConvertorTest {

    // 소숫점아래 6자리까지 동일하면 성공으로 본다
    private final static double VALID_DIGITS = 100000;

    @Test
    @DisplayName("bytes -> KB 성공")
    void bytesToKiloBytesSuccess() {
        // given
        Long bytes = 14873015L;

        // when
        double kilobytes = ByteConvertor.toKiloBytes(bytes);
        kilobytes = Math.round(kilobytes * VALID_DIGITS) / VALID_DIGITS;

        // then
        double expected = Math.round(14524.4287109375 * VALID_DIGITS) / VALID_DIGITS;
        Assertions.assertEquals(expected, kilobytes);
    }

    @Test
    @DisplayName("bytes -> MB 성공")
    void bytesToMegaBytesSuccess() {
        // given
        Long bytes = 14873015L;

        // when
        double megabytes = ByteConvertor.toMegaBytes(bytes);
        megabytes = Math.round(megabytes * VALID_DIGITS) / VALID_DIGITS;

        // then
        double expected = Math.round(14.184012413024902 * VALID_DIGITS) / VALID_DIGITS;
        Assertions.assertEquals(expected, megabytes);
    }

    @Test
    @DisplayName("bytes -> GB 성공")
    void bytesToGigaBytesSuccess() {
        // given
        Long bytes = 14873015L;

        // when
        double gigabytes = ByteConvertor.toGigaBytes(bytes);
        gigabytes = Math.round(gigabytes * VALID_DIGITS) / VALID_DIGITS;

        // then
        double expected = Math.round(0.013851574622095 * VALID_DIGITS) / VALID_DIGITS;
        Assertions.assertEquals(expected, gigabytes);
    }
}