package com.example.share2dlibgdx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class JoystickArrows {
    Group group;
    int size, x, y;
    public ImageButton RIGHT, LEFT, UP, DOWN;

    public JoystickArrows(int size, int x, int y) {
        this.size = size;
        this.x = x;
        this.y = y;
        Texture myTexture = new Texture(Gdx.files.internal("arrowLeft.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        LEFT = new ImageButton(myTexRegionDrawable);
        LEFT.setPosition(150, 200);
        LEFT.setSize(200, 80);


        myTexture = new Texture(Gdx.files.internal("arrowRight.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        RIGHT = new ImageButton(myTexRegionDrawable);
        RIGHT.setPosition(350, 200);
        RIGHT.setSize(200, 80);


        myTexture = new Texture(Gdx.files.internal("arrowUp.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        UP = new ImageButton(myTexRegionDrawable);
        UP.setPosition(250, 300);
        UP.setSize(200, 80);


        myTexture = new Texture(Gdx.files.internal("arrowDown.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        DOWN = new ImageButton(myTexRegionDrawable);
        DOWN.setPosition(250, 95);
        DOWN.setSize(200, 80);
    }

    public Group joystick() {


        group = new Group();
        group.setPosition(-130, -50);

        group.addActor(RIGHT);
        group.addActor(LEFT);
        group.addActor(UP);
        group.addActor(DOWN);

        return group;
    }

//        if (Gdx.input.isKeyPressed(Input.Keys.UP) && tr != 3) {
//        tr = 0;
//    } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && tr != 2) {
//        tr = 1;
//    } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && tr != 1) {
//        tr = 2;
//    } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && tr != 0) {
//        tr = 3;
//    }
}
