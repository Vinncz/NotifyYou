package com.example.notifyyou.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;
import com.example.notifyyou.ViewModels.TileItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class TileItemAdapter extends RecyclerView.Adapter<TileItemAdapter.TileItemHolder> implements settableTileItemList {

    private List<TileItem> tileItemList = new ArrayList<TileItem>();
    private TileItemViewModel vm;

    /* Constructor */
    public TileItemAdapter (TileItemViewModel _vm) {
        this.vm = _vm;
    }

    @NonNull
    @Override
    public TileItemHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tile_item, parent, false);
        return new TileItemHolder(itemView);
    }
    @Override
    public void onBindViewHolder (@NonNull TileItemHolder holder, int position) {
        TileItem ti = tileItemList.get(position);

        holder.id.setText(ti.getId().toString());
        holder.isPinned.setText(ti.getIsPinned().toString());
        holder.title.setText(ti.getTitle());
        holder.body.setText(ti.getBody());

        if (ti.getIsPinned()) {
            holder.unpinToggle.setVisibility(View.VISIBLE);
            holder.pinToggle.setVisibility(View.GONE);

        } else {
            holder.pinToggle.setVisibility(View.VISIBLE);
            holder.unpinToggle.setVisibility(View.GONE);

        }

        holder.pinToggle.setOnClickListener(v -> {
            ti.setIsPinned(true);
            vm.update(ti);
            notifyDataSetChanged();
        });

        holder.unpinToggle.setOnClickListener(v -> {
            ti.setIsPinned(false);
            vm.update(ti);
            notifyDataSetChanged();
        });

        holder.deleteToggle.setOnClickListener(v -> {
            vm.delete(ti);
            notifyDataSetChanged();
        });
    }
    @Override
    public int getItemCount () {
        return tileItemList.size();
    }

    @Override
    public void setTileItemList (List<TileItem> _items) {
        this.tileItemList = _items;
        notifyDataSetChanged();
    }

    class TileItemHolder extends RecyclerView.ViewHolder {
        private final com.google.android.material.textview.MaterialTextView title, body, id, isPinned;
        private final com.google.android.material.button.MaterialButton pinToggle, unpinToggle, deleteToggle;

        public TileItemHolder (@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.title);
            title.setMaxLines(2);

            body = itemView.findViewById(R.id.body);
            body.setMaxLines(3);

            id = itemView.findViewById(R.id.id);

            isPinned = itemView.findViewById(R.id.isPinned);

            pinToggle = itemView.findViewById(R.id.pinTileItem);
            pinToggle.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "pin was pressed!", Toast.LENGTH_SHORT).show();
            });

            unpinToggle = itemView.findViewById(R.id.unpinTileItem);
            unpinToggle.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "unpin was pressed!", Toast.LENGTH_SHORT).show();
            });

            deleteToggle = itemView.findViewById(R.id.deleteTileItem);
            deleteToggle.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "delete was pressed!", Toast.LENGTH_SHORT).show();
            });

        }
    }
}
