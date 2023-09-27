package com.example.notifyyou.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.notifyyou.Fragments.Home;
import com.example.notifyyou.Fragments.NewFragment;
import com.example.notifyyou.R;
import com.example.notifyyou.Utils.CONFIG;
import com.example.notifyyou.Utils.NotificationHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences(CONFIG.channelName, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();

//        spe.putString(CONFIG.sharedPreferenceUnpinnedTileItemsChannel, "");
//        spe.putString(CONFIG.sharedPreferencePinnedTileItemsChannel, "");
//
//        spe.commit();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentContainerView, new Home());
        transaction.commit();

        NotificationHelper nh = new NotificationHelper(
                                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE),
                                        MainActivity.this
                                    );

        BottomNavigationView bnv = findViewById(R.id.bottomNavigationView);
        bnv.setOnItemSelectedListener(
                item -> {
                    Fragment selectedFragment = null;
                    int itemId = item.getItemId();

                    if (itemId == R.id.home) {
                        selectedFragment = new Home();

                    } else if (itemId == R.id.newTileItem) {
                        selectedFragment = new NewFragment();

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
