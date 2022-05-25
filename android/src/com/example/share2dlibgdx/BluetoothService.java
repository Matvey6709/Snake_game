package com.example.share2dlibgdx;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BluetoothService {
    BluetoothAdapter bluetoothAdapter;

    SendReceive sendReceive;

    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;

    int REQUEST_ENABLE_BLUETOOTH = 1;

    private static final String APP_NAME = "BTChat";
    private static final UUID MY_UUID = UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a66");

    BluetoothDevice[] btArray;

    public BluetoothService() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public ArrayList<String> getListDevice() {

        Set<BluetoothDevice> bt = bluetoothAdapter.getBondedDevices();
        ArrayList<String> strings = new ArrayList<>();
        btArray = new BluetoothDevice[bt.size()];
        int index = 0;

        if (bt.size() > 0) {
            for (BluetoothDevice device : bt) {
                btArray[index] = device;
                strings.add(device.getName());
                index++;
            }
        }
        return strings;
    }

    ServerClass serverClass;

    public void listen() {
        serverClass = new ServerClass();
        serverClass.start();
    }

    ClientClass clientClass;

    public void itemB(int i) {
        try {
            clientClass = new ClientClass(btArray[i]);
            clientClass.start();
        } catch (Exception e) {

        }

    }

    public void send(String msg) {
        String string = String.valueOf(msg);
        sendReceive.write(string.getBytes());
    }

    private class ServerClass extends Thread {
        private BluetoothServerSocket serverSocket;

        public ServerClass() {
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            if (!isInterrupted()) {
                BluetoothSocket socket = null;
                System.out.println("ServerClass");
                while (socket == null) {
                    try {
                        Message message = Message.obtain();
                        message.what = STATE_CONNECTING;
//                    handler.sendMessage(message);
                        System.out.println("CONNECTING");

                        socket = serverSocket.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Message message = Message.obtain();
                        message.what = STATE_CONNECTION_FAILED;
                        System.out.println("FAILED");
                        status = STATE_CONNECTION_FAILED;
//                    handler.sendMessage(message);
                    }

                    if (socket != null) {
                        Message message = Message.obtain();
                        message.what = STATE_CONNECTED;
                        System.out.println("CONNECTED");
                        status = STATE_CONNECTED;
//                    handler.sendMessage(message);

                        sendReceive = new SendReceive(socket);
                        sendReceive.start();

                        break;
                    }
                }
            }

        }

    }

    private class ClientClass extends Thread {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass(BluetoothDevice device1) {
            device = device1;

            try {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                System.out.println(device.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            if (!isInterrupted()) {
                try {
                    System.out.println("ClientClass");
                    socket.connect();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
//                handler.sendMessage(message);
                    status = STATE_CONNECTED;
                    System.out.println("CONNECTED");

                    sendReceive = new SendReceive(socket);
                    sendReceive.start();

                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    System.out.println("FAILED");
                    status = STATE_CONNECTION_FAILED;
//                handler.sendMessage(message);
                }
            }
        }
    }

    private class SendReceive extends Thread {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket) {
            bluetoothSocket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;

            try {
                tempIn = bluetoothSocket.getInputStream();
                tempOut = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream = tempIn;
            outputStream = tempOut;
        }

        public void run() {
            if (!isInterrupted()) {
                byte[] buffer = new byte[1024];
                int bytes;

                while (true) {
                    try {
                        bytes = inputStream.read(buffer);
//                    handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();
//                    System.out.println("SEND");
                        ms = new String(buffer, 0, bytes);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void write(byte[] bytes) {
            if (!isInterrupted()) {
                try {
                    outputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    String ms = "";
    int status;

    public boolean getS() {
        if (status == 3) {
            return true;
        }
        return false;
    }

    public String getMs() {
        return ms;
    }

    public void stopBl() {
        try {
            bluetoothAdapter.disable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    public void stopT() {
        try {
            serverClass.interrupt();
            clientClass.interrupt();
            sendReceive.interrupt();
        } catch (Exception e) {

        }
    }
}
