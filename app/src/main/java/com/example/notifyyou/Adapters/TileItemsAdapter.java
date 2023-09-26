package com.example.notifyyou.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class TileItemsAdapter extends RecyclerView.Adapter<TileItemsAdapter.ViewHolder> {
    private ArrayList<TileItem> itemList;

    public TileItemsAdapter (ArrayList<TileItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public TileItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tile_items_layout, parent, false);
        return new TileItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TileItemsAdapter.ViewHolder holder, int position) {
        TileItem item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, body;
        private final ImageButton edit, deactivate, delete;

        private void MakeSnackbar (String buttonName, String name, View itemView) {
            final Integer MARGIN_BOTTOM = 225;

            Snackbar s = Snackbar.make(itemView, buttonName + " button for " + name + " was pressed", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", view -> {
                        Snackbar s1 = Snackbar.make(itemView, "Restored!", Snackbar.LENGTH_SHORT).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);

                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)  s1.getView().getLayoutParams();

                        params.setMargins(0, 0, 0, MARGIN_BOTTOM);

                        s1.getView().setLayoutParams(params);
                        s1.show();
                    })
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);

            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) s.getView().getLayoutParams();

            params.setMargins(0, 0, 0, MARGIN_BOTTOM);

            s.getView().setLayoutParams(params);
            s.show();
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            edit = itemView.findViewById(R.id.editButton);
            deactivate = itemView.findViewById(R.id.deactivateButton);
            delete = itemView.findViewById(R.id.deleteButton);

            edit.setOnClickListener(view -> {
                TileItem ti = itemList.get(getAdapterPosition());
                MakeSnackbar("Edit", ti.getTitle(), view);
            });

            deactivate.setOnClickListener(view -> {
                TileItem ti = itemList.get(getAdapterPosition());
                MakeSnackbar("Deactivate", ti.getTitle(), view);
            });

            delete.setOnClickListener(view -> {
                TileItem ti = itemList.get(getAdapterPosition());
                MakeSnackbar("Delete", ti.getTitle(), view);
            });
        }

        public void bind(TileItem item) {
            title.setText(item.getTitle());
            title.setMaxLines(2);
            body.setText(item.getBody());
            body.setMaxLines(3);
        }
    }
}
