package datamanager;

public class Player {
    String str;
    int level;
    String name;
    int applesX;
    int applesY;

    public int getApplesX() {
        return applesX;
    }

    public void setApplesX(int applesX) {
        this.applesX = applesX;
    }

    public int getApplesY() {
        return applesY;
    }

    public void setApplesY(int applesY) {
        this.applesY = applesY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Player() {

    }

    public String getStr() {
        return str;
    }


    public Player(String str) {
        this.str = str;
    }
}
