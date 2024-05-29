package com.example.evenement_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Invites extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private List<Model> modelList = new ArrayList<>();
    private RecyclerView mrecyclerView;
    private CustomAdapter adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invites);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        mrecyclerView = findViewById(R.id.recycler_view);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView = findViewById(R.id.bottom_nav);
        setupBottomNavigationView();

        // Load data from Firestore
        showData();
    }

    private void setupBottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_Menu_y);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;
            int itemId = item.getItemId();
            if (itemId == R.id.nav_ajouter_event) {
                intent = new Intent(getApplicationContext(), AddEvent.class);
            } else if (itemId == R.id.nav_profil) {
                intent = new Intent(getApplicationContext(), Profil.class);
            } else if (itemId == R.id.nav_home) {
                intent = new Intent(getApplicationContext(), Accueil.class);
            } else if (itemId == R.id.nav_Budget) {
                intent = new Intent(getApplicationContext(), Budget1.class);
            } else if (itemId == R.id.nav_Menu_y) {
                intent = new Intent(getApplicationContext(), Menu.class);
            }
            if (intent != null) {
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });
    }

    private void showData() {
        db.collection("invites")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            Model model = new Model(doc.getString("id"), doc.getString("invite"));
                            modelList.add(model);
                        }
                        adapter = new CustomAdapter(Invites.this, modelList);
                        mrecyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(Invites.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(Invites.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
