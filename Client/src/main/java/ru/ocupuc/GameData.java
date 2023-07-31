package ru.ocupuc;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameData {

    public static String id ;
    public static Pacman myPacman; // Для хранения своего пакмана
    public static List<Pacman> enemyPacmans = new ArrayList<>(); // Для хранения вражеских пакманов
    public static ConcurrentLinkedQueue<Pacman> updatesQueue = new ConcurrentLinkedQueue<>(); // Для хранения обновлений
    public static GameMap gameMap = new GameMap();

}
