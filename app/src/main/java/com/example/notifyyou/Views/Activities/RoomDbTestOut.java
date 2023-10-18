package com.example.notifyyou.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.example.notifyyou.Adapters.TileItemAdapter;
import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;
import com.example.notifyyou.Utils.Permissions.NotificationHelper;
import com.example.notifyyou.Utils.Permissions.OnPermissionResultListener;
import com.example.notifyyou.Utils.Permissions.PermissionManager;
import com.example.notifyyou.ViewModels.TileItemViewModel;

import java.util.List;

public class RoomDbTestOut extends AppCompatActivity {

    private PermissionManager permissionManager;
    private TileItemViewModel vw;

    private OnPermissionResultListener myPermissionListener = new OnPermissionResultListener() {
        @Override
        public void onPermissionGranted(String permission) {
            // Handle permission granted
        }

        @Override
        public void onPermissionDenied(String permission) {
            // Handle permission denied
        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_db_test_out);

        vw = new ViewModelProvider(this).get(TileItemViewModel.class);

        permissionManager = new PermissionManager(RoomDbTestOut.this, myPermissionListener);
        permissionManager.requestPermission(Manifest.permission.POST_NOTIFICATIONS);
        requestNotificationPolicyAccess();

        RecyclerView rv = findViewById(R.id.fragmentHolder);
        rv.setLayoutManager(new LinearLayoutManager(this));

        TileItemAdapter adapter = new TileItemAdapter(vw);
        rv.setAdapter(adapter);

        vw.getAll().observe(this, tileItems -> adapter.setTileItemList(tileItems));

    }


    public void requestNotificationPolicyAccess() {
        NotificationHelper.requestNotificationPolicyAccess(RoomDbTestOut.this.getBaseContext(), myPermissionListener);
    }
}
