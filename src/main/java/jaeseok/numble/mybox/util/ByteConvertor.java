package jaeseok.numble.mybox.util;

public class ByteConvertor {
    private static final double UNIT_SIZE = 1024.0;

    public static double toKiloBytes(Long bytes) {
        return (double) bytes / UNIT_SIZE;
    }

    public static double toMegaBytes(Long bytes) {
        return toKiloBytes(bytes) / UNIT_SIZE;
    }

    public static double toGigaBytes(Long bytes) {
        return toMegaBytes(bytes) / UNIT_SIZE;
    }
}
