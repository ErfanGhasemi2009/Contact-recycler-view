package com.alaroze.contact;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements ContactAdapter.ItemEventListener {

    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private int editingPosition = -1;
    EditText etFullname;
    ImageButton btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.rv_main_contact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ContactAdapter(this);
        recyclerView.setAdapter(adapter);

        etFullname = findViewById(R.id.et_main_fullname);
        btnChange = findViewById(R.id.btn_main_add);

        btnChange.setOnClickListener(v -> {
            if (etFullname.length() > 0) {
                if (editingPosition == -1) {
                    adapter.addContact(etFullname.getText().toString());
                    recyclerView.scrollToPosition(0);
                }else {
                    adapter.editContact(etFullname.getText().toString(), editingPosition);
                    recyclerView.scrollToPosition(editingPosition);

                    editingPosition = -1;
                    btnChange.setImageResource(R.drawable.ic_add_white_24dp);
                }
                etFullname.setText("");
            }
        });

    }

    @Override
    public void onItemViewClick(String fullname, int position) {
        editingPosition = position;
        etFullname.setText(fullname);
        btnChange.setImageResource(R.drawable.ic_done_white_24dp);
    }
}