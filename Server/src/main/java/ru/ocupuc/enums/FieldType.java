package ru.ocupuc.enums;


import java.util.Arrays;

public enum FieldType {
    EMPTY ((short)0),
    WALL((short)1),
    PILL((short) 2);

    private final short code;

    FieldType(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    public static FieldType getByCode(short code) {
        return Arrays.stream(FieldType.values())
                .filter(ft -> ft.code == code)
                .findFirst()
                .orElse(null);
    }
}
