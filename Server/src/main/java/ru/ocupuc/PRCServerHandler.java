package ru.ocupuc;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;




public class PRCServerHandler extends SimpleChannelInboundHandler<String> {
    private Field field;

    public PRCServerHandler() {
        this.field = new Field();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Отправляем размер карты и саму карту клиенту при подключении
        String sizeMessage = String.format("size(%d;%d)\n", field.getSizeX(), field.getSizeY());
        ctx.writeAndFlush(sizeMessage);
        ctx.writeAndFlush(field.getGrid().toString() + "\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String command) throws Exception {
        System.out.println("server channel read");
        System.out.println(ctx.channel().remoteAddress() + " to server: " + command);

        // Обрабатываем команду от клиента и обновляем положение Pacman
        field.processCommand(command);

        // Отправляем текущие координаты Pacman обратно клиенту
        String pacmanPositionMessage = String.format("Pacman position: (%d;%d)\n",
                field.getPacmanPosition().x,
                field.getPacmanPosition().y);
        ctx.writeAndFlush(pacmanPositionMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}