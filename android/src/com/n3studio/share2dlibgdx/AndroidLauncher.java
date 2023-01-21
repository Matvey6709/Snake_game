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
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;
import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdEventListener;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;

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
    BannerAdView mBannerAdView;
    ProgressBar timer;
    private InterstitialAd mInterstitialAd;

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

            @Override
            public void showevent_(String str) {
                YandexMetrica.reportEvent(str, str);

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
                try{
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
                }catch (Exception e){}

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

            @Override
            public void checkBluetoothforAndroid12() {
                checkBluetoothforAndroid12_();
            }
        };

        Permissions.verifyLocationPermissions(context);
        game game = new game(loaded, bluetoothLoaded);
        View viewgame = initializeForView(game);
        baner(viewgame);//or    initialize(game, config);
//        videoAdd();

        YandexMetricaConfig config2 = YandexMetricaConfig.newConfigBuilder("26ca94c9-cf64-441e-b1f6-a3264dbec52a").build();
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config2);
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(getApplication());
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

    int count = 0;

    public void TimerForAdd_() {
        new CountDownTimer(25000, 1000) {

            public void onTick(long millisUntilFinished) {
                System.out.println(millisUntilFinished);
                if(millisUntilFinished < 25000){
                    timer.setProgress(count);
                    count += 5;
                }
            }

            public void onFinish() {
                timer.setVisibility(View.GONE);
                count = 0;
                mBannerAdView.destroy();
            }

        }.start();
    }

    LinearLayout layout;
    LinearLayout layout2;

    public void baner(View viewgame) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout = findViewById(R.id.mainlayout);
        layout2 = findViewById(R.id.seconmainlayout);
        MobileAds.initialize(this, new InitializationListener() {
            @Override
            public void onInitializationCompleted() {
                Log.d("YANDEX_MOBILE_ADS_TAG", "SDK initialized");
            }
        });
        mBannerAdView = new BannerAdView(this);
        mBannerAdView.setAdUnitId("R-M-2151067-1");
        mBannerAdView.setAdSize(AdSize.stickySize(500));
        final AdRequest adRequest = new AdRequest.Builder().build();
        layout2.addView(mBannerAdView);
        layout.addView(viewgame);
        mBannerAdView.setBannerAdEventListener(new BannerAdEventListener() {
            @Override
            public void onAdLoaded() {
                TimerForAdd_();
                System.out.println("Работает6");
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                System.out.println("Работает5");
                timer.setVisibility(View.GONE);

            }

            @Override
            public void onAdClicked() {
                System.out.println("Работает4");
            }

            @Override
            public void onLeftApplication() {
                System.out.println("Работает3");
            }

            @Override
            public void onReturnedToApplication() {
                System.out.println("Работает2");
            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {
                System.out.println("Работает1");
            }
        });
        mBannerAdView.loadAd(adRequest);

        timer = findViewById(R.id.progressBar);
//        TimerForAdd_();

    }

    public void videoAdd() {
        mInterstitialAd = new InterstitialAd(AndroidLauncher.this);
        mInterstitialAd.setAdUnitId("demo-interstitial-yandex");
// Создание объекта таргетирования рекламы.
        final AdRequest adRequest = new AdRequest.Builder().build();

        // Регистрация слушателя для отслеживания событий, происходящих в рекламе.
        mInterstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(AdRequestError adRequestError) {
                Toast.makeText(AndroidLauncher.this, "Ошибка",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });

        // Загрузка объявления.
        mInterstitialAd.loadAd(adRequest);
    }

    boolean showadd = false;
    public void showvideoAdd() {
            showadd = true;
        System.out.println("szdfggggggggggggggggggggg " + showadd);
    }

    public void checkBluetoothforAndroid12_(){
        if (ContextCompat.checkSelfPermission(AndroidLauncher.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(AndroidLauncher.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }
    }



}

