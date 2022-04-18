package datamanager;

import com.example.share2dlibgdx.Snake;

public interface InterfaceDataLoaded {

    public abstract Player requestData(String nameGame, String namePlayer, Snake share);

    public abstract void create();

    public abstract void put(String nameGame, String namePlayer, String vector2, String level);

    public abstract String checkStartPlay();

    public abstract int countPlayersGames(String nameGame);

    public String getFirstPlayer(String nameGame);

}
