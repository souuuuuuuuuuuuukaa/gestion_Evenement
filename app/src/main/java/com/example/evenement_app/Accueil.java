package com.example.evenement_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Accueil extends AppCompatActivity {
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private AccueilAdapter accueilAdapter;
    private List<AccueilModel> accueilList = new ArrayList<>();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accueilAdapter = new AccueilAdapter(accueilList);
        recyclerView.setAdapter(accueilAdapter);




        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_ajouter_event) {
                Intent hometIntent = new Intent(getApplicationContext(), AddEvent.class);
                startActivity(hometIntent);
                finish();
                return true;
            } else if (itemId == R.id.nav_profil) {
                Intent hometIntent = new Intent(getApplicationContext(), Profil.class);
                startActivity(hometIntent);
                finish();
                return true;
            } else if (itemId == R.id.nav_home) {
                Intent hometIntent = new Intent(getApplicationContext(), Accueil.class);
                startActivity(hometIntent);
                finish();
                return true;
            } else if (itemId == R.id.nav_Budget) {
                Intent hometIntent = new Intent(getApplicationContext(), Budget1.class);
                startActivity(hometIntent);
                finish();
                return true;
            } else if (itemId == R.id.nav_Menu_y) {
                Intent hometIntent = new Intent(getApplicationContext(), Menu.class);
                startActivity(hometIntent);
                finish();
                return true;
            }
            return false;
        });


        // Fetch data from Firestore
        db.collection("acceuils")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    accueilList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String description = documentSnapshot.getString("description");
                        String imageUrl = documentSnapshot.getString("imageUrl");
                        String eventId = documentSnapshot.getString("eventId");

                        AccueilModel accueil = new AccueilModel(eventId, imageUrl, description);
                        accueilList.add(accueil);
                    }
                    accueilAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("Accueil", "Error fetching events: " + e.getMessage());
                });
    }

    private static class AccueilViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView description;
        TextView eventId;

        AccueilViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.event_image);
            description = itemView.findViewById(R.id.event_description);
            eventId = itemView.findViewById(R.id.event_id);
        }

        void bind(AccueilModel accueil) {
            description.setText(accueil.getDescription());
            eventId.setText(accueil.getEventId());
            Glide.with(itemView.getContext())
                    .load(accueil.getImageUrl())
                    .into(imageView);
        }
    }

    private class AccueilAdapter extends RecyclerView.Adapter<AccueilViewHolder> {
        private List<AccueilModel> accueilList;

        AccueilAdapter(List<AccueilModel> accueilList) {
            this.accueilList = accueilList;
        }

        @NonNull
        @Override
        public AccueilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acceuil, parent, false);
            return new AccueilViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AccueilViewHolder holder, int position) {
            AccueilModel accueil = accueilList.get(position);
            holder.bind(accueil);
        }

        @Override
        public int getItemCount() {
            return accueilList.size();
        }
    }

    private static class AccueilModel {
        private String description;
        private String eventId;
        private String imageUrl;

        AccueilModel(String eventId, String imageUrl, String description) {
            this.eventId = eventId;
            this.imageUrl = imageUrl;
            this.description = description;
        }

        String getEventId() {
            return eventId;
        }

        String getDescription() {
            return description;
        }

        String getImageUrl() {
            return imageUrl;
        }
    }
}
