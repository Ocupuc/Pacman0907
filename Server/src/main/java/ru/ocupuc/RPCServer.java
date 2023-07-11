package ru.ocupuc;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;


public class RPCServer {

    private final String ip;
    private final int port;
    private final int ioThreads; //  Поток чтения и записи, используемый для обработки сетевого потока
    private final int workerThreads; //  Поток вычислений для бизнес-обработки

    public RPCServer(String ip, int port, int ioThreads, int workerThreads) {
        this.ip = ip;
        this.port = port;
        this.ioThreads = ioThreads;
        this.workerThreads = workerThreads;
    }

    public void start() {
        //Основная группа, используемая для обработки запроса клиента на подключение
        EventLoopGroup bossGroup = new NioEventLoopGroup(ioThreads);
        //  Рабочая группа, используемая для обработки операций ввода-вывода, подключенных к каждому клиенту
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreads);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new PRCServerHandler());
                        }
                    });
            bootstrap.option(ChannelOption.SO_BACKLOG, 100)  //  Клиентский набор принимает размер очереди
                   .option(ChannelOption.SO_REUSEADDR, true) //  Повторно используйте Addr, чтобы избежать конфликтов портов
 //                   .option(ChannelOption.TCP_NODELAY, true) //  Отключите слияние малого потока, обеспечьте своевременную информацию
                    .childOption(ChannelOption.SO_KEEPALIVE, true); //  Отсутствие движения в течение длительного времени автоматически отключает
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("Server start listen at " + port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }

    }

}