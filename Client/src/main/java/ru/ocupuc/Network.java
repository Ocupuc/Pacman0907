package ru.ocupuc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class Network {
    private SocketChannel channel;

    private static final String HOST = "localhost";
    private static final int PORT = 8090;


    public Network(Callback onMessageReceivedCallback) {

        Thread t =  new Thread(() -> {
            System.out.println("new Network");
            EventLoopGroup workerGroup = new NioEventLoopGroup();  //в данном случае предролагается что к клиенту ни кто не подключается, по этому пул потоков только один
            try {
                Bootstrap b = new Bootstrap();
                b.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(),            // переводит байкод в строки
                                        new ClientHandler(onMessageReceivedCallback));
                            }
                        });
                ChannelFuture future = b.connect(HOST, PORT).sync();
                future.channel().closeFuture().sync(); // это что бы канал сразу не закрылся
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }


        });
        t.setDaemon(true);
        t.start();
    }

    public void sendMessage(String str) {
        channel.writeAndFlush(str);
    }
}