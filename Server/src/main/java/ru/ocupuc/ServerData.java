package ru.ocupuc;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import ru.ocupuc.dto.MovementDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ServerData {
    public static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static final ConcurrentMap<String, Pacman> pacmans = new ConcurrentHashMap<>();
    public static final List<MovementDTO> movementDTOs = new ArrayList<>();

    // Путь к файлу с описанием поля игры.
    private static final String PACMAN_FIELD_FILE = "D:\\libGDX\\Pacman0907\\Server\\src\\main\\resources\\pacman_field.txt";
    public static final PacmanField pacmanField;

    static {
        try {
            pacmanField = new PacmanField(PACMAN_FIELD_FILE);
        } catch (Exception e) {
            System.err.println("Ошибка при инициализации поля Pacman: " + e.getMessage());
            throw new RuntimeException(e);  // Чтобы остановить работу сервера в случае ошибки
        }
    }
}