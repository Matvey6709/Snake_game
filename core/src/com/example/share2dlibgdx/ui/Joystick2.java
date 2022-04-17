package com.example.share2dlibgdx.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Joystick2 {

    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;

    private int outerCircleRadius;
    private int innerCircleRadius;

    private boolean isPressed = false;
    private double joystickCenterToTouchDistance;
    private double actuatorX;
    private double actuatorY;

    Pixmap pixmap, pixmap2;
    Texture pix, pix2;
    SpriteBatch batch;

    Vector3 pos = new Vector3(0, 0, 0);

    Camera camera;

    public Joystick2(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius, SpriteBatch batch, Camera camera) {

        // центры кругов
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        // радиусы кругов
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;
        this.batch = batch;
        this.camera = camera;
        // кисти для кругов
        pixmap = new Pixmap(500, 500, Pixmap.Format.RGBA8888);//d
        pixmap.setColor(Color.BLUE);
        pixmap.fillCircle(outerCircleCenterPositionX, outerCircleCenterPositionY, outerCircleRadius);

        pix();


    }

    public void pix(){
        pixmap2 = new Pixmap(500, 500, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.BLACK);
        pixmap2.fillCircle(innerCircleCenterPositionX, innerCircleCenterPositionY, innerCircleRadius);

        pix = new Texture(pixmap);

        pix2 = new Texture(pixmap2);
    }


    public void draw() {
        // рисуем внешний круг
        batch.begin();
        batch.draw(pix, 0, 0);
        batch.draw(pix2, 0, 0);
        batch.end();

    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX * outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY * outerCircleRadius);
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        System.out.println(deltaDistance);

        if (deltaDistance < outerCircleRadius) {
            actuatorX = deltaX / outerCircleRadius;
            actuatorY = deltaY / outerCircleRadius;
        } else {
            actuatorX = deltaX / deltaDistance;
            actuatorY = deltaY / deltaDistance;
        }
        pix();
    }

    public boolean isPressed(double x, double y) {
//        pos.set((float) outerCircleCenterPositionX, (float) outerCircleCenterPositionY, 0);
//        joystickCenterToTouchDistance = Math.sqrt(
//                Math.pow(outerCircleCenterPositionX - pos.x, 2) +
//                        Math.pow(outerCircleCenterPositionY - pos.y, 2)
//        );
//        camera.unproject(pos);
//        return joystickCenterToTouchDistance < outerCircleRadius;
        if (x >= outerCircleCenterPositionX/4 && x <= outerCircleRadius*2 + outerCircleCenterPositionX/4) {
//            System.out.println("Y");
            if (y >= 0 && y < Gdx.graphics.getHeight()-(outerCircleRadius*2)) {
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }

    public void resetActuator() {
        actuatorX = 0;
        actuatorY = 0;
        pix();
    }
}
