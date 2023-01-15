package com.n3studio.share2dlibgdx;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
//import com.yandex.mobile.ads.common.InitializationListener;
//import com.yandex.mobile.ads.common.MobileAds;
//import com.yandex.mobile.ads.common.InitializationListener;
//import com.yandex.mobile.ads.common.MobileAds;

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
    DialogС dialogС;
    ArrayList<BluetoothDevice> devices;
    ArrayList<Bitmap> bitmaps;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_snake);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        bitmaps = new ArrayList<>();

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
            public void dialogC(String nameDevice) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialogС = new DialogС(context, nameDevice);
                        dialogС.show();
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

            @Override
            public boolean getClose() {
                return dialogС.close;
            }
        };

        bluetoothLoaded = new InterfaceBluetoothLoaded() {
            @Override
            public void BluetoothService() {
                bluetoothService = new BluetoothService();
                if (ActivityCompat.checkSelfPermission(AndroidLauncher.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                bluetoothService.bluetoothAdapter.startDiscovery();
                devices = new ArrayList<>();
            }

            @Override
            public ArrayList<String> getListDevice() {
                ArrayList<String> d = bluetoothService.getListDevice();
                d.add("Устройства рядом");
                for (int i = 0; i < devices.size(); i++) {
                    if (ActivityCompat.checkSelfPermission(AndroidLauncher.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                    }
                    d.add("-G=-" + devices.get(i).getName());
                    bluetoothService.btArray.add(devices.get(i));
                }
                return d;
            }

            @Override
            public void listen() {
                Permissions.enableDiscoveribility(context);
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
            public void sendImage(int i) {
                bluetoothService.sendImage(getBitmap(i));
                System.out.println("BITMAPBITMAPBITMAPBITMAPBITMAPBITMAPBITMAP");
                System.out.println(getBitmap(i).getHeight());
                System.out.println("BITMAPBITMAPBITMAPBITMAPBITMAPBITMAPBITMAP");
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
                if (ActivityCompat.checkSelfPermission(AndroidLauncher.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
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

            @Override
            public String getMyNameDevice() {
                if (ActivityCompat.checkSelfPermission(AndroidLauncher.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                return bluetoothService.bluetoothAdapter.getName();
            }

            @Override
            public String getStatus() {
                return bluetoothService.getStatus();
            }

            @Override
            public void Matgic() {
//                Permissions.verifyLocationPermissions(context);
            }
        };

        Permissions.verifyLocationPermissions(context);
//        MobileAds.initialize(this, new InitializationListener() {
//            @Override
//            public void onInitializationCompleted() {
////                Log.d(YANDEX_MOBILE_ADS_TAG, "SDK initialized");
//            }
//        });
        game game = new game(loaded, bluetoothLoaded);
        View viewgame = initializeForView(game);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout layout = findViewById(R.id.mainlayout);
        layout.addView(viewgame);
//        MobileAds.initialize(this, new InitializationListener() {
//            @Override
//            public void onInitializationCompleted() {
////                Log.d(YANDEX_MOBILE_ADS_TAG, "SDK initialized");
//            }
//        });
//        initialize(game, config);
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

        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            String d = data.getData().toString();
            Uri selectedImage = Uri.parse(d);
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmaps.add(bitmap);
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

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter f = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, f);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.add(device);
                //                handler.post(() -> Toast.makeText(context, device.getName(), Toast.LENGTH_LONG).show());
//                System.out.println("ACTION_FOUND");

            }
        }
    };

    public class DialogС {
        Dialog dialog;
        AlertDialog alert;
        AlertDialog.Builder alertDialog;
        String title;
        String textButtonP;
        String textButtonN;
        String nameDevice;
        boolean close = true;

        public DialogС(Context context, String num) {
            this.nameDevice = num;
            alertDialog = new AlertDialog.Builder(context);
//            View view = LayoutInflater.from(context).inflate(R.layout.dialog, (FrameLayout) findViewById(R.id.dialogC));
////            alertDialog.setView(view);
//            alert = alertDialog.create();
//            alert.setView(view);
//            alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog = new Dialog(context);
            View view2 = LayoutInflater.from(context).inflate(R.layout.dialog, (FrameLayout) findViewById(R.id.dialogC));
            ((TextView) view2.findViewById(R.id.text)).setText("Ваша задача собрать " + num);

            view2.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    close = false;
                    dialog.dismiss();
                }
            });


            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view2);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            close = true;
        }

        public void show() {
            dialog.show();
        }

    }

    public Bitmap getBitmap(int i) {
        try {
            return bitmaps.get(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void Magic() {
//        Texture texture = new Texture("загруженное (1).png");
//        if (!texture.getTextureData().isPrepared()) {
//            texture.getTextureData().prepare();
//        }
//        Pixmap pixmap = texture.getTextureData().consumePixmap();
//
//        ByteBuffer byteBuffer = pixmap.getPixels();
//        byte[] pixelDataByteArray = new byte[byteBuffer.remaining()];
////        Bitmap bitmap = BitmapFactory.decodeByteArray(pixelDataByteArray, 0, pixelDataByteArray.length);
//        System.out.println("GGGGGGGGGGGGGGGGGGGG");
//        System.out.println(TexturesClass.snakebody.getHeight());
//        System.out.println(pixelDataByteArray.length);
//
//        byteBuffer.get(pixelDataByteArray);
//        Texture fromByteArray = new Texture(new Pixmap(pixelDataByteArray, 0, pixelDataByteArray.length));
//        System.out.println(fromByteArray.getHeight());
//        Bitmap bmp=null;
//        try {
//            bmp = BitmapFactory.decodeStream((InputStream)new URL("https://get.wallhere.com/photo/road-clouds-river-mountains-asphalt-marking-1069905.jpg").getContent());
//        } catch (Exception e) {}
//        System.out.println(bmp.getHeight());
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//        bmp.recycle();
//
//        Bitmap bitmap= BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
//        System.out.println(bitmap.getHeight());
    }
}

