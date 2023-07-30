package ru.ocupuc;

import com.badlogic.gdx.math.Vector2;
import ru.ocupuc.dto.MovementDTO;

import static ru.ocupuc.ServerData.*;

public class MovementManager {


    public void addMovementDTO(MovementDTO dto) {
    //    System.out.println(dto.toString());
        movementDTOs.remove(dto);
        movementDTOs.add(dto);
    //    movementDTOs.forEach(System.out::println);
    //    System.out.println("--------------------------------");
    }



    public void move() {
        for (MovementDTO dto : movementDTOs) {
            String id = dto.getId();
            Pacman pacman = pacmans.get(id);
            if (pacman == null) {
                continue;
            }

            Vector2 currentPosition = pacman.getVector2Position();
            Vector2 newPosition = new Vector2(currentPosition);

            if (dto.isdPressed()) {
                newPosition.x += 1;
            }
            if (dto.iswPressed()) {
                newPosition.y += 1;
            }
            if (dto.isaPressed()) {
                newPosition.x -= 1;
            }
            if (dto.issPressed()) {
                newPosition.y -= 1;
            }

            // Check if the new position is a wall
            if (!pacmanField.isWall(newPosition)) {
                pacman.setPositionFromVector2(newPosition);
            }
        }
    }
}
