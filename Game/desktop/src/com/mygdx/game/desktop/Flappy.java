package com.mygdx.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


//Game är en abstract klass, kan inte instansieras
public class Flappy extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public void create() {
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        //setScreen metoden sätter nytt obj av MainmenuScreenm spelinstance, flappybird
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); //important! . Without this call, the Screen that you set in the create() method will not be rendered!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }


}
