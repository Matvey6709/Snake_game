package com.n3studio.share2dlibgdx;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;


public class Permissions {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION_ID = 10;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void verifyLocationPermissions(Activity activity) {
        //Enable Location
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            //Enable Location
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivity(intent);
        }

        //Ask Permissions for Location
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION_ID);
        }
    }

    public static void enableDiscoveribility(Activity activity) {
        Intent discoverableIntent = new Intent(
                BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(
                BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 15);
        activity.startActivity(discoverableIntent);
    }
}