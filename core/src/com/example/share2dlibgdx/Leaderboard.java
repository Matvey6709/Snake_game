package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

import handler.LabelHandler;

public class Leaderboard implements Screen {
    private Stage stage;
    FitViewport fitViewport;
    OrthographicCamera camera;

    float gameWidth, gameHeight;
    List<String> list;

    Skin skin;

    private static final String reallyLongString = "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n";


    String roms[];

    game game;

    public Leaderboard(final game gam, final String namePlayer) {
        this.game = gam;

        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        fitViewport = new FitViewport(600, 400, camera);

        this.stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(this.stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        final Table scrollTable = new Table();

//
//
//        final Label text = LabelHandler.INSTANCE.createLabel(reallyLongString, 40, Color.WHITE);
//        text.setAlignment(Align.center);
//        text.setWrap(true);
//        final Label text2 = LabelHandler.INSTANCE.createLabel("This is a short string!", 40, Color.WHITE);
//        text2.setAlignment(Align.center);
//        text2.setWrap(true);
//        final Label text3 = LabelHandler.INSTANCE.createLabel(reallyLongString, 40, Color.WHITE);
//        text3.setAlignment(Align.center);
//        text3.setWrap(true);
//        scrollTable.add(text);
//        scrollTable.row();
//        scrollTable.add(text2);
//        scrollTable.row();
//        scrollTable.add(text3);
//        scrollTable.row();
//        scroller.setTransform(true);
//        scroller.setBounds(100, 500, gameWidth, gameHeight + 100);
//        scroller.setSmoothScrolling(true);
//        scroller.setPosition(500, 0);

        game.loaded.create();
        game.loaded.checkStartPlay();

        Label text = LabelHandler.INSTANCE.createLabel("choose a room", 30, Color.WHITE);
        text.setPosition(30, 300);

        TextButton reset = new TextButton("reboot", skin);
        reset.setSize(80, 80);
        reset.setPosition(350, 30);
        reset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    roms = game.loaded.checkStartPlay2().replace("null", "").split(" ");
                    System.out.println(roms.length);
                    System.out.println(game.loaded.checkStartPlay2());

                    scrollTable.clear();
                    for (int i = 0; i < roms.length; i++) {
                        TextButton button = new TextButton(roms[i], skin);
                        final int finalI = i;
                        button.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                game.loaded.dispose();
                                game.setScreen(new Game1_Screen(game, namePlayer, roms[finalI], false));
                            }
                        });
                        scrollTable.add(button).size(400, 120);
                        scrollTable.row();
                    }
                } catch (Exception e) {

                }
            }
        });

        TextButton newGame = new TextButton("New game", skin);
        newGame.setSize(100, 80);
        newGame.setPosition(450, 30);
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.loaded.dispose();
                game.setScreen(new Game1_Screen(game, namePlayer, new Random().nextInt(900000) + 100000 + "" + System.currentTimeMillis(), true));
            }
        });


        final ScrollPane scroller = new ScrollPane(scrollTable);
        scroller.setSmoothScrolling(true);
        scroller.setTransform(true);
        scroller.setScale(0.6f);


        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();


        this.stage.addActor(table);
        this.stage.addActor(reset);
        this.stage.addActor(text);
        this.stage.addActor(newGame);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.stage.act();
        this.stage.draw();
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
        stage.dispose();
        skin.dispose();
    }
}
 