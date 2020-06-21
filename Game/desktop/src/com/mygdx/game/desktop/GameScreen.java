package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;


public class GameScreen extends MainMenuScreen {
    final Flappy game;

    Texture birdImage;
    Texture backgroundImage;
    Texture lowerPipeImage;
    Texture upperPipeImage;

    Array<Rectangle> upperPipes;
    Array<Rectangle> lowerPipes;
    // Rectangle bird;
    Circle bird;

    // Sound flapSound;
    //Sound dropSound;
    OrthographicCamera camera;

    //increases with timePassed. Start value 2 pixels per frame
    private int speedPipes = 2;
    //lowest difficulty is 2 ms
    private int spawnDifficultyLimit = 2000;
    long lastSpawnedPipes;
    private int scaleBird = 20; //shrinking rectangle for accurate collision

    //scaling image of pipes for collision
    private int scalePipeHeight = 60;

    final int BIRD_DROPPING = 5;
    final int BIRD_RISING = 10;


    //konstruktor
    public GameScreen(final Flappy game) {
        super(game);
        this.game = game;

        // load the images for the droplet and the bird, 64x64 pixels each
        backgroundImage = new Texture(Gdx.files.internal("backgroundImage.png"));
        birdImage = new Texture(Gdx.files.internal("bird.png"));
        //pipeImage = new Texture(Gdx.files.internal("pipe.png")); OLD
        lowerPipeImage = new Texture(Gdx.files.internal("lower_pipe.png"));
        upperPipeImage = new Texture(Gdx.files.internal("upper_pipe.png"));


        // load the drop sound effect and the rain background "music"
        //  flapSound = Gdx.audio.newSound(Gdx.files.internal("flapSound.wav"));

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //create first image of bird and spawn
        //birds = new Array<Rectangle>();
        spawnBird();

        upperPipes = new Array<Rectangle>();
        lowerPipes = new Array<Rectangle>();
        spawnPipes();

    }

    private void spawnBird() {
        Circle bird = new Circle();
        bird.x = 800 / 4 - 32 / 2; // center the bird horizontally
        bird.y = 480 / 2; // bottom left corner of the bird is 20 pixels above
        // the bottom screen edge
        bird.radius = 40;
        //bird.width = 64- scaleBird;
        //bird.height = 50 - scaleBird;
        this.bird = bird;
    }

    private void spawnPipes() {
        Rectangle lowerPipe = new Rectangle();
        Rectangle upperPipe = new Rectangle();

        //leaving room for bird to pass, 64 pixels
        lowerPipe.height = MathUtils.random(0, ((480 / 2) - (64 / 2)));
        upperPipe.height = MathUtils.random(0, ((480 / 2) - (64 / 2)));

        lowerPipe.width = 64;
        upperPipe.width = 64;

        //spawn pipes at right side of screen
        lowerPipe.x = 800;
        upperPipe.x = 800;

        lowerPipe.y = 0;
        upperPipe.y = 480 - upperPipe.height;

        //keep hole reasonably small, autmatically set to smallest if too big gap
        /* CODE HERE*/

        lowerPipes.add(lowerPipe);
        upperPipes.add(upperPipe);

        //increase time, will be changed according to difficulty
        lastSpawnedPipes = TimeUtils.millis();
    }


    public void render(float delta) {
        //update matrix
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();


        game.batch.draw(backgroundImage, 0, 0, 800, 480);
        //  game.font.draw(game.batch, "Pipes passed: " + pipesPassed, 0, 480);


        //move lowerpipe
        for (Rectangle lowerPipe : lowerPipes) {
            game.batch.draw(lowerPipeImage, lowerPipe.x, lowerPipe.y, lowerPipe.width, lowerPipe.height + scalePipeHeight);
            lowerPipe.x -= speedPipes;
        }

        //move upperpipe
        for (Rectangle upperPipe : upperPipes) {
            game.batch.draw(upperPipeImage, upperPipe.x, upperPipe.y, upperPipe.width, upperPipe.height + scalePipeHeight);
            upperPipe.x -= speedPipes;
        }

        //scaleBird is scaling image of bird, circle obj stays the same
        game.batch.draw(birdImage, bird.x, bird.y, (bird.radius * 2) - scaleBird, (bird.radius * 2) - scaleBird);
        bird.y -= BIRD_DROPPING;

        //bird stays within frame
        if (bird.y < 0) {
            bird.y = 0;
        }

        if (bird.y > (480 - (bird.radius * 2))) {
            bird.y = (480 - (bird.radius * 2));
        }

        //move bird
        if (Gdx.input.isTouched()) {
            bird.y += BIRD_RISING;
        }


        //check if new pipes needs to be generated
        if (TimeUtils.millis() - lastSpawnedPipes > spawnDifficultyLimit) {
            spawnPipes();
        }


        Iterator<Rectangle> iterator = lowerPipes.iterator();
        {
            while (iterator.hasNext()) {
                Rectangle lowerPipe = iterator.next();
                if (Intersector.overlaps(bird, lowerPipe)) {
                 /*   try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    game.batch.draw(backgroundImage, 0, 0, 800, 480);
                    game.font.draw(game.batch, "GAME OVER", 100, 150);
                }

            }
            game.batch.end();
        }

    }




    @Override
    public void show() {
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

