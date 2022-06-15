package datamanager;

import com.badlogic.gdx.graphics.Texture;
import com.example.share2dlibgdx.Snake;

public interface InterfaceDataLoaded {

    Player requestData(String nameGame, String namePlayer, Snake share);

    Player requestData2();

    void create();

    void put(String nameGame, String vector2);

    String checkStartPlay();

    String checkStartPlay2();

    int countPlayersGames(String nameGame);

    int countPlayersGames2();

    void dispose();

    void dispose2();

    boolean isOnline();

    void isOnline2(String nameGame, String namePlayer);

    void toast(String text);

    void delete(String nameGame);

    void isExistsGame(String nGame, String namePlayer2);

    boolean isExistsGame2();

    boolean isNamePlayer(String namePlayer);

    void putMeal(String nameGame, String namePlayer, String level, int appleX, int appleY);

    void setExistsGame(boolean existsGame);

    void Photo();

    Texture getPhoto();

    void setPhoto();

    void dialog(String title, String message, String textPositiveButton);

    void dialogC(String nameDevice);

    void save(String key, String value);

    String get(String key, String def);

    boolean getClose();
}
