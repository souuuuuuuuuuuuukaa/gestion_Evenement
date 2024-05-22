package com.example.evenement_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListEvent extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    ListView listView;
    private List<Event> events;
    private ArrayAdapter<Event> adapter;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        listView = findViewById(R.id.listview);
        events = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, events);
        listView.setAdapter(adapter);
        firebaseFirestore = FirebaseFirestore.getInstance();

      // Récupérer les événements depuis Firestore
   /*    firebaseFirestore.collection("evenements")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Récupérer le nom de l'événement
                            String eventName = document.getString("Nom d'événement");
                            eventNames.add(eventName);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });*/


        firebaseFirestore.collection("evenements")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Log the entire document to inspect its content
                            Log.d("Firestore", "Document data: " + document.getData());

                            // Récupérer les informations sur l'événement
                            String eventName = document.getString("Nom d'événement");
                            Timestamp eventDateTimestamp = document.getTimestamp("Date d'événement");
                            Timestamp eventHeureTimestamp = document.getTimestamp("L'heure");
                            String budgetevent=document.getString("Bedget");

                            // Vérifier si les valeurs ne sont pas nulles avant d'appeler toDate()
                            if (eventDateTimestamp != null && eventHeureTimestamp != null) {
                                Date eventDate = eventDateTimestamp.toDate();
                                Date eventTime = eventHeureTimestamp.toDate();

                                // Créer un nouvel événement et l'ajouter à la liste
                                Event event = new Event(eventName, eventDate,eventTime,budgetevent);
                                events.add(event);
                            } else {
                                // Gérer le cas où les valeurs de date ou d'heure sont nulles
                                Log.e("Firestore", "Date d'événement ou L'heure est nulle pour l'événement: " + eventName);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Gérer le cas où la récupération des données depuis Firestore a échoué
                        Exception e = task.getException();
                        if (e != null) {
                            Log.e("Firestore", "Erreur lors de la récupération des données depuis Firestore", e);
                        } else {
                            Log.e("Firestore", "Erreur inconnue lors de la récupération des données depuis Firestore");
                        }
                    }
                });





        bottomNavigationView=findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.nav_ajouter_event);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_ajouter_event) {
                Intent hometIntent = new Intent(getApplicationContext(), AddEvent.class);
                startActivity(hometIntent);
                finish();
                return true;
            } else if (itemId == R.id.nav_profil) {
                Intent hometIntent = new Intent(getApplicationContext(),Profil.class);
                startActivity(hometIntent);
                finish();
                return true;
            } else  if (itemId == R.id.nav_home) {
                Intent hometIntent = new Intent(getApplicationContext(), Accueil.class);
                startActivity(hometIntent);
                finish();
                return true;
            }
            else if (itemId == R.id.nav_Budget) {
                Intent hometIntent = new Intent(getApplicationContext(), Budget1.class);
                startActivity(hometIntent);
                finish();
                return true;
            }
            else if (itemId == R.id.nav_Menu_y) {
                Intent hometIntent = new Intent(getApplicationContext(), Menu.class);
                startActivity(hometIntent);
                finish();
                return true;
            }
            return false;
        });
    }
}