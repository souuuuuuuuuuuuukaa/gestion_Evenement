package com.example.evenement_app;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Invites extends AppCompatActivity {

    private FirebaseFirestore db;
    private ListView listViewEvents;
    private TextView textViewInviteDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invites);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        listViewEvents = findViewById(R.id.listViewEvents);
        textViewInviteDetails = findViewById(R.id.textViewInviteDetails);

        // Retrieve event names from Firestore collection "evenements"
        db.collection("evenements").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> eventNames = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        eventNames.add(document.getId()); // Use document ID as event name
                    }
                    // Set up ArrayAdapter for the ListView
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Invites.this, android.R.layout.simple_list_item_1, eventNames);
                    listViewEvents.setAdapter(adapter);

                    // Handle click on an event
                    listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String eventName = (String) parent.getItemAtPosition(position);
                            displayInviteDetails(eventName); // Display invite details for selected event
                        }
                    });
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                    Toast.makeText(Invites.this, "Error fetching events", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Display invite details for the selected event
    private void displayInviteDetails(String eventName) {
        db.collection("evenements").document(eventName).collection("invites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    StringBuilder inviteDetails = new StringBuilder();
                    for (DocumentSnapshot document : task.getResult()) {
                        String nom = document.getString("nom");
                        if (nom != null) {
                            inviteDetails.append("Nom: ").append(nom).append("\n");
                        }
                        // Ajoutez d'autres champs d'invitation si nécessaire (email, etc.)
                    }
                    Log.d("TAG", "Invite details: " + inviteDetails.toString()); // Log pour le débogage
                    textViewInviteDetails.setText(inviteDetails.toString()); // Met à jour le TextView avec les détails des invitations
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                    Toast.makeText(Invites.this, "Erreur lors de la récupération des invitations", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
