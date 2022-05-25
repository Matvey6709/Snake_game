package com.example.share2dlibgdx;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import datamanager.InterfaceBluetoothLoaded;
import datamanager.InterfaceDataLoaded;
import datamanager.Player;

public class AndroidLauncher extends AndroidApplication {

    FireBaseDataBase base;
    InterfaceDataLoaded loaded;
    final AndroidLauncher context = this;
    public static final int GET_FROM_GALLERY = 3;
    public static final int ENABLE_REQUEST = 1;
    Texture tex;
    InterfaceBluetoothLoaded bluetoothLoaded;
    BluetoothService bluetoothService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        loaded = new InterfaceDataLoaded() {
            @Override
            public void create() {
                base = new FireBaseDataBase();
                base.Create();
            }

            @Override
            public Player requestData(String nameGame, String namePlayer, Snake share) {
                return base.requestData(nameGame);
            }

            @Override
            public Player requestData2() {
                return base.requestData2();
            }


            @Override
            public void put(String nameGame, String vector2) {
                base.putData(nameGame, vector2);
            }

            @Override
            public String checkStartPlay() {
                return base.checkStartPlay();
            }

            @Override
            public String checkStartPlay2() {
                return base.checkStartPlay2();
            }

            @Override
            public int countPlayersGames(String nameGame) {
                return base.countPlayersGames(nameGame);
            }

            @Override
            public int countPlayersGames2() {
                return base.countPlayersGames2();
            }

            @Override
            public void dispose() {
                base.dispose();
            }

            @Override
            public void dispose2() {
                base.dispose2();
            }

            @Override
            public boolean isOnline() {
                return isNetworkConnected();
            }

            @Override
            public void isOnline2(String nameGame, String namePlayer) {
                base.isOnline2(nameGame);
            }

            @Override
            public void toast(String text) {
                handler.post(() -> Toast.makeText(context, text, Toast.LENGTH_LONG).show());
            }

            @Override
            public void delete(String nameGame) {
                base.delete(nameGame);
            }

            @Override
            public void isExistsGame(String nGame, String namePlayer2) {
                base.isExistsGame(nGame);
            }

            @Override
            public boolean isExistsGame2() {
                return base.isExistsGame2();
            }

            @Override
            public boolean isNamePlayer(String namePlayer) {
                return false;
            }

            @Override
            public void putMeal(String nameGame, String namePlayer, String level, int appleX, int appleY) {
                base.putData(nameGame, namePlayer, level, appleX, appleY);
            }

            @Override
            public void setExistsGame(boolean existsGame) {
                base.setExistsGame(existsGame);
            }

            @Override
            public void Photo() {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }

            @Override
            public Texture getPhoto() {
                return tex;
            }

            @Override
            public void setPhoto() {
                tex = null;
            }

            @Override
            public void dialog(String title, String message, String textPositiveButton) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(context)
                                .setTitle(title)
                                .setMessage(message)
                                .setPositiveButton(textPositiveButton, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_email)
                                .show();
                    }
                });

            }

            @Override
            public void save(String key, String value) {
                editor.putString(key, value);
                editor.apply();
            }

            @Override
            public String get(String key, String def) {
                return sharedPreferences.getString(key, def);
            }


        };

        bluetoothLoaded = new InterfaceBluetoothLoaded() {
            @Override
            public void BluetoothService() {
                bluetoothService = new BluetoothService();
            }

            @Override
            public ArrayList<String> getListDevice() {
                return bluetoothService.getListDevice();
            }

            @Override
            public void listen() {
                bluetoothService.listen();
            }

            @Override
            public void itemB(int i) {
                bluetoothService.itemB(i);
            }

            @Override
            public void send(String msg) {
                bluetoothService.send(msg);
            }

            @Override
            public String getMs() {
                return bluetoothService.getMs();
            }

            @Override
            public boolean getS() {
                return bluetoothService.getS();
            }

            @Override
            public void stopBl() {
                bluetoothService.stopBl();
            }

            @Override
            public void enableBl() {
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), ENABLE_REQUEST);
            }

            @Override
            public boolean isEnabled() {
                return bluetoothService.isEnabled();
            }

            @Override
            public boolean enable() {
                return enable;
            }

            @Override
            public void stopT() {
                bluetoothService.stopT();
            }

            @Override
            public void restartGame() {
                Intent mStartActivity = new Intent(context, AndroidLauncher.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                mgr.set(AlarmManager.RTC, 0, mPendingIntent);
                try {
                    mPendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        };

        initialize(new game(loaded, bluetoothLoaded), config);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }

    Bitmap bitmap;
    boolean enable = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            String d = data.getData().toString();
            Uri selectedImage = Uri.parse(d);
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (bitmap.getWidth() > 1600 || bitmap.getHeight() > 1600) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 1600,
                        1600, false);
            }
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    tex = new Texture(bitmap.getWidth(), bitmap.getHeight(), Pixmap.Format.RGBA8888);
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex.getTextureObjectHandle());
                    GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
                    bitmap.recycle();
                }
            });

        }
        if (requestCode == ENABLE_REQUEST && resultCode == Activity.RESULT_OK) {
            enable = true;
        }
    }

//        Toast.makeText(this, "Чтобы избежать ошибок, придумайте разные имена игроков", Toast.LENGTH_LONG).show();
//        btAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (btAdapter == null) {
//            new AlertDialog.Builder(this)
//                    .setTitle("Not compatible")
//                    .setMessage("Your phone does not support Bluetooth")
//                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
////                            System.exit(0);
//                        }
//                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        }


//        deviceItemList = new ArrayList<DeviceItem>();
//
//        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
//        System.out.println(pairedDevices.size());
//        if (pairedDevices.size() > 0) {
//            for (BluetoothDevice device : pairedDevices) {
//                DeviceItem newDevice= new DeviceItem(device.getName(), device.getAddress(),"false");
//                deviceItemList.add(newDevice);
//
//            }
//        }
//        System.out.println(deviceItemList.get(1).getDeviceName());
//        device = btAdapter.getRemoteDevice(deviceItemList.get(1).getAddress());
//        try {
//            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
//            Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
//            tmp = (BluetoothSocket) m.invoke(device, 1);
//        } catch (IOException e) {
////            Log.e(TAG, "create() failed", e);
//            System.out.println("НЕЕЕЕЕЕТ");
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        mmSocket = tmp;
//        System.out.println(mmSocket.isConnected());
}

