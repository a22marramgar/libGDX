package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.utils.Methods;
import com.mygdx.game.utils.Settings;

import java.util.Random;

import helpers.AssetManager;

public class Enemy extends Scrollable{

    private Rectangle collisionRectangle;

    Random r;

    int assetEnemy;
    public Enemy(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
        collisionRectangle = new Rectangle();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Actualitzem el cercle de col·lisions (punt central de l'asteroide i el radi.
        collisionRectangle.set(position.x + width / 2.0f, position.y + height / 2.0f, width / 2.0f, height / 2.0f);


    }

    public void setOrigin() {

        this.setOrigin(width/2 + 1, height/2);

    }

    @Override
    public void reset(float newY){
        super.reset(newY);

        float newSize = 64;
// Modificarem l'alçada i l'amplada segons l'aleatori anterior
        width = height = 64;
// La posició serà un valor aleatori entre 0 i l'alçada de l'aplicació menys l'alçada de l'asteroide
        position.x = new Random().nextInt(Settings.GAME_WIDTH - (int) width);
        setOrigin();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.enemy, position.x, position.y, width, height);
    }

    public boolean collides(Player player) {

        if (position.x <= player.getX() + player.getWidth()) {
// Comprovem si han col·lisionat sempre que l'asteroide estigui a la mateixa alçada que l'spacecraft
            return (Intersector.overlaps(collisionRectangle, player.getCollisionRect()));
        }
        return false;
    }
}
