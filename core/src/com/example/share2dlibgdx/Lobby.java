package com.example.share2dlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.GroupLayout;

import handler.FontSizeHandler;
import handler.ImageTextButtonHandler;
import handler.LabelHandler;

public class Lobby extends BaseScreen {
    Table table;
    OrthographicCamera camera;
    boolean show = false;
    TextureRegion backgroundTexture;
    Skin skin;
    String namePlayer = "Игрок";
    Label label;

    Viewport fitViewport;

    game game;
    Stage stage;
    Texture t;
    Texture level;


    Table scrollTable;
    Choosingskins choosingskins;
    ResetName resetName;
    SelectLevel selectLevel;
    Table scrollTable2;
    int i = 0;
    ArrayList<Texture> skins;
    ArrayList<BIndex> imageTextButtons;
    SelectSkin selectSkin;
    SnakeSkin m1;
    SnakeSkin m2;
    SnakeSkin m3;
    ImageTextButton buttonM1;
    ImageTextButton buttonM2;
    ImageTextButton buttonM3;

    ImageTextButton setting;
    Label settingLabel;
    Texture head;
    Texture body;
    Color GREY = new Color(217f, 217f, 217f, 1);
    Table scrollTableGames;
    ImageTextButton button;
    ImageTextButton button2;

    Сlassic_Game_Screen сlassic_game_screen;
    Levels_Game_Screen levels_game_screen;
    Leaderboard leaderboard;

    public Lobby(final game gam) {
        game = gam;

        level = new Texture("options.png");
        t = new Texture("bacLo.png");
        backgroundTexture = new TextureRegion(t, 0, 0, 1280, 720);
        camera = new OrthographicCamera();
        fitViewport = new FitViewport(1280, 720, camera);

        stage = new Stage(fitViewport);
        stage.getViewport().setCamera(camera);
        imageTextButtons = new ArrayList<>();
        skins = new ArrayList();
        buttonM3 = ImageTextButtonHandler.INSTANCE.createButtonSprite(Asset.instance().getSprite("snake_head"), "", 60, Color.WHITE);
        buttonM2 = ImageTextButtonHandler.INSTANCE.createButtonSprite(Asset.instance().getSprite("snake_body"), "", 60, Color.WHITE);
        buttonM1 = ImageTextButtonHandler.INSTANCE.createButtonSprite(Asset.instance().getSprite("snake_tail"), "", 60, Color.WHITE);
        m3 = new SnakeSkin(buttonM3, Asset.instance().getSprite("snake_head"));
        m2 = new SnakeSkin(buttonM2, Asset.instance().getSprite("snake_body"));
        m1 = new SnakeSkin(buttonM1, Asset.instance().getSprite("snake_tail"));
        Screen();
        Gdx.input.setInputProcessor(stage);
        game.loaded.create();

        head = new Texture("head.png");
        body = new Texture("body.png");

        game.bluetoothLoaded.BluetoothService();

        leaderboard = new Leaderboard(game, new Random().nextInt(900) + 100 + "" + System.currentTimeMillis(), namePlayer);
        сlassic_game_screen = new Сlassic_Game_Screen(game, false);
        levels_game_screen = new Levels_Game_Screen(game, 0);
    }


    float f;
    boolean photo = false;

