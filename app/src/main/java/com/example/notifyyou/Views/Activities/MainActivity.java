package com.example.notifyyou.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.Utils.Permissions.PermissionManager;
import com.example.notifyyou.ViewModels.TileItemViewModel;
import com.example.notifyyou.Views.Fragments.HomeFragment;
import com.example.notifyyou.Views.Fragments.NewFragment;
import com.example.notifyyou.R;
import com.example.notifyyou.Utils.CONFIG;
import com.example.notifyyou.Utils.Permissions.NotificationHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private PermissionManager permissionManager;
    private TileItemViewModel vw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vw = new ViewModelProvider(this).get(TileItemViewModel.class);

        FragmentManager manager = getSupportFragmentManager();
        displayInitialFragment(manager, new HomeFragment().withViewModel(vw));

        NotificationHelper nh = new NotificationHelper(
                                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE),
                                        this
                                    );

        handleBottomNavInitialization(manager);

    }

    private static void displayInitialFragment (FragmentManager manager, Fragment _f) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentContainerView, _f);
        transaction.commit();
    }

    private void handleBottomNavInitialization (FragmentManager manager) {
        BottomNavigationView bnv = findViewById(R.id.bottomNavigationView);
        bnv.setOnItemSelectedListener(
                item -> {
                    Fragment selectedFragment = null;
                    int itemId = item.getItemId();

                    if (itemId == R.id.home) {
                        selectedFragment = new HomeFragment().withViewModel(vw);

                    } else if (itemId == R.id.newTileItem) {
                        selectedFragment = new NewFragment().withViewModel(vw);

                    }

                    if (selectedFragment != null) {
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.replace(R.id.fragmentContainerView, selectedFragment);
                        transaction1.commit();
                    }

                    return true;
                }
        );
    }
}
