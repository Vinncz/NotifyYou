package com.example.notifyyou.Utils.Permissions;

public interface OnPermissionResultListener {
    void onPermissionGranted (String _permission);
    void onPermissionDenied (String _permission);
}