    int l;


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);//Цвет экрана
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        fitViewport.apply();
        f += delta;
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, 1280, 720);
        game.batch.end();

        if (photo && game.loaded.getPhoto() != null) {
            photo = false;
            Texture s = game.loaded.getPhoto();
            final BIndex index = new BIndex(ImageTextButtonHandler.INSTANCE.createButtonSprite(new Sprite(s), "", 60, Color.WHITE), l);
            imageTextButtons.add(index);

            if (i <= 1) {
                scrollTable2.add(index.button).size(100, 100).padBottom(20).padRight(10);
                skins.add(s);
                i++;
            } else {
                scrollTable2.add(index.button).size(100, 100).padBottom(20).row();
                skins.add(s);
                i = 0;
            }
            index.button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    selectSkin.remove();
                    selectSkin.textButton.setVisible(false);
                    selectSkin.tableScrollSkin.setVisible(false);

                    selectSkin.b.button.setStyle(ImageTextButtonHandler.INSTANCE.imageTextButtonStyle(new Sprite(skins.get(index.index)), 60));
                    selectSkin.b.sprite = new Sprite(skins.get(index.index));
                }
            });
            l++;
            game.loaded.setPhoto();
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        game.loaded.create();
        game.loaded.setExistsGame(true);
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
        level.dispose();
        head.dispose();
        body.dispose();
    }

    public void Screen() {
        table = new Table();
        table.setBounds(1810, 0, 550, 720);
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("w.png")));
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        setting = ImageTextButtonHandler.INSTANCE.createButtonWay("setLob.png", "", 40, Color.WHITE, false);
        setting.setTransform(true);
        setting.setSize(60, 60);
        setting.setOrigin(setting.getWidth() / 2, setting.getHeight() / 2);
        setting.setPosition(1150, 600);
        setting.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings(drawable);
            }
        });
        settingLabel = LabelHandler.INSTANCE.createLabel("Настройки", 40, GREY);
        settingLabel.setPosition(1280, 600);

        label = LabelHandler.INSTANCE.createLabel(namePlayer, 40, GREY);
        label.setPosition(140, 600);


        final ImageTextButton startGame1 = ImageTextButtonHandler.INSTANCE.createButtonWay("butL (1).png", "Онлайн", 40, Color.WHITE, true);


        startGame1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.loaded.isOnline()) {
//                    if (namePlayer.equals("Default")) {
//                    String namePlayerU = new Random().nextInt(900) + 100 + "" + System.currentTimeMillis();
//                    }
                    System.gc();
                    game.setScreen(leaderboard);
                } else {
                    game.loaded.toast("Включите интернет");
                }
            }
        });

        ImageTextButton startGame3 = ImageTextButtonHandler.INSTANCE.createButtonWay("butL (2).png", "Уровни", 40, Color.WHITE, true);
//        startGame3.setPosition(854, 720/2-startGame3.getHeight()/2);
        startGame3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectLevel.show();
            }
        });
//        TextButton startGame4 = new TextButton("Начать игру 4", setStyle(skin, 40));
        ImageTextButton startGame4 = ImageTextButtonHandler.INSTANCE.createButtonWay("butL (3).png", "Bluetooth", 40, Color.WHITE, true);
//        startGame4.setPosition(854, 108);
        startGame4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    if (game.bluetoothLoaded.isEnabled()) {
                        leaderboard.namePlayer = namePlayer;
                        leaderboard.namePlayerUn = new Random().nextInt(900) + 100 + "" + System.currentTimeMillis();
                        System.gc();
                        game.setScreen(new LeaderboardBluetooth(game));
                    } else {
                        game.bluetoothLoaded.enableBl();
                    }
                } catch (Exception e) {
                    game.loaded.toast("У вашего устройство нет bluetooth");
                }


            }
        });

//        stage.addActor(dialog);


        scrollTableGames = new Table();
        scrollTableGames.add(startGame1).size(420, 100).padBottom(20).row();
        scrollTableGames.add(startGame3).size(420, 100).padBottom(20).row();
        scrollTableGames.add(startGame4).size(420, 100).padBottom(20).row();
        scrollTableGames.setPosition(1000, 720 / 2 - scrollTableGames.getHeight() / 2);
//        final ScrollPane scroller = new ScrollPane(scrollTable);

//        scroller.setScale(0.1f);
//        scroller.setBounds(0, 0, 50, 450);
        final Table tableS = new Table();
        tableS.setFillParent(false);
        tableS.setTransform(true);
        tableS.setBounds(410, 100, 420, 100);
