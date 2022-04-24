package com.example.share2dlibgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import handler.LabelHandler;

public class ScrollTest implements ApplicationListener {
    private Stage stage;
    FitViewport fitViewport;
    OrthographicCamera camera;

    float gameWidth, gameHeight;
    List<String> list;

    private static final String reallyLongString = "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n";

    @Override
    public void create() {
        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();


        camera = new OrthographicCamera();
        fitViewport = new FitViewport(600, 400, camera);

        this.stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(this.stage);
        final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));


        final Label text = LabelHandler.INSTANCE.createLabel(reallyLongString, 40, Color.WHITE);
        text.setAlignment(Align.center);
        text.setWrap(true);
        final Label text2 = LabelHandler.INSTANCE.createLabel("This is a short string!", 40, Color.WHITE);
        text2.setAlignment(Align.center);
        text2.setWrap(true);
        final Label text3 = LabelHandler.INSTANCE.createLabel(reallyLongString, 40, Color.WHITE);
        text3.setAlignment(Align.center);
        text3.setWrap(true);


        final Table scrollTable = new Table();
        scrollTable.add(text);
        scrollTable.row();
        scrollTable.add(text2);
        scrollTable.row();
        scrollTable.add(text3);
        scrollTable.row();

        TextButton button = new TextButton("Hiii", skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Hiii");
            }
        });
        scrollTable.add(button).size(400, 120);


        list = new List<String>(skin);
        String[] strings = new String[40];
        for (int i = 0, k = 0; i < 40; i++) {
            strings[k++] = "String: " + i;

        }
        list.setItems(strings);

        final ScrollPane scroller = new ScrollPane(scrollTable, skin);
//        scroller.setTransform(true);
//        scroller.setBounds(100, 500, gameWidth, gameHeight + 100);
//        scroller.setSmoothScrolling(true);
//        scroller.setPosition(500, 0);

        scroller.setScale(0.7f);


        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();
        this.stage.addActor(table);


    }

    @Override
    public void render() {
        this.stage.act();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();

    }

    @Override
    public void resize(final int width, final int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}