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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
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


    public Lobby(final game gam) {
        game = gam;
        level = new Texture("options.png");
        t = new Texture("bac2.jpg");
        backgroundTexture = new TextureRegion(t, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCamera();
        fitViewport = new FitViewport(1280, 720, camera);

        stage = new Stage(fitViewport);
        stage.getViewport().setCamera(camera);
        imageTextButtons = new ArrayList<>();
        skins = new ArrayList();
        buttonM3 = new ImageTextButton("", imageTextButtonStyle(Asset.instance().getSprite("snake_head"), 60));
        buttonM2 = new ImageTextButton("", imageTextButtonStyle(Asset.instance().getSprite("snake_body"), 60));
        buttonM1 = new ImageTextButton("", imageTextButtonStyle(Asset.instance().getSprite("snake_tail"), 60));
        m3 = new SnakeSkin(buttonM3, Asset.instance().getSprite("snake_head"));
        m2 = new SnakeSkin(buttonM2, Asset.instance().getSprite("snake_body"));
        m1 = new SnakeSkin(buttonM1, Asset.instance().getSprite("snake_tail"));
        Screen();
//        TestG g = new TestG();
//        stage.addActor(g);
        Gdx.input.setInputProcessor(stage);
        game.loaded.create();

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
//        if(Gdx.input.isTouched()){
//            RotateToAction rotate = new RotateToAction();
//            rotate.setRotation(90f);
//            rotate.setDuration(0.2f);
//            setting.addAction(rotate);
//        }

        game.batch.end();

        if (photo && game.loaded.getPhoto() != null) {
            photo = false;
            Texture s = game.loaded.getPhoto();
            final BIndex index = new BIndex(new ImageTextButton("", imageTextButtonStyle(new Sprite(s), 60)), l);
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
                    System.out.println(l);

                    selectSkin.b.button.setStyle(imageTextButtonStyle(new Sprite(skins.get(index.index)), 60));
                    selectSkin.b.sprite = new Sprite(skins.get(index.index));
                }
            });
            l++;
            game.loaded.setPhoto();
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        game.batch.begin();
        game.batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
//        game.loaded.setExistsGame(true);
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
    }

    public void Screen() {
        table = new Table();
        table.setBounds(1760, 0, 400, 720);
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("bac.jpg")));
        skin = new Skin(Gdx.files.internal("uiskin.json"));

//        font = new BitmapFont();
        setting = new ImageTextButton("", imageTextButtonStyle("setting.png", 40));
        setting.setTransform(true);
        setting.setOrigin(setting.getWidth(), setting.getHeight());
        setting.setPosition(1090, 576);
        setting.setSize(100, 100);
//        table.add(setting);
        setting.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings(drawable);
            }
        });
        settingLabel = LabelHandler.INSTANCE.createLabel("Настройки", 40, Color.BLACK);
        settingLabel.setPosition(1280, 600);

        label = LabelHandler.INSTANCE.createLabel(namePlayer, 87, Color.BLACK);
        label.setX(140);
        label.setY(600);

        TextButton startGame1 = new TextButton("Начать игру 1", setStyle(skin, 40));
        startGame1.setPosition(454, 108);
        startGame1.setSize(300, 120);

        startGame1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.loaded.isOnline()) {
//                    if (namePlayer.equals("Default")) {
//                    String namePlayerU = new Random().nextInt(900) + 100 + "" + System.currentTimeMillis();
//                    }
                    game.setScreen(new Leaderboard(game, new Random().nextInt(900) + 100 + "" + System.currentTimeMillis(), namePlayer));
                } else {
                    game.loaded.toast("Включите интернет");
                }
            }
        });

        TextButton startGame2 = new TextButton("Начать игру 2", setStyle(skin, 40));
        startGame2.setPosition(54, 108);
        startGame2.setSize(300, 120);
        startGame2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Game2_Screen(game));
            }
        });

        TextButton startGame3 = new TextButton("Начать игру 3", setStyle(skin, 40));
        startGame3.setPosition(854, 108);
        startGame3.setSize(300, 120);
        startGame3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectLevel.show();
            }
        });

//        stage.addActor(dialog);


        final Table scrollTable = new Table();
        scrollTable.add(startGame1).size(420, 100);
        scrollTable.add(startGame2).size(420, 100).padLeft(20);
        scrollTable.add(startGame3).size(420, 100).padLeft(20);
        final ScrollPane scroller = new ScrollPane(scrollTable);