//        tableS.add(scroller);


        stage.addActor(tableS);
        stage.addActor(label);
        stage.addActor(scrollTableGames);
        stage.addActor(table);
        stage.addActor(setting);
        stage.addActor(settingLabel);

        choosingskins = new Choosingskins();
        resetName = new ResetName();
        selectLevel = new SelectLevel();
    }

    public void settings(Drawable drawable) {
        table.setBackground(drawable);
        if (show) {
            scrollTableGames.setVisible(true);
            table.clear();
            table.setBounds(1810, 0, 550, 720);
            table.setVisible(false);
            this.show = false;
            MoveByAction action = new MoveByAction();
            action.setAmount(350, 0);
            action.setDuration(0.2f);
            setting.addAction(action);

            RotateByAction rotate = new RotateByAction();
            rotate.setAmount(-200);
            rotate.setDuration(0.3f);
            setting.addAction(rotate);

            MoveByAction actionLabelSetting = new MoveByAction();
            actionLabelSetting.setAmount(350, 0);
            actionLabelSetting.setDuration(0.05f);
            settingLabel.addAction(actionLabelSetting);
        } else {
            scrollTableGames.setVisible(false);
            MoveByAction action = new MoveByAction();
            action.setAmount(-350, 0);
            action.setDuration(0.2f);
            setting.addAction(action);

            RotateToAction rotate = new RotateToAction();
            rotate.setRotation(200f);
            rotate.setDuration(0.3f);
            setting.addAction(rotate);

            MoveByAction actionLabelSetting = new MoveByAction();
            actionLabelSetting.setAmount(-350, 0);
            actionLabelSetting.setDuration(0.2f);
            settingLabel.addAction(actionLabelSetting);

            table.clear();
            table.setVisible(true);
            button = ImageTextButtonHandler.INSTANCE.createButtonWay("butS (1).png", "Имя", 35, Color.WHITE, true);
            button2 = ImageTextButtonHandler.INSTANCE.createButtonWay("butS (2).png", "Скин", 35, Color.WHITE, true);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    resetName.show();
                }
            });

            button2.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    choosingskins.show();
                }
            });

            table.add(button).size(400, 100).padBottom(30f).row();
            table.add(button2).size(400, 100).row();

            MoveByAction action2 = new MoveByAction();
            action2.setAmount(-1080, 0);
            action2.setDuration(0.2f);
            table.addAction(action2);
            this.show = true;

        }
    }

    public TextButton.TextButtonStyle setStyle(Skin skin, int size) {
        TextButton textButton = new TextButton("", skin);
        TextButton.TextButtonStyle style = textButton.getStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
        style.checked = textButton.getStyle().checked;
        style.up = textButton.getStyle().up;
        style.down = textButton.getStyle().down;

        return style;
    }

//    public ImageTextButton.ImageTextButtonStyle imageTextButtonStyle(String way, int size) {
//        Texture myTexture = new Texture(Gdx.files.internal(way));//buttonBer
//        TextureRegion myTextureRegion = new TextureRegion(myTexture);
//        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
//        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
//        style.up = myTexRegionDrawable;
//        style.down = myTexRegionDrawable;
//        style.checked = myTexRegionDrawable;
//        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
//        return style;
//    }

//    public ImageTextButton.ImageTextButtonStyle imageTextButtonStyle(Sprite texture, int size) {
//        TextureRegion myTextureRegion = new TextureRegion(texture);
//        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
//        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
//        style.up = myTexRegionDrawable;
//        style.down = myTexRegionDrawable;
//        style.checked = myTexRegionDrawable;
//        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
//        return style;
//    }


    public class Choosingskins {
        ImageTextButton buttonBack2;
        //        TextButton button2;
        Table resetNameTable2;


        public Choosingskins() {
            resetNameTable2 = new Table();
            buttonBack2 = ImageTextButtonHandler.INSTANCE.createButtonWay("back.png", "", 60, Color.WHITE, false);
            buttonBack2.setPosition(60, 70);
            buttonBack2.setSize(250, 100);
//            button2 = new TextButton("Сохранить", setStyle(skin, 60));
//            button2.setPosition(300, 70);

            stage.addActor(resetNameTable2);
            stage.addActor(buttonBack2);
//            stage.addActor(button2);


            buttonBack2.setVisible(false);
//            button2.setVisible(false);
            resetNameTable2.setVisible(false);

            resetNameTable2.setBounds(0, 0, 1280, 720);
            Texture tex = new Texture("bacT.png");
            TextureRegion t = new TextureRegion(tex);
            Drawable drawable = new TextureRegionDrawable(t);
            resetNameTable2.setBackground(drawable);

            buttonBack2.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
//                    button2.setVisible(false);
                }
            });
//            button2.addListener(new ChangeListener() {
//                @Override
//                public void changed(ChangeEvent event, Actor actor) {
//                    resetNameTable2.setVisible(false);
//                    buttonBack2.setVisible(false);
//                    button2.setVisible(false);
//
//                }
//            });
            m1.button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    showM1();

                }
            });

            m2.button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    showM2();
                }
            });

            m3.button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    showM3();
                }
            });
