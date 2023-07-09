package ru.ocupuc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;




public class PRCServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println("server channel read");
        System.out.println(ctx.channel().remoteAddress() + "to server: " + s);
        ctx.write("server write " + s);
        ctx.flush();
    }
}
