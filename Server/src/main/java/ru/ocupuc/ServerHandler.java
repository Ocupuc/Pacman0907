package ru.ocupuc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ocupuc.dto.MovementDTO;
import ru.ocupuc.dto.PacmanDTO;

import java.util.Collections;


import static ru.ocupuc.ServerData.channels;
import static ru.ocupuc.ServerData.pacmans;

public class ServerHandler extends SimpleChannelInboundHandler<String> {
    private final MovementManager movementManager = new MovementManager();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        try {
            Channel incoming = ctx.channel();
            channels.add(incoming);
            String id = incoming.id().asShortText();
            int size = pacmans.size();
            Pacman pacman = new Pacman(id, 3 * size, 0); // или инициализировать случайные координаты
            logger.info("Pacman: {}", pacman);
            pacmans.put(id, pacman);
            PacmanDTO pacmanDTO = new PacmanDTO(pacman.getId(), pacman.getX(), pacman.getY());
            ServerMessage message = new ServerMessage(MessageType.MY_PACMAN, Collections.singletonList(pacmanDTO));
            String messageJson = mapper.writeValueAsString(message);
            incoming.writeAndFlush(messageJson);
        } catch (Exception e) {
            logger.error("Ошибка при обработке события channelActive", e);
        }
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        try {
            MovementDTO movementDTO = mapper.readValue(msg, MovementDTO.class);
            movementManager.addMovementDTO(movementDTO);
        } catch (JsonProcessingException e) {
            logger.error("Ошибка при десериализации сообщения: {}", e.getMessage());
        }
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        try {
            Channel incoming = ctx.channel();
            String id = incoming.id().asShortText();
            pacmans.remove(id);
            super.channelInactive(ctx);
        } catch (Exception e) {
            logger.error("Ошибка при обработке события channelInactive", e);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        try {
            Channel incoming = ctx.channel();
            String id = incoming.id().asShortText();
            pacmans.remove(id);
            super.exceptionCaught(ctx, cause);
        } catch (Exception e) {
            logger.error("Ошибка при обработке исключения", e);
        }
    }
}