//            ********************************
            resetNameTable2.add(m1.button).size(150, 150).padRight(20);
            resetNameTable2.add(m2.button).size(150, 150).padRight(20);
            resetNameTable2.add(m3.button).size(150, 150).padRight(20);


            selectSkin = new SelectSkin();
        }

        public void showM1() {
            selectSkin.show(m1);
        }

        public void showM2() {
            selectSkin.show(m2);
        }

        public void showM3() {
            selectSkin.show(m3);
        }

        public void show() {
            scrollTableGames.setVisible(false);
            buttonBack2.setVisible(true);
//            button2.setVisible(true);
            resetNameTable2.setVisible(true);
//            buttonM1.setVisible(true);
//            buttonM2.setVisible(true);
//            buttonM3.setVisible(true);
            setting.setVisible(false);
            button.setVisible(false);
            button2.setVisible(false);
        }

        public void remove() {
            scrollTableGames.setVisible(true);
            buttonBack2.setVisible(false);
//            button2.setVisible(false);
            resetNameTable2.setVisible(false);
            setting.setVisible(true);
            button.setVisible(true);
            button2.setVisible(true);
//            buttonM1.setVisible(false);
//            buttonM2.setVisible(false);
//            buttonM3.setVisible(false);
        }

    }

    public class ResetName {
        Table resetNameTable;
        Group group;

        public ResetName() {
            resetNameTable = new Table();
            resetNameTable.setBounds(0, 0, 1280, 720);
            Texture tex = new Texture("bacT.png");
            TextureRegion t = new TextureRegion(tex);
            Drawable drawable = new TextureRegionDrawable(t);
            resetNameTable.setBackground(drawable);
            TextField textField = new TextField("Новое имя", skin);
            TextField.TextFieldStyle style = new TextField.TextFieldStyle();
            style = textField.getStyle();
            style.font = FontSizeHandler.INSTANCE.getFont(40, Color.WHITE);

            textField = new TextField("", style);
            textField.setMessageText("Новое имя");
            textField.setMaxLength(8);
            textField.setAlignment(Align.center);
            final TextField finalTextField = textField;
            ImageTextButton buttonSave = ImageTextButtonHandler.INSTANCE.createButtonWay("saveButton.png", "", 60, Color.WHITE, false);

            buttonSave.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    label.setText(finalTextField.getText());
                    namePlayer = finalTextField.getText();
                }
            });
            ImageTextButton buttonBack = ImageTextButtonHandler.INSTANCE.createButtonWay("back.png", "", 60, Color.WHITE, false);
            buttonBack.setPosition(60, 70);
            buttonBack.setSize(250, 100);
            buttonBack.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                }
            });
            resetNameTable.add(textField).align(Align.center).size(300, 60);
            resetNameTable.add(buttonSave).size(100, 100).row();
            group = new Group();
            group.addActor(resetNameTable);
            group.addActor(buttonBack);
            group.setVisible(false);
            stage.addActor(group);
        }

        public void show() {
            group.setVisible(true);
            scrollTableGames.setVisible(false);
            setting.setVisible(false);
            button.setVisible(false);
            button2.setVisible(false);
        }

        public void remove() {
            setting.setVisible(true);
            button.setVisible(true);
            button2.setVisible(true);
            scrollTableGames.setVisible(true);
            final AlphaAction alphaAction = new AlphaAction();
            alphaAction.setAlpha(0.5f);
            alphaAction.setDuration(0.1f);
            RunnableAction run = new RunnableAction();
            run.setRunnable(new Runnable() {
                @Override
                public void run() {
                    if (alphaAction.isComplete()) {
                        group.setVisible(false);
                        AlphaAction alphaAction = new AlphaAction();
                        alphaAction.setAlpha(1);
                        group.addAction(alphaAction);
                    }
                }
            });
            group.addAction(new SequenceAction(alphaAction, run));
        }

    }

    public class SelectLevel {
        Image image;
        Table table2;
        Label labelLevel;
        ImageTextButton closeLevel;

        public SelectLevel() {
            image = new Image(level);
            image.setSize(500, 700);
            image.setPosition(380, 0);
            scrollTable = new Table();
            final ScrollPane scroller = new ScrollPane(scrollTable);
//        scroller.setSmoothScrolling(true);
//        scroller.setTransform(true);
            scroller.setBounds(0, 0, 500, 500);

            table2 = new Table();
//        table.setFillParent(true);
            table2.add(scroller).fill().expand();
            table2.setBounds(380, 60, 500, 500);
            ImageTextButton startGame2 = ImageTextButtonHandler.INSTANCE.createButtonWay("buton2.png", "Классичекая", 40, Color.WHITE, false);
            startGame2.setPosition(54, 108);
            startGame2.setSize(300, 120);
            startGame2.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setScreen(сlassic_game_screen);
                }
            });
            scrollTable.add(startGame2).size(420, 100).padBottom(20);
            scrollTable.row();
            for (int i = 0; i < 5; i++) {
                ImageTextButton button = ImageTextButtonHandler.INSTANCE.createButtonWay("buton2.png", i + 1 + " уровень", 40, Color.WHITE, false);
                final int finalI = i;
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        image.setVisible(false);
                        image.setVisible(false);
                        table2.setVisible(false);
                        labelLevel.setVisible(false);
                        closeLevel.setVisible(false);
                        levels_game_screen.level = finalI;
                        game.setScreen(levels_game_screen);
                    }
                });
                scrollTable.add(button).size(420, 100).padBottom(20);
                scrollTable.row();
            }
            labelLevel = LabelHandler.INSTANCE.createLabel("Выберите уровень", 30, Color.BLACK);
            labelLevel.setPosition(485, 630);
            closeLevel = ImageTextButtonHandler.INSTANCE.createButtonWay("close.png", "", 15, Color.WHITE, false);
            closeLevel.setPosition(800, 626);
            closeLevel.setSize(50, 50);
            closeLevel.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    image.setVisible(false);
                    table2.setVisible(false);
                    labelLevel.setVisible(false);
                    closeLevel.setVisible(false);
                }
            });

            image.setVisible(false);
            table2.setVisible(false);
            labelLevel.setVisible(false);
            closeLevel.setVisible(false);
            stage.addActor(image);
            stage.addActor(table2);
            stage.addActor(labelLevel);
            stage.addActor(closeLevel);
        }

        public void show() {
            image.setVisible(true);
            table2.setVisible(true);
            labelLevel.setVisible(true);
            closeLevel.setVisible(true);
        }

        public void remove() {
            image.setVisible(false);
            table2.setVisible(false);
            labelLevel.setVisible(false);
            closeLevel.setVisible(false);
        }
    }

    public class SelectSkin {
        Image image;
        ImageTextButton closeLevel;
        ImageTextButton skins1G;
        ImageTextButton skins2G;
        ImageTextButton skins3G;
        TextButton textButton;
        ImageTextButton skins1S;
        ImageTextButton skins2S;
        ImageTextButton skins3S;
        ImageTextButton skins1B;
        ImageTextButton skins2B;
        ImageTextButton skins3B;

        Table tableScrollSkin;

        SnakeSkin b;

        public SelectSkin() {
            scrollTable2 = new Table();
            image = new Image(level);
            image.setSize(500, 700);
            image.setPosition(380, 0);
            closeLevel = ImageTextButtonHandler.INSTANCE.createButtonWay("close.png", "", 15, Color.WHITE, false);
            closeLevel.setPosition(800, 626);
            closeLevel.setSize(50, 50);
            closeLevel.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                }
            });
            image.setVisible(false);
            closeLevel.setVisible(false);
            stage.addActor(image);
            stage.addActor(closeLevel);
            ScrollPane scroller = new ScrollPane(scrollTable2);

            scroller.setBounds(0, 0, 500, 500);
            tableScrollSkin = new Table();

            tableScrollSkin.add(scroller).fill().expand();
            tableScrollSkin.setBounds(380, 180, 500, 300);

            skins1S = ImageTextButtonHandler.INSTANCE.createButtonSprite(Asset.instance().getSprite("snake_head"), "", 60, Color.WHITE);
            skins2S = ImageTextButtonHandler.INSTANCE.createButtonSprite(Asset.instance().getSprite("snake_body"), "", 60, Color.WHITE);
            skins3S = ImageTextButtonHandler.INSTANCE.createButtonSprite(Asset.instance().getSprite("snake_tail"), "", 60, Color.WHITE);
            skins1S.setSize(90, 90);
            skins1S.setPosition(720, 450);
            skins2S.setSize(90, 90);
            skins2S.setPosition(590, 450);
            skins3S.setSize(90, 90);
            skins3S.setPosition(460, 450);

            skins1G = ImageTextButtonHandler.INSTANCE.createButtonWay("snakehead.png", "", 60, Color.WHITE, false);
            skins2G = ImageTextButtonHandler.INSTANCE.createButtonWay("snakebody.png", "", 60, Color.WHITE, false);
            skins3G = ImageTextButtonHandler.INSTANCE.createButtonWay("snakebody.png", "", 60, Color.WHITE, false);
            skins1G.setSize(90, 90);
            skins1G.setPosition(720, 320);
            skins2G.setSize(90, 90);
            skins2G.setPosition(590, 320);
            skins3G.setSize(90, 90);
            skins3G.setPosition(460, 320);

            skins1B = ImageTextButtonHandler.INSTANCE.createButtonWay("headB.png", "", 60, Color.WHITE, false);
            skins2B = ImageTextButtonHandler.INSTANCE.createButtonWay("bodyB.png", "", 60, Color.WHITE, false);
            skins3B = ImageTextButtonHandler.INSTANCE.createButtonWay("bodyB.png", "", 60, Color.WHITE, false);


            textButton = new TextButton("Добавить свой", setStyle(skin, 40));
            textButton.setSize(350, 90);
            textButton.setPosition(460, 100);
            textButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.loaded.Photo();
                    photo = true;
                }
            });
            scrollTable2.add(skins3S).size(100, 100).padBottom(20).padRight(10);

            scrollTable2.add(skins2S).size(100, 100).padBottom(20).padRight(10);

            scrollTable2.add(skins1S).size(100, 100).padBottom(20).row();

            scrollTable2.add(skins3G).size(100, 100).padBottom(20).padRight(10);

            scrollTable2.add(skins2G).size(100, 100).padBottom(20).padRight(10);

            scrollTable2.add(skins1G).size(100, 100).padBottom(20).row();

            scrollTable2.add(skins3B).size(100, 100).padBottom(20).padRight(10);

            scrollTable2.add(skins2B).size(100, 100).padBottom(20).padRight(10);

            scrollTable2.add(skins1B).size(100, 100).padBottom(20).row();

            stage.addActor(tableScrollSkin);
            stage.addActor(textButton);

            textButton.setVisible(false);
            tableScrollSkin.setVisible(false);

