//package com.example.share2dlibgdx;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g3d.particles.influencers.ModelInfluencer;
//import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.example.share2dlibgdx.ui.Joystick3;
//
//import java.util.Random;
//
//import datamanager.InterfaceDataLoaded;
//import datamanager.ServerUpdate;
//
//public class Game1 {
//
//    Share share;
//    Bread bread;
//    Touch touch;
//    InterfaceDataLoaded loaded;
//    float timeSet;
//    ServerUpdate serverUpdate;
//    Joystick3 joystick;
//    SpriteBatch batch;
//    boolean startPlay = false;
//    boolean t = false;
//    int nulls = 0;
//    DeterminantSize size;
//    boolean f = false;
//    PointUi pointUi;
//    boolean create = false;
//
//    public Game1(SpriteBatch batch, Joystick3 joystick, InterfaceDataLoaded loaded) {
//        this.joystick = joystick;
//        this.loaded = loaded;
//        size = new DeterminantSize();
//        share = new Share(batch, joystick, size.getWidthGame(100), size.getHeightGame(100));
//        bread = new Bread(batch, size.getWidthGame(100));
//        bread.spawn();
//        touch = new Touch(share, bread);
//        serverUpdate = new ServerUpdate();
//        serverUpdate.test();
//        pointUi = new PointUi();
//    }
//
//
//    public void render(SpriteBatch batch) {
//        if (startPlay) {
//            try {
////                Цыфры 3... 2... 1... GO..
//
//                bread.render();
//                share.render(Gdx.graphics.getDeltaTime());
//                if (timeSet > .50) {
//                    share.PlayerClient();
////                    loaded.put(share.NameGame, share.NamePlayer, share.vector2, share.level + "", bread.x);
//                    serverUpdate.render(loaded.requestData(share.NameGame, share.NamePlayer, share), share, batch);
//                    timeSet = 0;
//                }
//                serverUpdate.render2();
//                init();
//                timeSet += Gdx.graphics.getDeltaTime();
////                t = false;
//                pointUi.render(batch, share.level, serverUpdate.getLevel(), share.NamePlayer, serverUpdate.getNamePlayer());
//
//                for (int i = 0; i < serverUpdate.getLevel(); i++) {
//                    boolean g = touch.touchPlays(share, serverUpdate.cells.get(i).x, serverUpdate.cells.get(i).y);
//                    if (g) {
//                        if (create) {
//                            share.cells.get(0).x = 1200;
//                        } else {
//                            share.cells.get(0).x = 0;
//                        }
//                        share.cells.get(0).y = 0;
//                        g = false;
//                        break;
//                    }
//                }
//
//                if (share.level >= 8) {
//                    pointUi.WhoWin(share.NamePlayer);
//                    end = true;
//                } else if (serverUpdate.getLevel() >= 8 && serverUpdate.getNamePlayer() != null) {
//                    pointUi.WhoWin(serverUpdate.getNamePlayer());
//                    end = true;
//                }
////                Если у игрока будет 8 очков(яблок), то он побеждает(end())
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (loaded.checkStartPlay() != null && !t) {
//            share.NameGame = loaded.checkStartPlay();
//            startPlay = true;
//            System.out.println("Выполнено2");
//        } else if (loaded.checkStartPlay() == null) {
//            nulls++;
//            System.out.println("Выполнено3");
//        }
//        if (nulls == 200) {
//            if (!t) {
//                share.PlayerClient();
//                share.NameGame = new Random().nextInt(6) + "";
//                share.cells.get(0).x = 1200;
//                share.cells.get(0).y = 0;
//                create = true;
////                loaded.put(share.NameGame, share.NamePlayer, share.vector2, share.level + "");
//                t = true;
//                System.out.println("Выполнено4");
//            }
//            if (loaded.countPlayersGames(share.NameGame) == 2 && !startPlay) {
//                startPlay = true;
//                System.out.println("Выполнено");
//            }
//        }
//    }
//
//
//    boolean end = false;
//
//    public boolean end() {
//        return end;
//    }
//
//    public void init() {
//        if (touch.touchBred()) {
//            bread.spawn();
//            share.addLevel();
//        }
//    }
//
//    public void dispose() {
//        share.dispose();
//        batch.dispose();
//        serverUpdate.dispose();
//    }
//}
