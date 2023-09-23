package com.example.notifyyou.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;

import java.util.ArrayList;

public class PinnedTileItemsAdapter extends RecyclerView.Adapter<PinnedTileItemsAdapter.ViewHolder> {

    private ArrayList<TileItem> itemList;

    public PinnedTileItemsAdapter(ArrayList<TileItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinned_tile_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TileItem item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, body;
        private ImageButton edit, deactivate, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            edit = itemView.findViewById(R.id.editButton);
            deactivate = itemView.findViewById(R.id.deactivateButton);
            delete = itemView.findViewById(R.id.deleteButton);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TileItem ti = itemList.get(getAdapterPosition());
                    Toast.makeText(view.getContext(), "Edit button for " + ti.getTitle() + " is pressed", Toast.LENGTH_LONG).show();
                }
            });

            deactivate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TileItem ti = itemList.get(getAdapterPosition());
                    Toast.makeText(view.getContext(), "Deactivate button for " + ti.getTitle() + " is pressed", Toast.LENGTH_LONG).show();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TileItem ti = itemList.get(getAdapterPosition());
                    Toast.makeText(view.getContext(), "Delete button for " + ti.getTitle() + " is pressed", Toast.LENGTH_LONG).show();
                }
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
