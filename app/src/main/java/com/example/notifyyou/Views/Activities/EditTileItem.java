package com.example.notifyyou.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notifyyou.Models.TileItem;
import com.example.notifyyou.R;
import com.example.notifyyou.ViewModels.TileItemViewModel;

public class EditTileItem extends AppCompatActivity {

    private TileItemViewModel vm;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tile_item);

        TileItem ti = (TileItem) getIntent().getSerializableExtra("tile_item");
        vm  = new ViewModelProvider(this).get(TileItemViewModel.class);

        final com.google.android.material.textview.MaterialTextView id, isPinned;
        final com.google.android.material.textfield.TextInputEditText title, body;
        final com.google.android.material.button.MaterialButton pinToggle, unpinToggle, deleteToggle, saveChanges;

        title= findViewById(R.id.title);
        title.setText(ti.getTitle());

        body = findViewById(R.id.body);
        body.setText(ti.getBody());

        id = findViewById(R.id.id);
        id.setText(ti.getId().toString());

        isPinned = findViewById(R.id.isPinned);
        isPinned.setText(ti.getIsPinned().toString());

        pinToggle = findViewById(R.id.pinTileItem);
        pinToggle.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "pin was pressed!", Toast.LENGTH_SHORT).show();
        });

        unpinToggle = findViewById(R.id.unpinTileItem);
        unpinToggle.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "unpin was pressed!", Toast.LENGTH_SHORT).show();
        });

        deleteToggle = findViewById(R.id.deleteTileItem);
        if (ti.getIsPinned()) {
            unpinToggle.setVisibility(View.VISIBLE);
            pinToggle.setVisibility(View.GONE);

        } else {
            pinToggle.setVisibility(View.VISIBLE);
            unpinToggle.setVisibility(View.GONE);

        }

        saveChanges = findViewById(R.id.submit);
        saveChanges.setOnClickListener(v -> {
            ti.setTitle(title.getText().toString());
            ti.setBody(body.getText().toString());

            vm.update(ti);

            goBack();
        });

        deleteToggle.setOnClickListener(v -> {
            vm.delete(ti);
            goBack();
        });
    }

    private void redirect (Class _a) {
        Intent i = new Intent(this, _a);
        startActivity(i);
        finish();
    }

    private void goBack() {
        finish();
    }
}
