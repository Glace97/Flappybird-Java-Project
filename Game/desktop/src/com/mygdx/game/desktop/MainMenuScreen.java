package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {
    final Flappy game;
   OrthographicCamera camera;
   Texture backgroundImage;

   //konstruktor, dvs vi kan skapa objekt och sätta variablerna, som parameter
    public MainMenuScreen(final Flappy game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        this.backgroundImage = new Texture(Gdx.files.internal("backgroundImage.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
    //    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    //    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundImage, 0 , 0 , 800, 480);
        game.font.draw(game.batch, "Welcome to Flappy Bird!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
