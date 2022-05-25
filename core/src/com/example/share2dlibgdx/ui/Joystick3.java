package com.example.share2dlibgdx.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.example.share2dlibgdx.DeterminantSize;

import java.util.ArrayList;
import java.util.List;

public class Joystick3 extends Actor {

    private Texture circle;
    private Texture curJoystick;
    private boolean isJoystickDown = false;
    DeterminantSize size = new DeterminantSize();
    private float rad = 160;
    private float inverseRad = 0;
    int CUR_RADIUS = 40;

    public float curX = 0;
    public float curY = 0;

    public float valueX = 0;
    public float valueY = 0;

    public float getCurX(){
        return this.getX() + rad - CUR_RADIUS;
    }

    public float getCurY() {
        return this.getY() + rad - CUR_RADIUS;
    }
    public float getCurXD(){
        return this.getX() + rad - CUR_RADIUS + curX;
    }

    public float getCurYD() {
        return this.getY() + rad - CUR_RADIUS + curY;
    }



    public float getValueX() {
        return valueX;
    }

    public float getValueY() {
        return valueY;
    }

    public boolean isJoystickDown() {
        return isJoystickDown;
    }

    private List<JoystickChangedListener> listeners = new ArrayList<JoystickChangedListener>();

    public void addJoystickChangedListener(JoystickChangedListener listener) {
        listeners.add(listener);
    }

    public void removeJoystickChangedListener(JoystickChangedListener listener) {
        listeners.remove(listener);
    }

    public void clearJoystickChangedListener() {
        listeners.clear();
    }

    public void handelChangedListener() {
        for (JoystickChangedListener listener : listeners) {
            listener.changed(valueX, valueY);
        }
    }

    public Joystick3(Texture circle, Texture curJoystick) {
        this.circle = circle;
        this.curJoystick = curJoystick;
//        setDefaultWH();
        setDefaultXY();
        setHeight(size.getHeightGame(250));
        setWidth(size.getWidthGame(250));
        addListener(new JoystickInputListener(this));
    }

    public void resetCur() {
        curX = 0;
        curY = 0;
    }

    public void setTouched() {
        isJoystickDown = true;
    }

    public void setUnTouched() {
        isJoystickDown = false;
    }

    public void setDefaultWH() {
        setWidth(160);
        setHeight(160);
        rad = 80;
        inverseRad = 1 / rad;
    }

    public void setWidth(float w) {
        super.setWidth(w);
        super.setHeight(w);
        rad = w / 2;
        inverseRad = 1 / rad;
    }

    public void setHeight(float h) {
        super.setWidth(h);
        super.setHeight(h);
        rad = h / 2;
        inverseRad = 1 / rad;
    }

    public void setDefaultXY() {
        setY( 60);
        setX( 60);

    }

    public void changeCursor(float x, float y) {

        float dx = x - rad;
        float dy = y - rad;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length < rad) {
            this.curX = dx;
            this.curY = dy;
        } else {
            float k = rad / length;
            this.curX = dx * k;
            this.curY = dy * k;
        }
        valueX = curX * inverseRad;
        valueY = curY * inverseRad;
//        System.out.println(curX);
//        System.out.println(curY);


    }

    public Actor hit(float x, float y, boolean touchable) {
        Actor actor = super.hit(x, y, touchable);
        if (actor == null) return null;
        else {
            float dx = x - rad;
            float dy = y - rad;
            return (dx * dx + dy * dy <= rad * rad) ? this : null;
        }

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(circle, this.getX(), this.getY(), this.getWidth(), this.getHeight());
//        batch.setColor(1, 0, 0, 1);
        if (isJoystickDown) {
            batch.draw(curJoystick, this.getX() + rad - CUR_RADIUS + curX, this.getY() + rad - CUR_RADIUS + curY, 2 * CUR_RADIUS, 2 * CUR_RADIUS);
        } else {
            batch.draw(curJoystick, this.getX() + rad - CUR_RADIUS, this.getY() + rad - CUR_RADIUS, 2 * CUR_RADIUS, 2 * CUR_RADIUS);
        }
//        batch.setColor(1, 1, 0, 1);
    }

    public void dispose(){
        circle.dispose();
        curJoystick.dispose();
    }
}
