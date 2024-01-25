package com.mygdx.game.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.utils.Methods;
import com.mygdx.game.utils.Settings;

import java.util.ArrayList;
import java.util.Random;

public class ScrollHandler extends Group {

    // Fons de pantalla
    Background bg, bg_back;

    // Asteroides
    int numAsteroids;
    ArrayList<Enemy> enemies;

    // Objecte Random
    Random r;

    public ScrollHandler() {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(0, bg.getTailY(), Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        // Afegim els fons al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();

        // Comencem amb 3 asteroides
        numAsteroids = 3;

        // Creem l'ArrayList
        enemies = new ArrayList<Enemy>();

        // Definim una mida aleatòria entre el mínim i el màxim
        float newSize = 64;
        //float newSize = Methods.randomFloat(Settings.MIN_ENEMY, Settings.MAX_ENEMY) * 34;

        // Afegim el primer asteroide a l'array i al grup
        Enemy enemy = new Enemy(MathUtils.random(0, 800 - 64), 0, newSize, newSize, Settings.ENEMY_SPEED);
        enemies.add(enemy);
        addActor(enemy);

// Des del segon fins l'últim asteroide
        for (int i = 1; i < numAsteroids; i++) {
// Creem la mida aleatòria
            newSize = 64;
            //newSize = Methods.randomFloat(Settings.MIN_ENEMY, Settings.MAX_ENEMY) * 34;
// Afegim l'asteroide
            enemy = new Enemy(enemies.get(enemies.size() - 1).getTailY() + Settings.ENEMY_GAP, 0, newSize, newSize, Settings.ENEMY_SPEED);
// Afegim l'asteroide a l'ArrayList
            enemies.add(enemy);
// Afegim l'asteroide al grup d'actors
            addActor(enemy);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
// Si algun element es troba fora de la pantalla, fem un reset de l'element
        if (bg.isDownOfScreen()) {
            bg.reset(bg_back.getTailY());

        } else if (bg_back.isDownOfScreen()) {
            bg_back.reset(bg.getTailY());

        }

        for (int i = 0; i < enemies.size(); i++) {

            Enemy enemy = enemies.get(i);
            if (enemy.isDownOfScreen()) {
                if (i == 0) {
                    enemy.reset(enemies.get(enemies.size() - 1).getTailY() + Settings.ENEMY_GAP);
                } else {
                    enemy.reset(enemies.get(i - 1).getTailY() + Settings.ENEMY_GAP);
                }
            }
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public boolean collides(Player player) {

// Comprovem les col·lisions entre cada asteroide i la nau
        for (Enemy enemy : enemies) {
            if (enemy.collides(player)) {
                return true;
            }
        }
        return false;
    }
}
