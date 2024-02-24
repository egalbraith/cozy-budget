package com.cozybudget.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.cozybudget.game.CozyBudget;

public class GameScreen implements Screen {
    final CozyBudget game;

    Texture catImage;
    Rectangle cat;
    OrthographicCamera camera;

    public GameScreen(final CozyBudget game) {
        this.game = game;

        catImage = new Texture(Gdx.files.internal("cat-banana.gif"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);

        cat = new Rectangle();
        cat.x = (float) 800 / 2 - (float) 64 / 2;
        cat.y = 20;
        cat.width = 64;
        cat.height = 64;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#9FAF90"));

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(catImage, cat.x, cat.y, cat.width, cat.height);
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            cat.x = touchPos.x - (float) 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cat.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cat.x += 200 * Gdx.graphics.getDeltaTime();
        }

        // make sure the bucket stays within the screen bounds
        if (cat.x < 0) {
            cat.x = 0;
        }
        if (cat.x > 800 - 64) {
            cat.x = 800 - 64;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        catImage.dispose();
    }
}
