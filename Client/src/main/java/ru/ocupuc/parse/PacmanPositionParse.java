package ru.ocupuc.parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record PacmanPositionParse(int x, int y) {
    // Разбирает строку вида "Pacman position: (x;y)"
    public static PacmanPositionParse parseFromString(String s) {
        String regex = "Pacman position: \\((\\d+);(\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            try {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                return new PacmanPositionParse(x, y);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректный формат числа в строке: " + s);
            }
        } else {
            throw new IllegalArgumentException("Неверная строка позиции: " + s);
        }
    }
}

