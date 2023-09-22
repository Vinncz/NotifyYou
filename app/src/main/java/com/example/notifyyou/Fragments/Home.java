package com.example.notifyyou.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notifyyou.Adapters.PinnedTileItemsAdapter;
import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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

        ArrayList<TileItem> items = new ArrayList<TileItem>();
        items.add(new TileItem("Bawa konci inggris", "jangan lupa"));
        items.add(new TileItem("Contoh kalimat yang panjang banget dah dih duh deh doh sa si su se so 1 2 3 4 5 6 7 8 9", "iya ini buat ngetes aja apa yang terjadi kalo teksnya panjanggg banget nget nget"));
        items.add(new TileItem("Bawa regal", "jangan lupa"));
        items.add(new TileItem("Bawa regal", "jangan lupa"));

        PinnedTileItemsAdapter ptia = new PinnedTileItemsAdapter(items);
        RecyclerView pinnedTileItems = (RecyclerView) v.findViewById(R.id.pinnedTileItems);

        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
        pinnedTileItems.setLayoutManager(layoutManager);
        pinnedTileItems.setPadding(8, 0, 8, 0);

        pinnedTileItems.setAdapter(ptia);

        return v;
    }
}
