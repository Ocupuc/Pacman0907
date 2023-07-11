package ru.ocupuc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PacmanPosition {
    private final int x;
    private final int y;

    public PacmanPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Разбирает строку вида "Pacman position: (x;y)"
    public static PacmanPosition parseFromString(String s) {
        String regex = "Pacman position: \\((\\d+);(\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return new PacmanPosition(x, y);
        } else {
            throw new IllegalArgumentException("Invalid position string: " + s);
        }
    }
}

