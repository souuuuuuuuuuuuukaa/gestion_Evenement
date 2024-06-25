package com.example.evenement_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListEvent extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;

    private FirebaseFirestore firestore;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize RecyclerView and its adapter
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_ajouter_event);

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

        // Fetch event data from Firestore
        fetchEventData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchEventData() {
        firestore.collection("evenements")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    eventList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String eventName = documentSnapshot.getString("Nom d'événement");
                        String imageUrl = documentSnapshot.getString("ImageURL");

                        Event event = new Event(eventName, imageUrl);
                        eventList.add(event);
                    }
                    eventAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("EventListActivity", "Error fetching events: " + e.getMessage());
                });
    }

    // ViewHolder for RecyclerView adapter
    private class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView eventImageView;
        TextView eventNameTextView;
        Event event;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImageView = itemView.findViewById(R.id.event_image_view);
            eventNameTextView = itemView.findViewById(R.id.event_name_text_view);
            itemView.setOnClickListener(this);
        }

        void bind(Event event) {
            this.event = event;
            eventNameTextView.setText(event.getEventName());
            Glide.with(itemView.getContext())
                    .load(event.getImageUrl())
                    .into(eventImageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ListEvent.this, EventDetails.class);
            intent.putExtra("eventName", event.getEventName());
            intent.putExtra("imageUrl", event.getImageUrl());
            intent.putExtra("eventDate", event.getEventDate() );
            intent.putExtra("eventTime", event.getEventTime() );
            intent.putExtra("budget", event.getBudget());
            Log.d("ListEvent", "EventName: " + event.getEventName() + ", ImageUrl: " + event.getImageUrl() + ", EventDate: " + event.getEventDate() + ", EventTime: " + event.getEventTime() + ", Budget: " + event.getBudget());
            startActivity(intent);
        }
        private void addInviteToEvent(String eventId, String inviteName) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Créer un nouvel invité avec un ID unique
            String inviteId = db.collection("evenements").document(eventId).collection("invites").document().getId();
            Model model = new Model(inviteId, inviteName);

            // Ajouter l'invité à la sous-collection 'invites' de l'événement
            db.collection("evenements").document(eventId).collection("invites").document(inviteId)
                    .set(model)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(ListEvent.this, "Invité ajouté avec succès à l'événement", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ListEvent.this, "Erreur lors de l'ajout de l'invité à l'événement", Toast.LENGTH_SHORT).show();
                    });
        }


    }

    // Adapter for RecyclerView
    private class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {

        private List<Event> eventList;

        EventAdapter(List<Event> eventList) {
            this.eventList = eventList;
        }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
            return new EventViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            Event event = eventList.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }
    }

    // Model class for Event
    private static class Event {
        private String eventName;
        private String imageUrl;
        private String eventDate; // Use Date type for event date
        private String eventTime; // Use Date type for event time
        private String budget;

        Event(String eventName, String imageUrl) {
            this.eventName = eventName;
            this.imageUrl = imageUrl;
        }

        public Event(String eventName, String imageUrl, String eventDate, String eventTime, String budget) {
            this.eventName = eventName;
            this.imageUrl = imageUrl;
            this.eventDate = eventDate;
            this.eventTime = eventTime;
            this.budget = budget;
        }

        String getEventName() {
            return eventName;
        }

        String getImageUrl() {
            return imageUrl;
        }

        String getEventDate() {
            return eventDate;
        }

        String getEventTime() {
            return eventTime;
        }

        String getBudget() {
            return budget;
        }
    }
}