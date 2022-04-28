package datamanager;

import com.example.share2dlibgdx.Snake;

public interface InterfaceDataLoaded {

    public abstract Player requestData(String nameGame, String namePlayer, Snake share);

    public abstract Player requestData2();

    public abstract void create();

    public abstract void put(String nameGame, String namePlayer, String vector2, String level, int appleX, int appleY);

    public abstract String checkStartPlay();

    public abstract String checkStartPlay2();

    public abstract int countPlayersGames(String nameGame);

    public abstract int countPlayersGames2();

    public String getFirstPlayer(String nameGame);

    public void dispose();

    public void dispose2();

}
