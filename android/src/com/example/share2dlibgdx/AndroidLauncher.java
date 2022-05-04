package com.example.share2dlibgdx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import datamanager.InterfaceDataLoaded;
import datamanager.Player;

public class AndroidLauncher extends AndroidApplication {

    FireBaseDataBase base;
    InterfaceDataLoaded loaded;
    final AndroidLauncher context = this;

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
            public void dispose() {
                base.dispose();
            }

            @Override
            public void dispose2() {
                base.dispose2();
            }

            @Override
            public boolean isOnline() {
                return isNetworkConnected();
            }

            @Override
            public void isOnline2(String nameGame, String namePlayer) {
                base.isOnline2(nameGame, namePlayer);
            }

            @Override
            public void toast(String text) {
                handler.post(() -> Toast.makeText(context, text, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void delete(String nameGame) {
                base.delete(nameGame);
            }

            @Override
            public void isExistsGame(String nGame, String namePlayer2) {
                base.isExistsGame(nGame, namePlayer2);
            }

            @Override
            public boolean isExistsGame2() {
                return base.isExistsGame2();
            }

            @Override
            public boolean isNamePlayer(String namePlayer) {
                return false;
            }

            @Override
            public void putNamePlayer(String namePlayer) {

            }

        };
//        Toast.makeText(this, "Включите Интернет", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Чтобы избежать ошибок, придумайте разные имена игроков", Toast.LENGTH_LONG).show();


        initialize(new game(loaded), config);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }

}

