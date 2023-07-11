package ru.ocupuc;

import java.awt.Point;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapGame {
    private HashMap<Point, String> map;

    public MapGame() {
        this.map = new HashMap<>();
    }

    // Разбирает строку вида "{(x1,y1)=type1, (x2,y2)=type2, ...}"
    public void parseFromString(String s) {
        String regex = "\\((\\d+),(\\d+)\\)=(\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            String type = matcher.group(3);
            map.put(new Point(x, y), type);
        }
    }

    public HashMap<Point, String> getMap() {
        return map;
    }
}

