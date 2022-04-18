package com.example.share2dlibgdx;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import datamanager.InterfaceDataLoaded;
import datamanager.Player;

public class AndroidLauncher extends AndroidApplication {

    FireBaseDataBase base;
    InterfaceDataLoaded loaded;
    AndroidLauncher context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        loaded = new InterfaceDataLoaded() {

            @Override
            public void create() {
                base = new FireBaseDataBase();
                base.Create();

            }

            @Override
            public Player requestData(String nameGame, String namePlayer, Snake share) {
                return base.requestData(nameGame, namePlayer, share);
            }


            @Override
            public void put(String nameGame, String namePlayer, String vector2, String level) {
                base.putData(nameGame, namePlayer, vector2, level);
            }

            @Override
            public String checkStartPlay() {
                return base.checkStartPlay();
            }

            @Override
            public int countPlayersGames(String nameGame) {
                return base.countPlayersGames(nameGame);
            }

            @Override
            public String getFirstPlayer(String nameGame) {
                return base.getFirstPlayer(nameGame);
            }
        };
        Toast.makeText(this, "Чтобы избежать ошибок, придумайте разные имена игроков", Toast.LENGTH_LONG).show();
        initialize(new game(loaded), config);
    }


}

