package ru.ocupuc;

import com.badlogic.gdx.math.Vector2;
import ru.ocupuc.enums.CellType;

import java.util.ArrayList;
import java.util.List;

public class MapDtoToGameMapConverter {
    public static GameMap convert(MapDTO mapDTO) {
        List<Vector2> walls = new ArrayList<>();
        List<Vector2> pills = new ArrayList<>();

        short[] data = mapDTO.getData();
        int fieldLength = mapDTO.getLength();
        for (int i = 0; i < data.length; i++) {
            CellType cellType = CellType.getByCode(data[i]);

            int x = i % fieldLength;
            int y =  i / fieldLength;


            Vector2 v = new Vector2(x, y);

            if (cellType == CellType.WALL) {
                walls.add(v);
            }
            if (cellType == CellType.PILL) {
                pills.add(v);
            }
        }

        GameMap gameMap = new GameMap();
        gameMap.updateWalls(walls);
        gameMap.updatePills(pills);

        return gameMap;
    }

}

