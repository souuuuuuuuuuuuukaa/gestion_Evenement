package com.example.evenement_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventDetails extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_ajouter_event);

        // Set bottom navigation item click listener
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

        invite= findViewById(R.id.addpersone);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "envoyer une invitation", Toast.LENGTH_SHORT).show();
                Intent hometIntent = new Intent(getApplicationContext(), Email.class);
                startActivity(hometIntent);
                finish();
            }
        });
    }
}
