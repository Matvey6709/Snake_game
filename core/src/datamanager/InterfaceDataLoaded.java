package datamanager;

import com.badlogic.gdx.graphics.Texture;
import com.example.share2dlibgdx.Snake;

public interface InterfaceDataLoaded {

    Player requestData(String nameGame, String namePlayer, Snake share);

    Player requestData2();

    void create();

    void put(String nameGame, String namePlayer, String vector2, String level, int appleX, int appleY);

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

    void putNamePlayer(String namePlayer);

    void setExistsGame(boolean existsGame);

    void Photo();

    Texture getPhoto();

    void setPhoto();
}
