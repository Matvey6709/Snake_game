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
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import handler.FontSizeHandler;
import handler.LabelHandler;

public class Lobby implements Screen {
    Table table;
    OrthographicCamera camera;
    boolean show = false;
    TextureRegion backgroundTexture;
    Skin skin;

    String namePlayer = "Default";
    Label label;

    Viewport fitViewport;
//    ScreenViewport fitViewport;

    int numGame = 0;

    BitmapFont font;

    game game;
    Stage stage;
    Texture t;


    public Lobby(final game gam) {
        game = gam;
        t = new Texture("bac2.jpg");
        backgroundTexture = new TextureRegion(t, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCamera();
        fitViewport = new FitViewport(1280, 720, camera);

        stage = new Stage(fitViewport);
        stage.getViewport().setCamera(camera);
        Screen();

        Gdx.input.setInputProcessor(stage);
    }

    float f;
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);//Цвет экрана
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

//        camera.update();
        fitViewport.apply();
        f += delta;
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, 1280, 720);
        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void show() {
        t = new Texture("bac2.jpg");
        backgroundTexture = new TextureRegion(t, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCamera();
        fitViewport = new FitViewport(1280, 720, camera);

        stage = new Stage(fitViewport);
        stage.getViewport().setCamera(camera);
        Screen();

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void resize(int width, int height) {
        fitViewport.update(width, height, true);
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
        stage.dispose();
        t.dispose();
    }

    public void Screen() {
        table = new Table();
        table.setBounds(880, 0, 400, 720);
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("bac.jpg")));
        skin = new Skin(Gdx.files.internal("uiskin.json"));


//        font = new BitmapFont();
        TextButton button = new TextButton("", skin);
        button = new TextButton("Settings", setStyle(skin, button, 40));
        button.setPosition(990, 576);
        button.setSize(200, 100);
//        table.add(button);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings(drawable);
            }
        });

        label = LabelHandler.INSTANCE.createLabel(namePlayer, 87, Color.BLACK);
        label.setX(140);
        label.setY(600);

        TextButton startGame1 = new TextButton("StartOnlineGame", skin);
        startGame1 = new TextButton("TestButton", setStyle(skin, startGame1, 40));
        startGame1.setPosition(454, 108);
        startGame1.setSize(300, 120);

        startGame1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.loaded.isOnline()) {
                    if (namePlayer.equals("Default")) {
                        namePlayer = new Random().nextInt(900) + 100 + "" + System.currentTimeMillis();
                    }
                    game.setScreen(new Leaderboard(game, namePlayer));
                } else {
                    game.loaded.toast("Включите интернет");
                }
            }
        });

        TextButton startGame2 = new TextButton("StartGame", skin);
        startGame2 = new TextButton("StartGame", setStyle(skin, startGame2, 40));
        startGame2.setPosition(54, 108);
        startGame2.setSize(300, 120);
        startGame2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Game2_Screen(game));
            }
        });

        stage.addActor(startGame1);
        stage.addActor(startGame2);
        stage.addActor(label);
        stage.addActor(table);
        stage.addActor(button);
    }

    public void settings(Drawable drawable) {
        if (show == true) {
            table.clear();
            table.setBackground((Drawable) null);
            this.show = false;
        } else {
            table.clear();
            table.setBackground(drawable);
            TextButton button = new TextButton("Reset Name", skin);
            button = new TextButton("Reset Name", setStyle(skin, button, 35));
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    ResetName();
                }
            });

            TextButton button2 = new TextButton("Reset Color", skin);
            button2 = new TextButton("Reset Color", setStyle(skin, button2, 35));
            button2.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {

                }
            });

            table.add(button).size(260, 80).padBottom(20f).row();
            table.add(button2).size(260, 80).row();
            this.show = true;

        }
    }

    public void ResetName() {
        final Table resetNameTable = new Table();
        resetNameTable.setBounds(0, 0, 1280, 720);
        Texture tex = new Texture("resetname.jpg");
        TextureRegion t = new TextureRegion(tex);
        Drawable drawable = new TextureRegionDrawable(t);
        resetNameTable.setBackground(drawable);
        final TextField textField = new TextField("New Name", skin);
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style = textField.getStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(40, Color.WHITE);
        style.cursor = new TextureRegionDrawable();


        TextButton button = new TextButton("Ok", skin);
        button = new TextButton("Ok", setStyle(skin, button, 60));
        button.setSize(100, 100);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resetNameTable.clear();
                resetNameTable.background((Drawable) null);
                label.setText(textField.getText());
                namePlayer = textField.getText();
            }
        });
        resetNameTable.add(textField).size(300, 65).padBottom(50f).row();
        resetNameTable.add(button).row();
        stage.addActor(resetNameTable);
        tex.dispose();
    }

    public TextButton.TextButtonStyle setStyle(Skin skin, TextButton end, int size) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
        style.checked = end.getStyle().checked;
        style.up = end.getStyle().up;
        style.down = end.getStyle().down;

        return style;
    }


}
