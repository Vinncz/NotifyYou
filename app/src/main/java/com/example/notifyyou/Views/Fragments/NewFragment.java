package com.example.notifyyou.Views.Fragments;

import android.app.Notification;
import android.os.Bundle;

import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notifyyou.Controllers.NotificationController;
import com.example.notifyyou.Controllers.TileItemController;
import com.example.notifyyou.Factories.NotificationFactory;
import com.example.notifyyou.Factories.TileItemFactory;
import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;
import com.example.notifyyou.Repositories.TileItemRepositoryOLD;
import com.example.notifyyou.ViewModels.TileItemViewModel;

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


    private TileItemViewModel vm;
    public NewFragment withViewModel (AndroidViewModel vm) {
        this.vm = (TileItemViewModel) vm;
        return this;
    }


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new, container, false);

        Button b = v.findViewById(R.id.NotificationButton);
        EditText title = v.findViewById(R.id.CustomNotificationHead);
        EditText body = v.findViewById(R.id.CustomNotificationBody);

        b.setOnClickListener(view -> {
            String notificationTitle = title.getText().toString();
            String notificationBody = body.getText().toString();

            TileItemController tic = new TileItemController(v.getContext());
            Boolean isValid = tic.validate(notificationTitle, notificationBody);

            if (isValid) {
                TileItem ti = TileItemFactory.MakeOne(notificationTitle, notificationBody);
                vm.insert(ti);

            } else {
                Toast.makeText(getContext(), "Title and Body cannot be empty, and must have at least 3 characters!", Toast.LENGTH_SHORT).show();

            }

        });

        return v;
    }
}
