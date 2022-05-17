package com.example.share2dlibgdx.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.example.share2dlibgdx.Snake;

public class ActivitySwipeDetector extends InputListener {

    static final String logTag = "ActivitySwipeDetector";
    static final int MIN_DISTANCE = 50;
    private float downX, downY, upX, upY;
    Snake snake;

    public ActivitySwipeDetector(Snake snake) {
        this.snake = snake;
    }

    public void onRightSwipe() {
        System.out.println("onRightSwipe");
        if (snake.transfer.tr != 2) {
            snake.transfer.tr = 1;
        }
    }

    public void onLeftSwipe() {
        System.out.println("onLeftSwipe");
        if (snake.transfer.tr != 1) {
            snake.transfer.tr = 2;
        }
    }

    public void onDownSwipe() {
        System.out.println("onDownSwipe");
        if (snake.transfer.tr != 0) {
            snake.transfer.tr = 3;
        }
    }

    public void onUpSwipe() {
        System.out.println("onUpSwipe");
        if (snake.transfer.tr != 3) {
            snake.transfer.tr = 0;
        }
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        downX = x;
        downY = y;
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//        upX = x;
//        upY = y;
//        float deltaX = downX - upX;
//        float deltaY = downY - upY;
//
//        // swipe horizontal?
//        if (Math.abs(deltaX) > Math.abs(deltaY)) {
//            if (Math.abs(deltaX) > MIN_DISTANCE) {
//                // left or right
//                if (deltaX > 0) {
//                    this.onLeftSwipe();
//                }
//                if (deltaX < 0) {
//                    this.onRightSwipe();
//                }
//            } else {
//            }
//        }
//        // swipe vertical?
//        else {
//            if (Math.abs(deltaY) > MIN_DISTANCE) {
//                // top or down
//                if (deltaY < 0) {
//                    this.onUpSwipe();
//                }
//                if (deltaY > 0) {
//                    this.onDownSwipe();
//                }
//            } else {
//            }
//        }
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        upX = x;
        upY = y;
        float deltaX = downX - upX;
        float deltaY = downY - upY;

        // swipe horizontal?
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (Math.abs(deltaX) > MIN_DISTANCE) {
                // left or right
                if (deltaX > 0) {
                    this.onLeftSwipe();
                    downX = x;
                    downY = y;
                }
                if (deltaX < 0) {
                    this.onRightSwipe();
                    downX = x;
                    downY = y;
                }
            } else {
            }
        }
        // swipe vertical?
        else {
            if (Math.abs(deltaY) > MIN_DISTANCE) {
                // top or down
                if (deltaY < 0) {
                    this.onUpSwipe();
                    downX = x;
                    downY = y;
                }
                if (deltaY > 0) {
                    this.onDownSwipe();
                    downX = x;
                    downY = y;
                }
            } else {
            }
        }
    }
}