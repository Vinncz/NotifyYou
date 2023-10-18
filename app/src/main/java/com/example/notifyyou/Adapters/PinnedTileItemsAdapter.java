package com.example.notifyyou.Adapters;

import android.app.Notification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notifyyou.Controllers.NotificationController;
import com.example.notifyyou.Factories.NotificationFactory;
import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;
import com.example.notifyyou.ViewModels.TileItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class PinnedTileItemsAdapter extends RecyclerView.Adapter<PinnedTileItemsAdapter.TileItemHolder> implements settableTileItemList {

    private List<TileItem> tileItemList = new ArrayList<TileItem>();
    private TileItemViewModel vm;

    /* Constructor */
    public PinnedTileItemsAdapter (TileItemViewModel _vm) {
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
        if (tileItemList.isEmpty()) {
            holder.bindEmptyState();

        } else {
            holder.bindData(tileItemList.get(position));
        }

    }
    @Override
    public int getItemCount () {
        return tileItemList.isEmpty() ? 1 : this.tileItemList.size();
    }

    @Override
    public void setTileItemList (List<TileItem> _items) {
        this.tileItemList.clear();

        this.tileItemList = _items;
        notifyDataSetChanged();
    }

    class TileItemHolder extends RecyclerView.ViewHolder {
        private final com.google.android.material.textview.MaterialTextView title, body, id, isPinned;
        private final com.google.android.material.button.MaterialButton pinToggle, unpinToggle, deleteToggle;

        public TileItemHolder bindData (TileItem _ti) {
            title.setMaxLines(2);

            body.setMaxLines(3);

            if (_ti.getIsPinned()) {
                unpinToggle.setVisibility(View.VISIBLE);
                pinToggle.setVisibility(View.GONE);

            } else {
                pinToggle.setVisibility(View.VISIBLE);
                unpinToggle.setVisibility(View.GONE);

            }

            pinToggle.setOnClickListener(v -> {
                _ti.setIsPinned(true);
                vm.update(_ti);
                notifyDataSetChanged();
            });

            unpinToggle.setOnClickListener(v -> {
                _ti.setIsPinned(false);
                vm.update(_ti);
                notifyDataSetChanged();
            });

            deleteToggle.setVisibility(View.VISIBLE);
            deleteToggle.setOnClickListener(v -> {
                vm.delete(_ti);
                notifyDataSetChanged();
            });

            id.setVisibility(View.VISIBLE);;
            id.setText(_ti.getId().toString());

            isPinned.setVisibility(View.VISIBLE);
            isPinned.setText(_ti.getIsPinned().toString());

            title.setText(_ti.getTitle());
            body.setText(_ti.getBody());

            return this;
        }

        public TileItemHolder (@NonNull View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.title);

            body = itemView.findViewById(R.id.body);

            id = itemView.findViewById(R.id.id);

            isPinned = itemView.findViewById(R.id.isPinned);

            pinToggle = itemView.findViewById(R.id.pinTileItem);

            unpinToggle = itemView.findViewById(R.id.unpinTileItem);

            deleteToggle = itemView.findViewById(R.id.deleteTileItem);

        }

        public TileItemHolder bindEmptyState () {
            title.setText("You haven't pinned anything!");
            body.setText("Consider tapping the \"Pin\" button, below any TileItems, to create a notification for that particular item!");
            id.setVisibility(View.GONE);
            unpinToggle.setVisibility(View.GONE);
            deleteToggle.setVisibility(View.GONE);
            isPinned.setVisibility(View.GONE);
            return this;
        }
    }
}
