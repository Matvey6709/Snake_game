package com.example.share2dlibgdx;

public class Cell {
    public float x;
    public float y;
    public float sizeX;
    public float sizeY;


    //    public Texture texture;
    public float rotate;


    public Cell(float x, float y, float sizeX, float sizeY) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
//        texture = new Texture("badlogic.jpg");
    }

    public Cell(float x, float y, float sizeX, float sizeY, int rotate) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
//        texture = new Texture("badlogic.jpg");
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRotate() {
        return rotate;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
    }

}
