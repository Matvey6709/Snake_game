package handler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TextButtonHandler {

    public static TextButtonHandler INSTANCE = new TextButtonHandler();

    public TextButton createTextButton(final String text, final int size, final Color color, Skin skin) {
        TextButton end = new TextButton("", skin);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, Color.WHITE);
        style.checked = end.getStyle().checked;
        style.up = end.getStyle().up;
        style.down = end.getStyle().down;
        return new TextButton(text, style);
    }
}
