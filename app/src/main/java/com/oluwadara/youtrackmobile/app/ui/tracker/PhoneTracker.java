package com.oluwadara.youtrackmobile.app.ui.tracker;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

public class PhoneTracker {

    private TelephonyManager telephonyManager;

    private GsmCellLocation cellLocation;


    public PhoneTracker(Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
    }

    public int getMnc() {
        String operator = telephonyManager.getNetworkOperator();
        return Integer.parseInt(operator.substring(3));
    }

    public int getMcc() {
        String operator = telephonyManager.getNetworkOperator();
        return Integer.parseInt(operator.substring(0, 3));
    }

    public int getCellId() {
        return cellLocation.getCid();
    }

    public int getLac() {
        return cellLocation.getLac();
    }
}
