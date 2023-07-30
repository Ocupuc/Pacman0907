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

//            Vector2 currentPosition = pacman.getVector2Position();
            
            int currentX = pacman.getX();
            int currentY = pacman.getY();
            int newX = pacman.getX();
            int newY = pacman.getY();
//            Vector2 newPosition = new Vector2(newPosition);


            if (dto.isdPressed()) {
                newX += 1;
            }
            if (dto.iswPressed()) {
                newY += 1;
            }
            if (dto.isaPressed()) {
                newX -= 1;
            }
            if (dto.issPressed()) {
                newY -= 1;
            }

            // Check if the new position is a wall
            if (!pacmanField.isWall(newX, newY)) {
                pacman.setX(newX);
                pacman.setY(newY);
            }
        }
    }
}
