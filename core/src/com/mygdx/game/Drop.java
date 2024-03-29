package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.GameScreen;

import helpers.AssetManager;

public class Drop extends Game {

    @Override
    public void create() {

        // A l'iniciar el joc carreguem els recursos
        AssetManager.load();
        // I definim la pantalla principal com a la pantalla
        setScreen(new GameScreen());

    }

    // Cridem per descartar els recursos carregats.
    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }

}