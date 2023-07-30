package ru.ocupuc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import ru.ocupuc.dto.MapDTO;
import ru.ocupuc.dto.PacmanDTO;
import ru.ocupuc.enums.MessageType;

import java.util.List;
import java.util.stream.Collectors;

import static ru.ocupuc.ServerData.*;

public class DataSender {

    private static final ObjectMapper mapper = new ObjectMapper();
    public void send(){
     //   System.out.println(ServerData.pacmans.size());

        MovementManager movementManager = new MovementManager();
        movementManager.move();

        List<Object> allPacmanDTOs = pacmans.values().stream()
                .map(p -> new PacmanDTO(p.getId(), p.getX(), p.getY()))
                .collect(Collectors.toList());
        MapDTO convert = MapToDTOConverter.convert(pacmanField);
        ServerMessage allPacmansMessage = new ServerMessage(MessageType.ENEMY_PACMANS,
                allPacmanDTOs, convert.getLength(), convert.getData()
                );

        String allPacmansMessageJson = null;
        try {
            allPacmansMessageJson = mapper.writeValueAsString(allPacmansMessage);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        for (Channel channel : channels) {
            channel.writeAndFlush(allPacmansMessageJson);

        }
    }
}
