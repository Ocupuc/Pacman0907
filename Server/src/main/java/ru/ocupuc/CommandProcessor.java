package ru.ocupuc;

import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {

    private Field field;

    public CommandProcessor(Field field) {
        this.field = field;
    }

    public void processCommand(String command) {
        Point currentPosition = field.getPacmanPosition();
        Point newPosition = null;

        String[] commands = command.split(", ");
        Map<String, Boolean> commandMap = new HashMap<>();
        for (String singleCommand : commands) {
            String[] parts = singleCommand.split(":");
            commandMap.put(parts[0], Boolean.parseBoolean(parts[1]));
        }

        if (commandMap.get("W")) {
            newPosition = new Point(currentPosition.x, currentPosition.y + 1);
        } else if (commandMap.get("S")) {
            newPosition = new Point(currentPosition.x, currentPosition.y - 1);
        } else if (commandMap.get("A")) {
            newPosition = new Point(currentPosition.x - 1, currentPosition.y);
        } else if (commandMap.get("D")) {
            newPosition = new Point(currentPosition.x + 1, currentPosition.y);
        }

        if (newPosition != null) {
            field.movePacman(newPosition);
        } else {
            System.out.println("Invalid command: " + command);
        }
    }
}
