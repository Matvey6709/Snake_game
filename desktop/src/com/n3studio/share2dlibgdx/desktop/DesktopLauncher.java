package com.n3studio.share2dlibgdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.n3studio.share2dlibgdx.Snake;
import com.n3studio.share2dlibgdx.game;

import datamanager.InterfaceDataLoaded;
import datamanager.Player;

public class DesktopLauncher {
    private static InterfaceDataLoaded loaded;

    //	InterfaceDataLoaded loaded;
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        loaded = new InterfaceDataLoaded() {
            @Override
            public Player requestData(String nameGame, String namePlayer, Snake share) {
                return null;
            }

            @Override
            public Player requestData2() {
                return null;
            }

            @Override
            public void create() {

            }

            @Override
            public void put(String nameGame, String namePlayer, String vector2, String level, int appleX, int appleY) {

            }

            @Override
            public String checkStartPlay() {
                return null;
            }

            @Override
            public String checkStartPlay2() {
                return null;
            }

            @Override
            public int countPlayersGames(String nameGame) {
                return 0;
            }

            @Override
            public int countPlayersGames2() {
                return 0;
            }

            @Override
            public String getFirstPlayer(String nameGame) {
                return null;
            }

            @Override
            public void dispose() {

            }

            @Override
            public void dispose2() {

            }
        };
        new LwjglApplication(new game(loaded), config);
    }
}
