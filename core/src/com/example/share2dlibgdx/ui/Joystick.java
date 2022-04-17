package com.example.share2dlibgdx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;


public class Joystick extends InputListener {
    Vector3 pos;
    Camera camera;
    Texture stick;
    Texture pole;
    SpriteBatch batch;

    public void update() {
        if (Gdx.input.isTouched()) {
            isBound(Gdx.input.getX(), Gdx.input.getY());
            if (!isBound(Gdx.input.getX(), Gdx.input.getY())) {
                pos(Gdx.input.getX(), Gdx.input.getY());
            }
        } else {
            ret();
        }
    }

    public void render() {
        batch.draw(pole, 0, 0, 400, 400);
        batch.draw(stick, pos.x, pos.y, 90, 90);
    }

    public Joystick(Camera camera, SpriteBatch batch) {
        this.camera = camera;
        this.batch = batch;
        pos = new Vector3(155, 155, 0);
//        pos = new Vector3(400, 500, 0);
        pole = new Texture("pole3.png");
        stick = new Texture("pole3.png");
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

        super.touchUp(event, x, y, pointer, button);
    }

    public boolean isBound(float x, float y) {
        if (x > 0 && x < pole.getWidth() / 2) {
            if (y > 0 && y < pole.getHeight()) {
            } else {
                pos(Gdx.input.getX(), Gdx.input.getY());
                return true;
            }
        }
        return false;
    }

    public void pos(float x, float y) {
        int rad = 100;
        double deltaX = x - rad;
        double deltaY = y - rad;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if(deltaDistance < rad) {
            pos.x = (float) deltaX/rad;
            pos.y = (float) deltaY/rad;
        } else {
            pos.x = (float) (deltaX/deltaDistance);
            pos.y = (float) ((float) deltaY/deltaDistance);
        }
        camera.unproject(pos);
//        float length = (float) Math.sqrt(((x - pos.x) * (x - pos.x) + (y - pos.y) * (y - pos.y)));
////        System.out.println(length);
//        int rad = 200;
//        float dx = x - rad;
//        float dy = y - rad;
//        System.out.println(dy);
//        if(pos.x <= 400 && pos.y <= 400){
//            pos.set(x, y, 0);
//        }else {
//            float k = rad/length;
//            pos.set(dx*k, dy*k, 0);
//
//        }
//        camera.unproject(pos);
//        int rad = 10;
//        float dx = x - rad;
//        float dy = y - rad;
//        float length = (float) Math.sqrt(dx*dx+dy*dy);
//        if(length < rad){
//
//        }else {
//            float k = rad/length;
//            pos.set(dx*k, dy*k, 0);
//        }



    }

    public void ret() {
        pos.set(155, 155, 0);

    }
}