//        scroller.setScale(0.1f);
//        scroller.setBounds(0, 0, 50, 450);
        final Table tableS = new Table();
        tableS.setFillParent(false);
        tableS.setTransform(true);
        tableS.setBounds(410, 100, 420, 100);
        tableS.add(scroller);


        stage.addActor(tableS);
        stage.addActor(label);
        stage.addActor(table);
        stage.addActor(setting);
        stage.addActor(settingLabel);

        choosingskins = new Choosingskins();
        resetName = new ResetName();
        selectLevel = new SelectLevel();


    }

    public void settings(Drawable drawable) {
        table.setBackground(drawable);
        if (show == true) {
            table.clear();
            table.setBounds(1760, 0, 400, 720);
            table.setVisible(false);
            this.show = false;
            MoveByAction action = new MoveByAction();
            action.setAmount(200, 0);
            action.setDuration(0.2f);
            setting.addAction(action);

            RotateByAction rotate = new RotateByAction();
            rotate.setAmount(-360);
            rotate.setDuration(0.2f);
            setting.addAction(rotate);

            MoveByAction actionLabelSetting = new MoveByAction();
            actionLabelSetting.setAmount(250, 0);
            actionLabelSetting.setDuration(0.05f);
            settingLabel.addAction(actionLabelSetting);
        } else {
            MoveByAction action = new MoveByAction();
            action.setAmount(-200, 0);
            action.setDuration(0.2f);
            setting.addAction(action);

            RotateToAction rotate = new RotateToAction();
            rotate.setRotation(360f);
            rotate.setDuration(0.2f);
            setting.addAction(rotate);

            MoveByAction actionLabelSetting = new MoveByAction();
            actionLabelSetting.setAmount(-250, 0);
            actionLabelSetting.setDuration(0.2f);
            settingLabel.addAction(actionLabelSetting);

            table.clear();
            table.setVisible(true);
            TextButton button = new TextButton("Поменять имя", setStyle(skin, 35));
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    resetName.show();
                }
            });

            TextButton button2 = new TextButton("Поменять скин", setStyle(skin, 30));
            button2.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    choosingskins.show();
                }
            });

            table.add(button).size(260, 80).padBottom(20f).row();
            table.add(button2).size(260, 80).row();

            MoveByAction action2 = new MoveByAction();
            action2.setAmount(-880, 0);
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

    public ImageTextButton.ImageTextButtonStyle imageTextButtonStyle(String way, int size) {
        Texture myTexture = new Texture(Gdx.files.internal(way));//buttonBer
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = myTexRegionDrawable;
        style.down = myTexRegionDrawable;
        style.checked = myTexRegionDrawable;
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
        return style;
    }

    public ImageTextButton.ImageTextButtonStyle imageTextButtonStyle(Sprite texture, int size) {
        TextureRegion myTextureRegion = new TextureRegion(texture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = myTexRegionDrawable;
        style.down = myTexRegionDrawable;
        style.checked = myTexRegionDrawable;
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
        return style;
    }


    public class Choosingskins {
        TextButton buttonBack2;
        //        TextButton button2;
        Table resetNameTable2;


        public Choosingskins() {
            resetNameTable2 = new Table();
            buttonBack2 = new TextButton("назад", setStyle(skin, 60));
            buttonBack2.setPosition(60, 70);
//            button2 = new TextButton("Сохранить", setStyle(skin, 60));
//            button2.setPosition(300, 70);

            stage.addActor(resetNameTable2);
            stage.addActor(buttonBack2);
//            stage.addActor(button2);


            buttonBack2.setVisible(false);
//            button2.setVisible(false);
            resetNameTable2.setVisible(false);

            resetNameTable2.setBounds(0, 0, 1280, 720);
            Texture tex = new Texture("resetname.jpg");
            TextureRegion t = new TextureRegion(tex);
            Drawable drawable = new TextureRegionDrawable(t);
            resetNameTable2.setBackground(drawable);

            buttonBack2.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    resetNameTable2.setVisible(false);
                    buttonBack2.setVisible(false);
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
            buttonBack2.setVisible(true);
//            button2.setVisible(true);
            resetNameTable2.setVisible(true);
//            buttonM1.setVisible(true);
//            buttonM2.setVisible(true);
//            buttonM3.setVisible(true);
        }

        public void remove() {
            buttonBack2.setVisible(false);
//            button2.setVisible(false);
            resetNameTable2.setVisible(false);
//            buttonM1.setVisible(false);
//            buttonM2.setVisible(false);
//            buttonM3.setVisible(false);
        }

    }

    public class ResetName {
        Table resetNameTable;

        public ResetName() {
            resetNameTable = new Table();
            resetNameTable.setBounds(0, 0, 1280, 720);
            Texture tex = new Texture("resetname.jpg");
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

            TextButton button = new TextButton("Сохранить", setStyle(skin, 30));
            final TextField finalTextField = textField;
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    label.setText(finalTextField.getText());
                    namePlayer = finalTextField.getText();
                }
            });
            TextButton buttonBack = new TextButton("назад", setStyle(skin, 30));
            final TextField finalTextField1 = textField;
            buttonBack.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                }
            });
            resetNameTable.add(textField).size(300, 65).padBottom(50f).row();
            resetNameTable.add(button).padBottom(50f).row();
            resetNameTable.add(buttonBack).row();
            resetNameTable.setVisible(false);
            stage.addActor(resetNameTable);
        }

        public void show() {
            resetNameTable.setVisible(true);
        }

        public void remove() {
//            resetNameTable.setVisible(false);
            final AlphaAction alphaAction = new AlphaAction();
            alphaAction.setAlpha(0.5f);
            alphaAction.setDuration(0.1f);
//            resetNameTable.addAction(alphaAction);
            RunnableAction run = new RunnableAction();
            run.setRunnable(new Runnable() {
                @Override
                public void run() {
                    if (alphaAction.isComplete()) {
                        resetNameTable.setVisible(false);
                        AlphaAction alphaAction = new AlphaAction();
                        alphaAction.setAlpha(1);
                        resetNameTable.addAction(alphaAction);
                    }

                }
            });
            resetNameTable.addAction(new SequenceAction(alphaAction, run));
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

            for (int i = 0; i < 5; i++) {
                TextButton button = new TextButton(i + 1 + " уровень", setStyle(skin, 40));
                final int finalI = i;
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        image.setVisible(false);
                        image.setVisible(false);
                        table2.setVisible(false);
                        labelLevel.setVisible(false);
                        closeLevel.setVisible(false);
                        game.setScreen(new Game3_Screen(game, finalI));
                    }
                });
                scrollTable.add(button).size(420, 100).padBottom(20);
                scrollTable.row();
            }
            labelLevel = LabelHandler.INSTANCE.createLabel("Выберите уровень", 30, Color.BLACK);
            labelLevel.setPosition(485, 630);
            closeLevel = new ImageTextButton("", imageTextButtonStyle("close.png", 15));
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

        Table tableScrollSkin;

        SnakeSkin b;

        public SelectSkin() {
            scrollTable2 = new Table();
            image = new Image(level);
            image.setSize(500, 700);
            image.setPosition(380, 0);
            closeLevel = new ImageTextButton("", imageTextButtonStyle("close.png", 15));
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

            skins1S = new ImageTextButton("", imageTextButtonStyle(Asset.instance().getSprite("snake_head"), 60));
            skins2S = new ImageTextButton("", imageTextButtonStyle(Asset.instance().getSprite("snake_body"), 60));
            skins3S = new ImageTextButton("", imageTextButtonStyle(Asset.instance().getSprite("snake_tail"), 60));
            skins1S.setSize(90, 90);
            skins1S.setPosition(720, 450);
            skins2S.setSize(90, 90);
            skins2S.setPosition(590, 450);
            skins3S.setSize(90, 90);
            skins3S.setPosition(460, 450);

            skins1G = new ImageTextButton("", imageTextButtonStyle("snakehead.png", 60));
            skins2G = new ImageTextButton("", imageTextButtonStyle("snakebody.png", 60));
            skins3G = new ImageTextButton("", imageTextButtonStyle("snakebody.png", 60));
            skins1G.setSize(90, 90);
            skins1G.setPosition(720, 320);
            skins2G.setSize(90, 90);
            skins2G.setPosition(590, 320);
            skins3G.setSize(90, 90);
            skins3G.setPosition(460, 320);
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
                    b.button.setStyle(imageTextButtonStyle(Asset.instance().getSprite("snake_tail"), 60));
                    b.sprite = Asset.instance().getSprite("snake_tail");
                }
            });
            skins2S.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(imageTextButtonStyle(Asset.instance().getSprite("snake_body"), 60));
                    b.sprite = Asset.instance().getSprite("snake_body");
                }
            });
            skins1S.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(imageTextButtonStyle(Asset.instance().getSprite("snake_head"), 60));
                    b.sprite = Asset.instance().getSprite("snake_head");
                }
            });
            skins3G.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(imageTextButtonStyle("snakebody.png", 60));
                    b.sprite = new Sprite(new Texture("snakebody.png"));
                }
            });
            skins2G.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(imageTextButtonStyle("snakebody.png", 60));
                    b.sprite = new Sprite(new Texture("snakebody.png"));
                }
            });
            skins1G.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    remove();
                    textButton.setVisible(false);
                    tableScrollSkin.setVisible(false);
                    b.button.setStyle(imageTextButtonStyle("snakehead.png", 60));
                    b.sprite = new Sprite(new Texture("snakehead.png"));
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
