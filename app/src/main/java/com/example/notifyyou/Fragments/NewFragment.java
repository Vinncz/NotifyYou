package com.example.notifyyou.Fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notifyyou.Factories.NotificationFactory;
import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;
import com.example.notifyyou.Repositories.TileItemRepository;
import com.example.notifyyou.Utils.NotificationHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static final String channelId = "NotifyYouNotificationChannelId";
    static int notificationId = 0;
    static final String channelName = "NotifyYouNotificationChannelName";


    public NewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewFragment newInstance(String param1, String param2) {
        NewFragment fragment = new NewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new, container, false);
        NotificationHelper nh = new NotificationHelper((NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE));
        nh.CreateNotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);

        Button b = v.findViewById(R.id.NotificationButton);
        EditText title = v.findViewById(R.id.CustomNotificationHead);
        EditText body = v.findViewById(R.id.CustomNotificationBody);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notificationTitle = title.getText().toString();
                String notificationBody = body.getText().toString();

                TileItem ti = new TileItem(notificationTitle, notificationBody);
                TileItemRepository.Post(ti);

                Notification n = NotificationFactory.CreatePresistentNotification(v.getContext(), channelId, notificationTitle, notificationBody);

                NotificationManagerCompat nm = NotificationManagerCompat.from(v.getContext());

                if (ActivityCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(v.getContext(), "You did not enable notification permission for this app", Toast.LENGTH_LONG).show();
                    return;
                }

                nm.notify(notificationId++, n);
            }
        });
        return v;
    }
}
