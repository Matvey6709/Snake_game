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
//                return base.test();
            }

            @Override
            public Player requestData2() {
                return base.requestData2();
            }


            @Override
            public void put(String nameGame, String namePlayer, String vector2, String level, int appleX, int appleY) {
                base.putData(nameGame, namePlayer, vector2, level, appleX, appleY);
            }

            @Override
            public String checkStartPlay() {
                return base.checkStartPlay();
            }

            @Override
            public String checkStartPlay2() {
                return base.checkStartPlay2();
            }

            @Override
            public int countPlayersGames(String nameGame) {
                return base.countPlayersGames(nameGame);
            }

            @Override
            public int countPlayersGames2() {
                return base.countPlayersGames2();
            }

            @Override
            public String getFirstPlayer(String nameGame) {
                return base.getFirstPlayer(nameGame);
            }

            @Override
            public void dispose() {
                base.dispose();
            }
        };
        Toast.makeText(this, "Чтобы избежать ошибок, придумайте разные имена игроков", Toast.LENGTH_LONG).show();
        initialize(new game(loaded), config);
//        initialize(new ScrollTest(), config);
    }


}

