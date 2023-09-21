package com.example.notifyyou.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.notifyyou.Fragments.Home;
import com.example.notifyyou.Fragments.NewFragment;
import com.example.notifyyou.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentContainerView, new Home());
        transaction.commit();

        BottomNavigationView bnv = findViewById(R.id.bottomNavigationView);
        bnv.setOnItemSelectedListener(
                new BottomNavigationView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        Fragment selectedFragment = null;
                        int itemId = item.getItemId();

                        if (itemId == R.id.home) {
                            selectedFragment = new Home();

                        } else if (itemId == R.id.newTileItem) {
                            selectedFragment = new NewFragment();
                            SharedPreferences sp = getSharedPreferences("", MODE_PRIVATE);
                        }

                        if (selectedFragment != null) {
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.fragmentContainerView, selectedFragment);
                            transaction.commit();
                        }
                        return true;
                    }
                });

    }
}
