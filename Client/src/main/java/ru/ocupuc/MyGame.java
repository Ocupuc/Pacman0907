package ru.ocupuc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import static ru.ocupuc.GameData.*;

public class MyGame extends ApplicationAdapter {
    private static final int CELL_SIZE = 30;
    private static final float FRAME_DURATION = 0.1f;

    private Texture pacmanTextureSheet;
    private Animation<TextureRegion> pacmanAnimation;
    private float elapsedTime = 0;

    private Texture wallTexture;
    private Texture pillTexture;

    Network network = new Network((args) -> { });

    KeyboardTracker keyboardTracker = new KeyboardTracker(network);



    @Override
    public void create() {
        Gdx.input.setInputProcessor(keyboardTracker);

        pacmanTextureSheet = new Texture(Gdx.files.internal("pacman1.png"));
        TextureRegion[][] pacmanFrames = TextureRegion.split(pacmanTextureSheet, 50, 50);
        pacmanAnimation = new Animation<TextureRegion>(FRAME_DURATION, pacmanFrames[0][0], pacmanFrames[1][1], pacmanFrames[2][0]);
        pacmanAnimation.setPlayMode(Animation.PlayMode.LOOP);

        wallTexture = new Texture(Gdx.files.internal("wall.png"));
        pillTexture = new Texture(Gdx.files.internal("pill.png"));
    }

    @Override
    public void render() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0f, 0f, 0.1f, 1.0f);
        SpriteBatch batch = new SpriteBatch();
        batch.begin();

        for (Pacman pacman : enemyPacmans) {
            TextureRegion currentFrame = pacmanAnimation.getKeyFrame(elapsedTime);
            batch.draw(currentFrame, pacman.getX() * CELL_SIZE, pacman.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            pacman.animate();
        }

        for (Vector2 wall : gameMap.getWalls()) {
            batch.draw(wallTexture, wall.x * CELL_SIZE, wall.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        for (Vector2 pill : gameMap.getPills()) {
            batch.draw(pillTexture, pill.x * CELL_SIZE, pill.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        if (myPacman != null) {
            TextureRegion currentFrame = pacmanAnimation.getKeyFrame(elapsedTime);
            batch.draw(currentFrame, myPacman.getX() * CELL_SIZE, myPacman.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        pacmanTextureSheet.dispose();
        wallTexture.dispose();
        pillTexture.dispose();
    }
}
