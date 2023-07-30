package ru.ocupuc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.ocupuc.dto.MovementDTO;
import ru.ocupuc.dto.PacmanDTO;

import java.util.Collections;

import static ru.ocupuc.ServerData.channels;
import static ru.ocupuc.ServerData.pacmans;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private final MovementManager movementManager = new MovementManager();
    private static final ObjectMapper mapper = new ObjectMapper();
    //TODO


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.add(incoming);

        String id = incoming.id().asShortText();
        int size = pacmans.size();
        Pacman pacman = new Pacman(id, 10 * size, 0); // или инициализировать случайные координаты
        System.out.println("Pacman: " + pacman);
        pacmans.put(id, pacman);

        PacmanDTO pacmanDTO = new PacmanDTO(pacman.getId(), pacman.getX(), pacman.getY());

        ServerMessage message = new ServerMessage(MessageType.MY_PACMAN, Collections.singletonList(pacmanDTO));
        String messageJson = mapper.writeValueAsString(message);
        incoming.writeAndFlush(messageJson);


    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {

        try {
            MovementDTO movementDTO = new ObjectMapper().readValue(msg, MovementDTO.class);
            movementManager.addMovementDTO(movementDTO);

        } catch (JsonProcessingException e) {
            System.out.println("Ошибка при десериализации сообщения: " + e.getMessage());
        }


    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive " + ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


}



