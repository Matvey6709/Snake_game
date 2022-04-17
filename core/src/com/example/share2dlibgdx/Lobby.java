package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import handler.LabelHandler;

public class Lobby implements Screen {



    Table table;
    OrthographicCamera camera;
    boolean show = false;
    TextureRegion backgroundTexture;
    Skin skin;

    String namePlayer = "Matmeyker";
    Label label;

    FitViewport fitViewport;
//    ScreenViewport fitViewport;

    int numGame = 0;

    BitmapFont font;

    game game;

    public Lobby(final game gam) {
        game = gam;
        backgroundTexture = new TextureRegion(new Texture("bac2.jpg"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 600, 400);
        fitViewport = new FitViewport(600, 400, camera);
//        fitViewport = new ScreenViewport(camera);

        game.loaded.create();

        game.stage = new Stage();
        game.stage.getViewport().setCamera(camera);
        Screen();

        Gdx.input.setInputProcessor(game.stage);

        fitViewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 1, 1);//Цвет экрана
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, 1280, 720);
        game.batch.end();

        game.stage.act(Gdx.graphics.getDeltaTime());
        game.stage.draw();
    }

    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
//        skin.dispose();
//        game.stage.dispose();
//        game.batch.dispose();
    }

    int y = 0;

    public void Screen() {
        table = new Table();
        table.setBounds(400, 0, 200, 400);
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("bac.jpg")));
        skin = new Skin(Gdx.files.internal("uiskin.json"));


//        font = new BitmapFont();
        TextButton button = new TextButton("Settings", skin);
        button.setPosition(450, 320);
        button.setSize(100, 60);
//        table.add(button);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings(drawable);
            }
        });

        label = LabelHandler.INSTANCE.createLabel(namePlayer, 30, Color.BLACK);
        label.setX(70);
        label.setY(350);


        TextButton startGame1 = new TextButton("Game1", skin);
        startGame1.setPosition(250, 60);
        startGame1.setSize(100, 60);

//        startGame1.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                numGame = 1;
////                fitViewport = new FitViewport(1280, 720, camera);
////                stage = new Stage();
////                stage.addActor(joystick);
//////                stage.addActor(game1.pointUi);
////                stage.addActor(game1.pointUi.FirstPointUi());//Поинт игрока 1
////                stage.addActor(game1.pointUi.SecondPointUi());//Поинт игрока 2
////                stage.addActor(game1.pointUi.apples());
////                stage.addActor(game1.pointUi.apples2());
////                stage.addActor(game1.pointUi.WinPlay());
////                game1.pointUi.WinLabel.addListener(new ChangeListener() {
////                    @Override
////                    public void changed(ChangeEvent event, Actor actor) {
////                        fitViewport = new FitViewport(600, 400, camera);
////                        stage = new Stage(fitViewport);
////                        stage.getViewport().setCamera(camera);
////                        Screen();
////                        fitViewport.apply();
////                        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
////                    }
////                });
////                Gdx.input.setInputProcessor(stage);
//
////                fitViewport.update(1280, 720);
////
////                fitViewport.apply();
////                camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
//            }
//        });
        startGame1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Нажата кнопка игры");
            }
        });

        TextButton button1 = new TextButton("TestScreen", skin);
        button1.setPosition(150, 200);
        button1.setSize(100, 60);
        button1.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {

                game.setScreen(new Game1_Screen(game, namePlayer));
                return true;
            }
        });

        game.stage.addActor(button1);
        game.stage.addActor(startGame1);
        game.stage.addActor(label);
        game.stage.addActor(table);
        game.stage.addActor(button);

//        skin.dispose();
    }

    public void settings(Drawable drawable) {
        System.out.println("GGHHH");
        if (show == true) {
            table.clear();
            table.setBackground((Drawable) null);
            this.show = false;
        } else {
            table.clear();
            table.setBackground(drawable);
            TextButton button = new TextButton("Reset Name", skin);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    ResetName();
                }
            });

            TextButton button2 = new TextButton("Reset Color", skin);
            button2.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {

                }
            });

            table.add(button).size(130, 40).padBottom(10f).row();
            table.add(button2).size(130, 40).row();
            this.show = true;

        }
    }

    public void ResetName() {
        final Table resetNameTable = new Table();
        resetNameTable.setBounds(0, 0, 600, 400);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("resetname.jpg")));
        resetNameTable.setBackground(drawable);
        final TextField textField = new TextField("New Name", skin);
        TextButton button = new TextButton("Ok", skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resetNameTable.clear();
                resetNameTable.background((Drawable) null);
                label.setText(textField.getText());
                namePlayer = textField.getText();
            }
        });
        resetNameTable.add(textField).padBottom(40f).row();
        resetNameTable.add(button).row();
        game.stage.addActor(resetNameTable);
    }
}
