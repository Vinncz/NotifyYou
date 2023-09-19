package com.example.notifyyou.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notifyyou.Factories.NotificationFactory;
import com.example.notifyyou.R;
import com.example.notifyyou.Utils.NotificationHelper;

public class MainActivity extends AppCompatActivity {

    static final String channelId = "NotifyYouNotificationChannelId";
    static int notificationId = 0;
    static final String channelName = "KONTOL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationHelper nh = new NotificationHelper((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
        nh.CreateNotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);

        Button b = findViewById(R.id.NotificationButton);
        EditText title = findViewById(R.id.CustomNotificationHead);
        EditText body = findViewById(R.id.CustomNotificationBody);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification n = NotificationFactory.CreatePresistentNotification(MainActivity.this, channelId, title.getText().toString(), body.getText().toString());

                NotificationManagerCompat nm = NotificationManagerCompat.from(MainActivity.this);

                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(MainActivity.this, "You did not enable notification permission for this app", Toast.LENGTH_LONG).show();
                    return;
                }

                nm.notify(notificationId++, n);
            }
        });

//        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
//
//        ArrayList<TileItem> til = new ArrayList<>();
//        til.add(new TileItem("name1", "desc1"));
//        til.add(new TileItem("name2", "desc2"));
//        til.add(new TileItem("name3", "desc3"));
//        til.add(new TileItem("name4", "desc4"));
//
//        Adapter a = new Adapter(til);
//        recyclerView.setAdapter(a);
    }
}
