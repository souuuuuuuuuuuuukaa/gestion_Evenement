package com.example.evenement_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class formulaire extends AppCompatActivity {




    //Progress Dialog
    ProgressDialog pd;
    //views
    EditText NomEtPrenom;
    Button oui,non;

    //Firestore instance
    FirebaseFirestore db;






BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);





        //initializer views avec leurs XML
        NomEtPrenom = findViewById(R.id.NomEtPrenom);

        oui=findViewById(R.id.oui);
        non=findViewById(R.id.non);

        //Progress dialog
        pd= new ProgressDialog(this);

        //firestore
        db = FirebaseFirestore.getInstance();

        //click button to upload data
        oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input data
                String invite = NomEtPrenom.getText().toString().trim();
                //fuction call to upload data
                uploadData(invite);
            }



            private void uploadData(String invite ) {
                //set title of progress bar;
                pd.setTitle("adding data to firestore");
                //show progress bar when user click save button
                pd.show();
                //random id for each data to be stored
                String id = UUID.randomUUID().toString();

                Map<String,Object> doc=new HashMap<>();
                doc.put("id",id);//id of data
                doc.put("invite",invite);


                //add this data
                db.collection("invites").document(id).set(doc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //called when data added successfully
                                pd.dismiss();
                                Toast.makeText(formulaire.this,"uploaded...",Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //called if there is any error while uploading
                                pd.dismiss();
                                //get and show error message
                                Toast.makeText(formulaire.this,e.getMessage(),Toast.LENGTH_SHORT).show();


                            }
                        });
            }
        });
        non.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent hometIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(hometIntent);
                finish();
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