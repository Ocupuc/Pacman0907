package ru.ocupuc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameData {

    public static String id;
    public static Pacman myPacman; // Для хранения своего пакмана
    public static List<Pacman> enemyPacmans = new ArrayList<>(); // Для хранения вражеских пакманов
    public static ConcurrentLinkedQueue<Pacman> updatesQueue = new ConcurrentLinkedQueue<>(); // Для хранения обновлений
}
