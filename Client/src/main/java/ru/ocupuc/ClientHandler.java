package ru.ocupuc;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.ocupuc.enums.MessageType;


public class ClientHandler extends SimpleChannelInboundHandler<String> {


    private Callback onMessageReceivedCallback;

    public ClientHandler(Callback onMessageReceivedCallback) {
        this.onMessageReceivedCallback = onMessageReceivedCallback;

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {

        if (!s.startsWith("{") || !s.endsWith("}")) {
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Wrapper wrapper = objectMapper.readValue(s, Wrapper.class);
            if (wrapper.getType() == MessageType.MY_PACMAN) {
                GameData.id = wrapper.getPacman().get(0).getId();
            }

            GameData.enemyPacmans = wrapper.getPacman();
        } catch (Exception e) {
            System.out.println("error: " + s);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        throw new RuntimeException(cause);
    }
}
