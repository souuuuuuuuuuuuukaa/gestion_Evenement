package com.example.evenement_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import android.widget.ArrayAdapter;
import android.widget.Spinner;






import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class formulaire extends AppCompatActivity {




    //Progress Dialog
    ProgressDialog pd;
    //views
    EditText invite_name;

    private Spinner eventSpinner;
    Button ajouter;

    //Firestore instance
    FirebaseFirestore db;






BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        invite_name = findViewById(R.id.invite_name);
        ajouter = findViewById(R.id.ajouter);
        eventSpinner = findViewById(R.id.event_spinner); // Initialize Spinner

        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        // Fetch event IDs from Firestore and populate Spinner
        db.collection("evenements")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> evenementsIds = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                evenementsIds.add(document.getId());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(formulaire.this, android.R.layout.simple_spinner_item, evenementsIds);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            eventSpinner.setAdapter(adapter);
                        } else {
                            Toast.makeText(formulaire.this, "Error fetching events: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //click button to upload data


        //répondre par oui
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input data
                String invite = invite_name.getText().toString().trim();
                eventSpinner = findViewById(R.id.event_spinner);
                //fuction call to upload data
                uploadData(invite);
            }





            private void uploadData(String invite) {
                pd.setTitle("Ajout de l'invité");
                pd.show();

                // Récupérer l'ID de l'événement sélectionné dans le Spinner
                String eventId = eventSpinner.getSelectedItem().toString(); // Assuming event IDs are stored as strings in the Spinner

                // Générer un ID unique pour l'invité
                String inviteId = UUID.randomUUID().toString();

                // Préparer les données de l'invité à ajouter
                Map<String, Object> inviteData = new HashMap<>();
                inviteData.put("nom", invite); // Exemple de champ "nom" pour l'invité, ajustez selon vos besoins

                // Ajouter l'invité à la collection "invites" du document d'événement correspondant
                db.collection("evenements").document(eventId).collection("invites").document(inviteId).set(inviteData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    pd.dismiss();
                                    Toast.makeText(formulaire.this, "Invité ajouté avec succès à l'événement.", Toast.LENGTH_SHORT).show();
                                    Intent homeIntent = new Intent(getApplicationContext(), AddEvent.class);
                                    startActivity(homeIntent);
                                    finish();
                                } else {
                                    pd.dismiss();
                                    Toast.makeText(formulaire.this, "Erreur lors de l'ajout de l'invité : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });






        bottomNavigationView=findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.nav_Menu_y);

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