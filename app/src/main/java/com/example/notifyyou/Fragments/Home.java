package com.example.notifyyou.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notifyyou.Adapters.PinnedTileItemsAdapter;
import com.example.notifyyou.Adapters.TileItemsAdapter;
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

        ArrayList<TileItem> items = new ArrayList<>();
        items.add(new TileItem("Do chores", "Wash dishes, do laundry, take trash out"));
        items.add(new TileItem("Defrost tomorrow's lunch", "Don't forget this!"));
        items.add(new TileItem("Follow up with SSC", "Request an internship letter"));
        items.add(new TileItem("Do assignments", "AOL Framework Layer Architecture, AOL Operating System, AOL Mobile Programming, Make a prototype for Entrepreneurship Market Validation, Final Project for OOAD LAB"));

        /* --------------------------------------------------------------------------- */

        PinnedTileItemsAdapter ptia = new PinnedTileItemsAdapter(items);
        RecyclerView pinnedTileItems = v.findViewById(R.id.pinnedTileItems);

        LinearLayoutManager ptiaLayoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
        pinnedTileItems.setLayoutManager(ptiaLayoutManager);
        pinnedTileItems.setPadding(0, 8, 0, 8);

        pinnedTileItems.setAdapter(ptia);

        /* --------------------------------------------------------------------------- */

        TileItemsAdapter tia = new TileItemsAdapter(items);
        RecyclerView tileItems = v.findViewById(R.id.tileItems);

        LinearLayoutManager tiaLayoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        tileItems.setLayoutManager(tiaLayoutManager);
        tileItems.setPadding(0, 16, 0, 0);

        tileItems.setAdapter(tia);

        /* --------------------------------------------------------------------------- */

        return v;
    }
}
