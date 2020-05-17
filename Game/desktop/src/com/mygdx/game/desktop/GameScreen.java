package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;


public class GameScreen extends MainMenuScreen {
    final Flappy game;

    Texture birdImage;
    Texture backgroundImage;
   // Sound flapSound;
    //Sound dropSound;
    OrthographicCamera camera;
    Rectangle bird;
    Array<Rectangle> pipes;
    long lastDropTime;
    int pipesPassed;


    //konstruktor
    public GameScreen(final Flappy game) {
        super(game);
        this.game = game;

        // load the images for the droplet and the bird, 64x64 pixels each
        backgroundImage = new Texture(Gdx.files.internal("backgroundImage.png"));
        birdImage = new Texture(Gdx.files.internal("bird.png"));

        // load the drop sound effect and the rain background "music"
      //  flapSound = Gdx.audio.newSound(Gdx.files.internal("flapSound.wav"));

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // create a Rectangle to logically represent the bird

        bird = new Rectangle();
        bird.x = 800 / 4 - 32 / 2; // center the bird horizontally
        bird.y = 480/2; // bottom left corner of the bird is 20 pixels above
        // the bottom screen edge
        bird.width = 64;
        bird.height = 50;

        // create the raindrops array and spawn the first raindrop
     //   pipes = new Array<Rectangle>();
        //spawnRaindrop();
    }

/*
    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

*/
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
    //   Gdx.gl.glClearColor(0, 0, 0.2f, 1);
      //  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update matrix
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bird and
        // all drops
        game.batch.begin();

        game.font.draw(game.batch, "Pipes passed: " + pipesPassed, 0, 480);
        game.batch.draw(backgroundImage, 0, 0, 800, 480);
        game.batch.draw(birdImage, bird.x, bird.y, bird.width, bird.height);
       /* for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }*/
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bird.x = touchPos.x - 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            bird.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            bird.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure the bird stays within the screen bounds
        if (bird.x < 0)
            bird.x = 0;
        if (bird.x > 800 - 64)
            bird.x = 800 - 64;
/*
        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();
*/
        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bird. In the later case we increase the
        // value our drops counter and add a sound effect.

        /*
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(bird)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }

         */
    }

    @Override
    public void show(){
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        birdImage.dispose();
    }


}

