package ru.ocupuc;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;




public class ClientHandler extends SimpleChannelInboundHandler<String>{




    private Callback onMessageReceivedCallback;

    public ClientHandler(Callback onMessageReceivedCallback) {
        this.onMessageReceivedCallback = onMessageReceivedCallback;

    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println(s);

        ObjectMapper objectMapper = new ObjectMapper();

        Wrapper wrapper = objectMapper.readValue(s, Wrapper.class);
        GameData.enemyPacmans = wrapper.getPacman();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
