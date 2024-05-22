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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Budget1 extends AppCompatActivity {

/*

    //Progress Dialog
    ProgressDialog pd;
    //views
    EditText depenses;
    Button button;

    //Firestore instance
    FirebaseFirestore db;
*/


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget1);


/*
        //initializer views avec leurs XML
        depenses = findViewById(R.id.depenses);

        button=findViewById(R.id.button);


        //Progress dialog
        pd= new ProgressDialog(this);

        //firestore
        db = FirebaseFirestore.getInstance();

        //click button to upload data
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input data
                String dep = depenses.getText().toString().trim();
                //fuction call to upload data
                uploadData(dep);
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
                doc.put("depenses",depenses);


                //add this data
                db.collection("budget").document(id).set(doc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //called when data added successfully
                                pd.dismiss();
                                Toast.makeText(Budget1.this,"uploaded...",Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //called if there is any error while uploading
                                pd.dismiss();
                                //get and show error message
                                Toast.makeText(Budget1.this,e.getMessage(),Toast.LENGTH_SHORT).show();


                            }
                        });
            }
        });
        */







        bottomNavigationView=findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.nav_Budget);

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