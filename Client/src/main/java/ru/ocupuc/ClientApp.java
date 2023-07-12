package ru.ocupuc;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    private static DemoClient demoClient;

    public static void main(String[] arg) {

        ExecutorService executor = Executors.newFixedThreadPool(2); // создаём пул из двух потоков

        executor.submit(() -> {
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle("Hello Client!");
            new Lwjgl3Application(new MyGame(), config);
        });

        executor.submit(() -> {
            demoClient = new DemoClient("127.0.0.1", 8090, 4);
            demoClient.start();
        });

        executor.shutdown();
    }
}