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
    Texture lowerPipeImage;
    Texture upperPipeImage;

    Array<Rectangle> upperPipes;
    Array<Rectangle> lowerPipes;
    Array<Rectangle> birds;

    // Sound flapSound;
    //Sound dropSound;
    OrthographicCamera camera;

    //increases with timePassed. Start value 2 pixels per frame
    private int speedPipes = 2;
    private int generatePipesLimit = 400; //när pipens x posistion lämnar p avstånd till höger, generera ny pipe
    private long timePassed;

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
        birds = new Array<Rectangle>();
        spawnBird();

        upperPipes = new Array<Rectangle>();
        lowerPipes = new Array<Rectangle>();
        spawnPipes();

    }

    private void spawnBird(){
        Rectangle bird = new Rectangle();
        bird.x = 800 / 4 - 32 / 2; // center the bird horizontally
        bird.y = 480/2; // bottom left corner of the bird is 20 pixels above
        // the bottom screen edge
        bird.width = 64;
        bird.height = 50;
        birds.add(bird);
    }

    private void spawnPipes() {
        Rectangle lowerPipe = new Rectangle();
        Rectangle upperPipe = new Rectangle();
        Rectangle nrOfPipes = new Rectangle();

        //leaving room for bird to pass, 64 pixels
        lowerPipe.height = lowerPipe.height = MathUtils.random(0, ( (480/2) - (64/2) ));
        upperPipe.height = MathUtils.random(0, ( (480/2) - (64/2) ));

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
        timePassed = TimeUtils.nanoTime();
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
        for(Rectangle lowerPipe : lowerPipes) {
            game.batch.draw(lowerPipeImage, lowerPipe.x, lowerPipe.y, lowerPipe.width, lowerPipe.height);
            lowerPipe.x -= speedPipes;
        }

        //move upperpipe
        for(Rectangle upperPipe : upperPipes) {
            game.batch.draw(upperPipeImage, upperPipe.x, upperPipe.y, upperPipe.width, upperPipe.height);
            upperPipe.x -= speedPipes;
        }


        //move bird
        for(Rectangle bird : birds) {
            game.batch.draw(birdImage, bird.x, bird.y, bird.width, bird.height);
            bird.y -= BIRD_DROPPING;
            if(bird.y < 0) {
                bird.y = 0;
            }
            if (Gdx.input.isTouched()) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                bird.y += BIRD_RISING;
            }
        }
        game.batch.end();
    }

    //check if need to spawn new set of pipes

   /*

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();
*/


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

