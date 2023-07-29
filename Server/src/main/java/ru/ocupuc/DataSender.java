package ru.ocupuc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;

import java.util.List;
import java.util.stream.Collectors;

import static ru.ocupuc.ServerData.channels;
import static ru.ocupuc.ServerData.pacmans;

public class DataSender {

    private static final ObjectMapper mapper = new ObjectMapper();
    public void send(){
        System.out.println(ServerData.pacmans.size());

        List<Object> allPacmanDTOs = pacmans.values().stream()
                .map(p -> new PacmanDTO(p.getId(), p.getX(), p.getY()))
                .collect(Collectors.toList());

        ServerMessage allPacmansMessage = new ServerMessage(MessageType.ENEMY_PACMANS, allPacmanDTOs);

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
