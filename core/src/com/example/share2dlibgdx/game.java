package com.example.share2dlibgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import datamanager.InterfaceDataLoaded;


public class game extends Game {

    SpriteBatch batch;
    InterfaceDataLoaded loaded;
    Stage stage;

    Viewport fitViewport;

    Lobby lobby;
    public game(InterfaceDataLoaded loaded) {
        this.loaded = loaded;
    }

    @Override
    public void create() {
//        backgroundTexture = new TextureRegion(new Texture("bac2.jpg"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        camera = new OrthographicCamera();
//        fitViewport = new FitViewport(600, 400, camera);
//
        batch = new SpriteBatch();

//        game1 = new Game1(batch, joystick, loaded);
//
//        stage = new Stage(fitViewport);
//        stage.getViewport().setCamera(camera);
//        Screen();
//
//        Gdx.input.setInputProcessor(stage);
//
//        fitViewport.apply();
//        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        lobby = new Lobby(this);
//        setScreen(new Game1_Screen(this, "Mat", "2535381650890042343", false));
        setScreen(new Lobby(this));
    }

    @Override
    public void render() {
//        camera.update();
//        clearScreen();
//        ScreenUtils.clear(0, 1, 1, 1);//Цвет экрана
//        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//
//
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//        batch.draw(backgroundTexture, 0, 0, 1280, 720);
//
//        switchGameRender(numGame);
//
//        batch.end();
//
//        stage.act(Gdx.graphics.getDeltaTime());
//        stage.draw();
        super.render();
    }


    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();

    }

//    public void Screen() {
//        table = new Table();
//        table.setBounds(400, 0, 200, 400);
//        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("bac.jpg")));
//        skin = new Skin(Gdx.files.internal("uiskin.json"));
//
//
//        font = new BitmapFont();
//        TextButton button = new TextButton("Settings", skin);
//        button.setPosition(500, 300);
//        button.setSize(70, 55);
//        table.add(button);
//
//        label = LabelHandler.INSTANCE.createLabel("namePlayer", 30, Color.BLACK);
//        label.setX(70);
//        label.setY(350);
//        button.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                settings(drawable);
//            }
//        });
//
//        TextButton startGame1 = new TextButton("Game1", skin);
//        startGame1.setPosition(250, 60);
//        startGame1.setSize(100, 60);
//
//        startGame1.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                numGame = 1;
//                fitViewport = new FitViewport(1280, 720, camera);
//                stage = new Stage();
//                stage.addActor(joystick);
////                stage.addActor(game1.pointUi);
//                stage.addActor(game1.pointUi.FirstPointUi());//Поинт игрока 1
//                stage.addActor(game1.pointUi.SecondPointUi());//Поинт игрока 2
//                stage.addActor(game1.pointUi.apples());
//                stage.addActor(game1.pointUi.apples2());
//                stage.addActor(game1.pointUi.WinPlay());
//                game1.pointUi.WinLabel.addListener(new ChangeListener() {
//                    @Override
//                    public void changed(ChangeEvent event, Actor actor) {
//                        fitViewport = new FitViewport(600, 400, camera);
//                        stage = new Stage(fitViewport);
//                        stage.getViewport().setCamera(camera);
//                        Screen();
//                        fitViewport.apply();
//                        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
//                    }
//                });
//                Gdx.input.setInputProcessor(stage);
//
////                fitViewport.update(1280, 720);
//
//                fitViewport.apply();
//                camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
//            }
//        });
//
//        TextButton button1 = new TextButton("TestScreen", skin);
//        button1.setPosition(50, 60);
//        button1.setSize(100, 60);
//        button1.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                setScreen(new MainMenuScreen(game.this));
//            }
//        });
//
//        stage.addActor(button1);
//        stage.addActor(startGame1);
//        stage.addActor(label);
//        stage.addActor(table);
//        stage.addActor(button);
//
//
//    }
//
//    public void settings(Drawable drawable) {
//        if (show == true) {
//            table.clear();
//            table.setBackground((Drawable) null);
//            this.show = false;
//        } else {
//            table.clear();
//            table.setBackground(drawable);
//            TextButton button = new TextButton("Reset Name", skin);
//            button.addListener(new ChangeListener() {
//                @Override
//                public void changed(ChangeEvent event, Actor actor) {
//                    ResetName();
//                }
//            });
//
//            TextButton button2 = new TextButton("Reset Color", skin);
//            button2.addListener(new ChangeListener() {
//                @Override
//                public void changed(ChangeEvent event, Actor actor) {
//
//                }
//            });
//
//            table.add(button).size(130, 40).padBottom(10f).row();
//            table.add(button2).size(130, 40).row();
//            this.show = true;
//
//        }
//    }
//
//    public void ResetName() {
//        final Table resetNameTable = new Table();
//        resetNameTable.setBounds(0, 0, 600, 400);
//        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("resetname.jpg")));
//        resetNameTable.setBackground(drawable);
//        final TextField textField = new TextField("New Name", skin);
//        TextButton button = new TextButton("Ok", skin);
//        button.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                resetNameTable.clear();
//                resetNameTable.background((Drawable) null);
//                label.setText(textField.getText());
//                game1.share.NamePlayer = textField.getText();
//            }
//        });
//        resetNameTable.add(textField).padBottom(40f).row();
//        resetNameTable.add(button).row();
//        stage.addActor(resetNameTable);
//    }

    //    Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture);
//    ImageButton playButton = new ImageButton(drawable);

//    public void switchGameRender(int numGame) {
//        switch (numGame) {
//            case 1:
//                    game1.render(batch);
//                    break;
//        }
//    }
}