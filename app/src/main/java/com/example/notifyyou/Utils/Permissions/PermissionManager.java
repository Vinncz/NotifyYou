package com.example.notifyyou.Utils.Permissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {
    public final static int PERMISSION_REQUEST_CODE = 1;

    private Context context;
    private OnPermissionResultListener listener;

    public PermissionManager (Context _c, OnPermissionResultListener _listener) {
        this.context = _c;
        this.listener = _listener;
    }

    public void requestPermission (String _permission) {
        if ( ContextCompat.checkSelfPermission(context, _permission) == PackageManager.PERMISSION_GRANTED ) {
            listener.onPermissionGranted(_permission);

        } else {
            ActivityCompat.requestPermissions(
                    ( Activity ) context,
                    new String[] {_permission},
                    PERMISSION_REQUEST_CODE
            );
        }
    }

    public void onRequestPermissionResult (int _requestCode, String [] _permissions, int [] _grantResults) {
        if (_requestCode == PERMISSION_REQUEST_CODE) {
            if (_grantResults.length > 0 && _grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                listener.onPermissionGranted(_permissions[0]);

            } else {
                listener.onPermissionDenied(_permissions[0]);

            }
        }
    }

}
