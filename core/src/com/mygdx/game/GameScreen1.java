package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import helpers.AssetManager;

public class GameScreen1 implements Screen {
    final Drop game;

    private Viewport viewport;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;

    Array<Rectangle> lives;
    long lastDropTime;
    int dropsGathered;
    Array<Rectangle> enemies;
    long lastEnemyTime;
    int enemyHits;
    int nextEnemyTime = MathUtils.random(1,5);;

    final long SECOND = 1000000000;

    public GameScreen1(final Drop game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        AssetManager.load();

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;

        enemyHits = 3;
        lives = new Array<Rectangle>();
        for(int i = 0; i<enemyHits; i++){
            Rectangle live = new Rectangle();
            live.x = i * 80 + 64/2;
            live.y = 400;
            live.width = 64;
            live.height = 64;
            lives.add(live);
        }

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        enemies = new Array<Rectangle>();
        spawnRaindrop();

    }

    private void spawnEnemies() {
        Rectangle enemy = new Rectangle();
        enemy.x = MathUtils.random(0, 800 - 64);
        enemy.y = 480;
        enemy.width = 64;
        enemy.height = 64;
        enemies.add(enemy);
        lastEnemyTime = TimeUtils.nanoTime();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        /*game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 400);
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        for (Rectangle enemy : enemies) {
            game.batch.draw(bucketImage, enemy.x, enemy.y);
        }
        for(Rectangle live : lives) {
            game.batch.draw(lifeImage, live.x, live.y,64,64);
        }
        game.batch.end();*/

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            bucket.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                //dropSound.play();
                iter.remove();
            }
        }

        // check if we need to create a new enemy
        if (TimeUtils.nanoTime() - lastEnemyTime > nextEnemyTime * SECOND){
            nextEnemyTime = MathUtils.random(1,5);
            spawnEnemies();
        }


        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter2 = enemies.iterator();
        while (iter2.hasNext()) {
            Rectangle enemy = iter2.next();
            enemy.y -= 500 * Gdx.graphics.getDeltaTime();
            if (enemy.y + 64 < 0)
                iter2.remove();
            if (enemy.overlaps(bucket)) {
                enemyHits--;
                lives.pop();
                iter2.remove();
                if(enemyHits <= 0){
                    game.setScreen(new GameOverScreen(game));
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        //rainMusic.play();
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
        /*dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();*/
    }

}