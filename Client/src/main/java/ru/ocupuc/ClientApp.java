package ru.ocupuc;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class ClientApp {


    public static void main(String[] arg) {




        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Pac-Man");
        config.setWindowedMode(690,670);
        new Lwjgl3Application(new MyGame(), config);


    }


}
