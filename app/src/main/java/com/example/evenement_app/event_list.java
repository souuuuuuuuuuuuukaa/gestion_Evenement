package com.example.evenement_app;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class event_list extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize RecyclerView and its adapter
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);

        // Fetch event data from Firestore
        fetchEventData();
    }

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
    private static class EventViewHolder extends RecyclerView.ViewHolder {

        ImageView eventImageView;
        TextView eventNameTextView;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImageView = itemView.findViewById(R.id.event_image_view);
            eventNameTextView = itemView.findViewById(R.id.event_name_text_view);
        }

        void bind(Event event) {
            eventNameTextView.setText(event.getEventName());
            Glide.with(itemView.getContext())
                    .load(event.getImageUrl())
                    .into(eventImageView);
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

        Event(String eventName, String imageUrl) {
            this.eventName = eventName;
            this.imageUrl = imageUrl;
        }

        String getEventName() {
            return eventName;
        }

        String getImageUrl() {
            return imageUrl;
        }
    }
}
