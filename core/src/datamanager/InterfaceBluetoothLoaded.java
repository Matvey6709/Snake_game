package datamanager;

import java.util.ArrayList;

public interface InterfaceBluetoothLoaded {

    void BluetoothService();

    ArrayList<String> getListDevice();

    void listen();

    void itemB(int i);

    void send(String msg);

    void sendImage(int i);

    String getMs();

    boolean getS();

    void stopBl();

    void enableBl();

    boolean isEnabled();

    boolean enable();

    void stopT();

    void restartGame();

    String getMyNameDevice();

    String getStatus();

    void Matgic();
}
