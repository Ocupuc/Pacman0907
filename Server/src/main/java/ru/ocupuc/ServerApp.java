package ru.ocupuc;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerApp {

    private static final String HOST = "localhost";
    private static final int PORT = 8090;

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(4); //пулл потоков для подключающихся клиентов
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // пул потоков для обработки

        try {
            ServerBootstrap b = new ServerBootstrap(); //преднастройка нашего сервака
            b.group(bossGroup, workerGroup)          //тут мы говорим-Сервак, используй два пула потоков
                    .channel(NioServerSocketChannel.class) //создаем стандартный Socket
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new StringDecoder(),  //перевод байкода в строку
                                    new StringEncoder(), new MainHandler());          //добавлям обработчик MainHandler
                        }
                    });
            ChannelFuture future = b.bind(PORT).sync(); // запускаем наш сервер и слушаем порт
            future.channel().closeFuture().sync(); //тут мы ждем пока кто-то остановит сервер

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully(); //как только сервак остановили нам надо закрыть пулы потоков
            workerGroup.shutdownGracefully();
        }
    }
}

