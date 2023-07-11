package ru.ocupuc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record MapSize(int mapSizeX, int mapSizeY) {

    // Разбирает строку вида "size(x;y)"
    public static MapSize parseFromString(String s) {
        String regex = "size\\((\\d+);(\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return new MapSize(x, y);
        } else {
            throw new IllegalArgumentException("Invalid size string: " + s);
        }
    }
}
