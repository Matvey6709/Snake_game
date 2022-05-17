package com.example.share2dlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import handler.LabelHandler;

public class Dialog extends com.badlogic.gdx.scenes.scene2d.ui.Dialog {

    public Dialog(String title, Skin skin) {
        super(title, skin);
    }

    public Dialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public Dialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    {
        text("Ваша задача:\nдостигнуть 20 левела", LabelHandler.INSTANCE.getStyleLabel("", 10, Color.WHITE));
        button("Ok");

    }

    @Override
    protected void result(Object object) {
        super.result(object);
    }
}