//            skins.add(skins3S.getImage());
//            skins.add(skins2S.getImage());
//            skins.add(skins1S.getImage());
//            skins.add(skins3G.getImage());
//            skins.add(skins2G.getImage());
//            skins.add(skins1G.getImage());


            skins3S.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(ImageTextButtonHandler.INSTANCE.imageTextButtonStyle(Asset.instance().getSprite("snake_tail"), 60));
                    b.sprite = Asset.instance().getSprite("snake_tail");
                }
            });
            skins2S.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(ImageTextButtonHandler.INSTANCE.imageTextButtonStyle(Asset.instance().getSprite("snake_body"), 60));
                    b.sprite = Asset.instance().getSprite("snake_body");
                }
            });
            skins1S.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(ImageTextButtonHandler.INSTANCE.imageTextButtonStyle(Asset.instance().getSprite("snake_head"), 60));
                    b.sprite = Asset.instance().getSprite("snake_head");
                }
            });
            skins3G.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(ImageTextButtonHandler.INSTANCE.imageTextButtonStyle("snakebody.png", 60));
                    b.sprite = new Sprite(new Texture("snakebody.png"));
                }
            });
            skins2G.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(ImageTextButtonHandler.INSTANCE.imageTextButtonStyle("snakebody.png", 60));
                    b.sprite = new Sprite(new Texture("snakebody.png"));
                }
            });
            skins1G.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(ImageTextButtonHandler.INSTANCE.imageTextButtonStyle("snakehead.png", 60));
                    b.sprite = new Sprite(new Texture("snakehead.png"));
                }
            });
            skins3B.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(ImageTextButtonHandler.INSTANCE.imageTextButtonStyle("bodyB.png", 60));
                    b.sprite = new Sprite(new Texture("bodyB.png"));
                }
            });
            skins2B.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(ImageTextButtonHandler.INSTANCE.imageTextButtonStyle("bodyB.png", 60));
                    b.sprite = new Sprite(new Texture("bodyB.png"));
                }
            });
            skins1B.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(ImageTextButtonHandler.INSTANCE.imageTextButtonStyle("headB.png", 60));
                    b.sprite = new Sprite(new Texture("headB.png"));
                }
            });
        }

        public void show(SnakeSkin b) {
            this.b = b;
            image.setVisible(true);
            closeLevel.setVisible(true);
            textButton.setVisible(true);
            tableScrollSkin.setVisible(true);
        }

        public void remove() {
            image.setVisible(false);
            closeLevel.setVisible(false);
        }
    }

    public class BIndex {
        ImageTextButton button;
        int index;

        public BIndex(ImageTextButton button, int index) {
            this.button = button;
            this.index = index;
        }
    }

    public static class SnakeSkin {
        ImageTextButton button;
        Sprite sprite;

        public SnakeSkin(ImageTextButton button, Sprite sprite) {
            this.button = button;
            this.sprite = sprite;
        }
    }
}