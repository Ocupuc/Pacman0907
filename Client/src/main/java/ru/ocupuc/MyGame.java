package ru.ocupuc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import static ru.ocupuc.GameData.*;

public class MyGame extends ApplicationAdapter {


    Network network = new Network((args) -> { });
    KeyboardTracker keyboardTracker = new KeyboardTracker(network);

    private static final int CELL_SIZE = 30;  // размер клетки


    private List<Texture> pacmanTextures = new ArrayList<Texture>();

    private Texture pacmanTexture;
    private Texture wallTexture;
    private Texture pillTexture;



    @Override
    public void create() {

        Gdx.input.setInputProcessor(keyboardTracker);
        pacmanTexture = new Texture(Gdx.files.internal("pacman.png"));
        wallTexture = new Texture(Gdx.files.internal("wall.png"));
        pillTexture = new Texture(Gdx.files.internal("pill.png"));



    }

    @Override
    public void render() {

        ScreenUtils.clear(0f, 0f, 0.1f, 1.0f);
        SpriteBatch batch = new SpriteBatch();
        batch.begin();

        for (Pacman pacman : enemyPacmans) {
            batch.draw(pacmanTexture, pacman.getX() * CELL_SIZE, pacman.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            pacman.animate();
        }

        for (Vector2 wall : gameMap.getWalls()) {
            batch.draw(wallTexture, wall.x * CELL_SIZE, wall.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        for (Vector2 pill : gameMap.getPills()) {
            batch.draw(pillTexture, pill.x * CELL_SIZE, pill.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        if (myPacman != null) {
            batch.draw(pacmanTexture, myPacman.getX() * CELL_SIZE, myPacman.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
        batch.end();
    }




    @Override
    public void dispose() {
        pacmanTexture.dispose();
        wallTexture.dispose();
        pillTexture.dispose();
    }
}
