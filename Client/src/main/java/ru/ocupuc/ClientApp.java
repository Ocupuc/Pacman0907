package ru.ocupuc;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {


    public static void main(String[] arg) {




        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Hello Client!");
        new Lwjgl3Application(new MyGame(), config);


    }


}
