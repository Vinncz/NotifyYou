package com.example.notifyyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final String channelId = "999";
    final int notificationId = 999;
    final String channelName = "KONTOL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateNotificationChannel();

        Button b = findViewById(R.id.NotificationButton);
        EditText title = findViewById(R.id.CustomNotificationHead);
        EditText body = findViewById(R.id.CustomNotificationBody);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification n = new NotificationCompat.Builder(MainActivity.this, channelId)
                        .setOngoing(true)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title.getText())
                        .setContentText(body.getText())
                        .build();

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
                nm.notify(notificationId, n);
            }
        });
    }

    public void CreateNotificationChannel () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }
}
