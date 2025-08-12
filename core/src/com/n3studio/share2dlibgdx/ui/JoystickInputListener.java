package com.n3studio.share2dlibgdx.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class JoystickInputListener extends InputListener {

    private com.n3studio.share2dlibgdx.ui.Joystick3 joystick;

    public JoystickInputListener(Joystick3 joystick) {
        this.joystick = joystick;
    }

    public JoystickInputListener() {
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        joystick.setTouched();
        joystick.changeCursor(x, y);
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        joystick.setUnTouched();
    }

    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        joystick.changeCursor(x, y);
        if(joystick.isJoystickDown()){
            joystick.handelChangedListener();
        }
    }


}
