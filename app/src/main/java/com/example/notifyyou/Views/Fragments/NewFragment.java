package com.example.notifyyou.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.notifyyou.Factories.TileItemFactory;
import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;
import com.example.notifyyou.ViewModels.TileItemViewModel;

public class NewFragment extends Fragment {

    private TileItemViewModel vm;
    public NewFragment withViewModel (AndroidViewModel vm) {
        this.vm = (TileItemViewModel) vm;
        return this;
    }

    public NewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new, container, false);

        Button b = v.findViewById(R.id.NotificationButton);
        EditText title = v.findViewById(R.id.CustomNotificationHead);
        EditText body = v.findViewById(R.id.CustomNotificationBody);
        com.google.android.material.materialswitch.MaterialSwitch useAlarm = v.findViewById(R.id.useAlarm);
        TimePicker timePicker = v.findViewById(R.id.alarmValue);

        timePicker.setVisibility(View.GONE);
        useAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (timePicker.getVisibility() != View.VISIBLE) {
                timePicker.setVisibility(View.VISIBLE);

            } else if (timePicker.getVisibility() != View.GONE) {
                timePicker.setVisibility(View.GONE);
            }
        });

        b.setOnClickListener(view -> {
            String notificationTitle = title.getText().toString();
            String notificationBody = body.getText().toString();

//            TileItemController tic = new TileItemController(v.getContext());
//            Boolean isValid = tic.validate(notificationTitle, notificationBody);

//            if (isValid) {
                TileItem ti = TileItemFactory.MakeOne(notificationTitle, notificationBody);
                vm.insert(ti);
                title.setText("");
                body.setText("");

//            } else {
//                Toast.makeText(getContext(), "Title and Body cannot be empty, and must have at least 3 characters!", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Successfully created TileItem!", Toast.LENGTH_SHORT).show();

//            }

        });

        return v;
    }
}
