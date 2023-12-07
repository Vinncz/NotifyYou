package com.example.notifyyou.Views.Fragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notifyyou.Adapters.PinnedTileItemsAdapter;
import com.example.notifyyou.Adapters.TileItemAdapter;
import com.example.notifyyou.Adapters.settableTileItemList;
import com.example.notifyyou.Controllers.NotificationController;
import com.example.notifyyou.Factories.NotificationFactory;
import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;
import com.example.notifyyou.Receiver.AlarmReceiver;
import com.example.notifyyou.ViewModels.TileItemViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class HomeFragment extends Fragment {

    private TileItemViewModel vm;
    public HomeFragment withViewModel (TileItemViewModel vm) {
        this.vm = vm;
        return this;
    }

    public HomeFragment () {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        itemView = v;

        attachRecyclerViewAdapter(true, new PinnedTileItemsAdapter(vm), v.findViewById(R.id.pinnedTileItems), new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));
        attachRecyclerViewAdapter(false, new TileItemAdapter(vm), v.findViewById(R.id.tileItems), new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false));

        return v;
    }

    private View itemView;
    private void attachRecyclerViewAdapter (Boolean _pinned, RecyclerView.Adapter _adapter, RecyclerView _target, RecyclerView.LayoutManager _layoutManager) {
        _target.setLayoutManager(_layoutManager);
        _target.setAdapter(_adapter);

        NotificationController nc = new NotificationController(NotificationManagerCompat.from(itemView.getContext()));

        Map<Integer, Notification> pinnedNotifications = new HashMap<>();




        /* Fetch all TileItems from database */
        LiveData<List<TileItem>> tileItemsLiveData = _pinned ? vm.getAllPinned() : vm.getAllUnpinned();




        tileItemsLiveData.observe(getViewLifecycleOwner(), new Observer<List<TileItem>>() {
            @SuppressLint("ScheduleExactAlarm")
            @Override
            public void onChanged(List<TileItem> newTileItems) {
                Set<Integer> newPinnedItems = new HashSet<>();

                for (TileItem ti : newTileItems) {
                    newPinnedItems.add(ti.getId());

                    if (ti.getIsPinned()) {
                        if (!pinnedNotifications.containsKey(ti.getId())) {
                            // Item was added to the set
                            Notification n = NotificationFactory.CreatePersistentNotificationForDefaultChannelId(itemView.getContext(), ti.getTitle(), ti.getBody());
                            nc.Notify(ti.getId(), n);
                            pinnedNotifications.put(ti.getId(), n);
                        }
                    } else {
                        if (pinnedNotifications.containsKey(ti.getId())) {
                            // Item was removed from the set
                            nc.Cancel(ti.getId());
                            pinnedNotifications.remove(ti.getId());
                        }
                    }
                }

                // Iterate over pinnedNotifications to cancel notifications for items not in the new data
                for (Integer itemId : new ArrayList<>(pinnedNotifications.keySet())) {
                    if (!newPinnedItems.contains(itemId)) {
                        nc.Cancel(itemId);
                        pinnedNotifications.remove(itemId);
                    }
                }

//                // Update the adapter's data with the new data
//                ((settableTileItemList) _adapter).setTileItemList(newTileItems);
//                 Update the adapter's data with the new data
                ((settableTileItemList) _adapter).setTileItemList(newTileItems);
            }
        });


        if (_pinned) {
            vm.getAllPinned().observe(getViewLifecycleOwner(), new Observer<List<TileItem>>() {
                @Override
                public void onChanged (List<TileItem> tileItems) {
                    ((settableTileItemList) _adapter).setTileItemList(tileItems);
                }
            });

        } else {
            vm.getAllUnpinned().observe(getViewLifecycleOwner(), new Observer<List<TileItem>>() {
                @Override
                public void onChanged (List<TileItem> tileItems) {
                    ((settableTileItemList) _adapter).setTileItemList(tileItems);
                }
            });

        }

    }

//    private void checkAlarm(TileItem ti){
//        // Set the alarm when the alarm is active.
//        if(ti.getAlarmIsActive()){
//            int hour = Integer.parseInt(ti.getSelectedTimeForAlarm().substring(0,2));
//            int minute = Integer.parseInt(ti.getSelectedTimeForAlarm().substring(3,5));
//            Calendar alarmTime = Calendar.getInstance();
//            alarmTime.set(Calendar.HOUR_OF_DAY, hour);
//            alarmTime.set(Calendar.MINUTE, minute);
//            alarmTime.set(Calendar.SECOND, 0);
//
//            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
//            Intent intent = new Intent(getContext(), AlarmReceiver.class);
//            intent.putExtra("id", ti.getId());
//            intent.putExtra("title", ti.getTitle());
//            intent.putExtra("body", ti.getBody());
//
//            if (alarmTime.before(Calendar.getInstance())){
//                alarmTime.add(Calendar.DATE, 1);
//            }
//
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), ti.getId(), intent, PendingIntent.FLAG_IMMUTABLE);
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
//        }else {
//            // Cancel the alarm when the alarm is off.
//            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
//            Intent intent = new Intent(getContext(), AlarmReceiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), ti.getId(), intent, PendingIntent.FLAG_IMMUTABLE);
//            alarmManager.cancel(pendingIntent);
//        }
//    }
}
