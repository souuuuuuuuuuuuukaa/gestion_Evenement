package com.example.evenement_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // Récupérer les données passées depuis ListEvent
        Intent intent = getIntent();
        if (intent != null) {
            String eventName = intent.getStringExtra("eventName");

            // Initialisation de Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Référence vers le document spécifique dans Firestore
            DocumentReference eventRef = db.collection("evenements").document(eventName);

            // Lecture des données
            eventRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        // Le document existe, récupérer les données
                        String eventName = documentSnapshot.getString("Nom d'événement");
                        String imageUrl = documentSnapshot.getString("ImageURL");
                        String heure = documentSnapshot.getString("L'heure");
                        String budget = documentSnapshot.getString("Bedget");
                        String dateEvenement = documentSnapshot.getString("Date d'événement");

                        // Afficher les données dans votre activité
                        TextView eventNameTextView = findViewById(R.id.event_name);
                        if (eventNameTextView != null) {
                            eventNameTextView.setText(eventName);
                        }

                        TextView heureTextView = findViewById(R.id.event_time);
                        if (heureTextView != null) {
                            heureTextView.setText("Heure: " + heure);
                        }

                        TextView budgetTextView = findViewById(R.id.budget);
                        if (budgetTextView != null) {
                            budgetTextView.setText("Budget: " + budget);
                        }

                        TextView dateTextView = findViewById(R.id.event_date);
                        if (dateTextView != null) {
                            dateTextView.setText("Date d'événement: " + dateEvenement);
                        }

                        ImageView eventImageView = findViewById(R.id.event_image);
                        if (imageUrl != null) {
                            Glide.with(EventDetails.this)
                                    .load(imageUrl)
                                    .into(eventImageView);
                        }
                    } else {
                        // Le document n'existe pas
                        Toast.makeText(EventDetails.this, "Document not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Erreur lors de la récupération des données
                    Toast.makeText(EventDetails.this, "Error fetching document", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
