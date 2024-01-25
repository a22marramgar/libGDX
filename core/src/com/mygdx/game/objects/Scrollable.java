package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.utils.Settings;

public class Scrollable extends Actor {
    protected Vector2 position;
    protected float velocity;
    protected float width;
    protected float height;
    protected boolean downOfScreen;

    public Scrollable(float x, float y, float width, float height, float velocity) {
        position = new Vector2(x, y);
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        downOfScreen = false;

    }

    public void act(float delta) {

        // Desplacem l'objecte en l'eix d'X
        position.y += velocity * delta;

        // Si es troba fora de la pantalla canviem la variable a true
        if (position.y + height > Settings.GAME_HEIGHT) {
            downOfScreen = true;
        }
    }

    public void reset(float newY) {
        position.y = newY;
        downOfScreen = false;
    }

    public boolean isDownOfScreen() {
        return downOfScreen;
    }

    public float getTailY() {
        return position.y + height;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
