package com.example.notifyyou.Views.Fragments;

import android.app.Notification;
import android.os.Bundle;

import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notifyyou.Adapters.PinnedTileItemsAdapter;
import com.example.notifyyou.Adapters.TileItemAdapter;
import com.example.notifyyou.Adapters.TileItemsAdapterOLD;
import com.example.notifyyou.Adapters.settableTileItemList;
import com.example.notifyyou.Controllers.NotificationController;
import com.example.notifyyou.Factories.NotificationFactory;
import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;
import com.example.notifyyou.Repositories.TileItemRepositoryOLD;
import com.example.notifyyou.ViewModels.TileItemViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private TileItemViewModel vm;
    public HomeFragment withViewModel (TileItemViewModel vm) {
        this.vm = vm;
        return this;
    }



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        LiveData<List<TileItem>> tileItemsLiveData = _pinned ? vm.getAllPinned() : vm.getAllUnpinned();
        tileItemsLiveData.observe(this, new Observer<List<TileItem>>() {
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
//
//                // Update the adapter's data with the new data
//                ((settableTileItemList) _adapter).setTileItemList(newTileItems);
            }
        });


        if (_pinned) {
            vm.getAllPinned().observe(this, new Observer<List<TileItem>>() {
                @Override
                public void onChanged (List<TileItem> tileItems) {
                    ((settableTileItemList) _adapter).setTileItemList(tileItems);
                }
            });

        } else {
            vm.getAllUnpinned().observe(this, new Observer<List<TileItem>>() {
                @Override
                public void onChanged (List<TileItem> tileItems) {
                    ((settableTileItemList) _adapter).setTileItemList(tileItems);
                }
            });

        }

    }
}
