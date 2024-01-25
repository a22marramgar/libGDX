package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.objects.Enemy;
import com.mygdx.game.objects.Player;
import com.mygdx.game.objects.ScrollHandler;
import com.mygdx.game.objects.Scrollable;
import com.mygdx.game.utils.Settings;

import java.util.ArrayList;

import helpers.AssetManager;
import helpers.InputHandler;

public class GameScreen implements Screen {
    Boolean gameOver = false;

    // Objectes necessaris
    private Stage stage;
    private Player player;
    private ScrollHandler scrollHandler;

    // Encarregats de dibuixar elements per pantalla
    private ShapeRenderer shapeRenderer;
    private Batch batch;

    private GlyphLayout textLayout;
    private Preferences prefs;
    private float explosionTime = 0;

    public GameScreen(){
        prefs = Gdx.app.getPreferences("prefs");
        // Iniciem la música
        AssetManager.music.play();

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem la càmera de les dimensions del joc
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        // Posant el paràmetre a true configurem la càmera perquè
        // faci servir el sistema de coordenades Y-Down
        camera.setToOrtho(true);

        // Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        // Creem l'stage i assginem el viewport
        stage = new Stage(viewport);

        batch = stage.getBatch();

        player = new Player(Settings.PLAYER_STARTX, Settings.PLAYER_STARTY, Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT);
        scrollHandler = new ScrollHandler();

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(player);
        // Donem nom a l'Actor
        player.setName("player");

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "GameOver");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Dibuixem i actualizem tots els actors de l'stage
        stage.draw();
        stage.act(delta);

        if (!gameOver) {
            if (scrollHandler.collides(player)) {
                stage.getRoot().findActor("player").remove();
                gameOver = true;
            }
        } else {
            batch.begin();
            AssetManager.font.draw(batch, textLayout, Settings.GAME_WIDTH/2 - textLayout.width/2, Settings.GAME_HEIGHT/2 - textLayout.height/2);
            // Si hi ha hagut col·lisió: reproduïm l'explosió
            batch.draw(
                    (TextureRegion) AssetManager.explosionAnim.getKeyFrame(explosionTime, false),
                    (player.getX() + player.getWidth() / 2) - 32,
                    player.getY() + player.getHeight() / 2 - 32,
                    64, 64);
            batch.end();

            explosionTime += delta;
        }

        drawElements();
    }

    private void drawElements() {

        // Recollim les propietats del batch de l'stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Pintem el fons de negre per evitar el "flickering"
        //Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));

        // Pintem la nau
        shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());

        // Recollim tots els Asteroid
        ArrayList<Enemy> enemies = scrollHandler.getEnemies();
        Enemy enemy;

        for (int i = 0; i < enemies.size(); i++) {

            enemy = enemies.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getWidth() / 2, enemy.getWidth() / 2);
        }
        shapeRenderer.end();

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

    public Player getPlayer() {
        return player;
    }

    public Stage getStage() {
        return stage;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }
}
