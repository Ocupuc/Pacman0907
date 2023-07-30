package ru.ocupuc;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final List<Vector2> walls = new ArrayList<>();
    private final List<Vector2> pills = new ArrayList<>();

  public void updateWalls(List<Vector2> walls){
      this.walls.clear();
      this.walls.addAll(walls);
  }
    public void updatePills(List<Vector2> pills){
        this.pills.clear();
        this.pills.addAll(pills);
    }
  
  
    public List<Vector2> getWalls() {
        return walls;
    }

    public List<Vector2> getPills() {
        return pills;
    }
    
    
}
