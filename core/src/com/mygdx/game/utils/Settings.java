package com.mygdx.game.utils;

public class Settings {

    // Mida del joc, s'escalarà segons la necessitat
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 480;

    // Propietats de la nau
    public static final float PLAYER_VELOCITY = 150;
    public static final int PLAYER_WIDTH = 64;
    public static final int PLAYER_HEIGHT = 64;
    public static final float PLAYER_STARTX = GAME_WIDTH / 2 - PLAYER_WIDTH / 2;;
    public static final float PLAYER_STARTY = GAME_HEIGHT - PLAYER_HEIGHT - 20;

    // Rang de valors per canviar la mida de l'asteroide
    public static final float MAX_ENEMY = 1.5f;
    public static final float MIN_ENEMY = 0.5f;

    // Configuració scrollable
    public static final int ENEMY_SPEED = 180;
    public static final int ENEMY_GAP = 75;
    public static final int BG_SPEED = 100;
}