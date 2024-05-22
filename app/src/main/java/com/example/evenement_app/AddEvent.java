package com.example.evenement_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class AddEvent extends AppCompatActivity {

    //Progress Dialog
    ProgressDialog progressDialog;
    //views
    EditText EventName,DateEvent,EventTime,Bedget;
    Button button;

    //Firestore instance
    FirebaseFirestore firebaseFirestore;

    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);



        //initializer views avec leurs XML
        EventName = findViewById(R.id.EventName);
        DateEvent=findViewById(R.id.DateEvent);
        EventTime=findViewById(R.id.EventTime);
        Bedget=findViewById(R.id.Bedget);
        button=findViewById(R.id.button);

        //Progress dialog
        progressDialog= new ProgressDialog(this);
        //firestore
        firebaseFirestore = FirebaseFirestore.getInstance();
        //click button to upload data
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input data
                String event = EventName.getText().toString().trim();
                String dateeventStr =DateEvent.getText().toString().trim();
                String timeeventStr = EventTime.getText().toString().trim();
                double Bedgetevent = Double.parseDouble(Bedget.getText().toString().trim());

                // Convert date and time strings to Date objects
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                Date dateevent, timeevent;
                try {
                    dateevent = dateFormat.parse(dateeventStr);
                    timeevent = timeFormat.parse(timeeventStr);
                } catch (ParseException e) {
                    // Handle parsing errors
                    Toast.makeText(getApplicationContext(), "Error parsing date or time", Toast.LENGTH_SHORT).show();
                    return;
                }
                Timestamp timestampDate = new Timestamp(dateevent);
                Timestamp timestampTime = new Timestamp(timeevent);

                //fuction call to upload data
                uploadData(event,timestampDate,timestampTime , Bedgetevent);
            }

            private void uploadData(String event, Timestamp dateevent, Timestamp timeevent, Double Bedgetevent) {
                //set title of progress bar;
                progressDialog.setTitle("adding data to firestore");
                //show progress bar when user click save button
                progressDialog.show();
                //random id for each data to be stored
               // String id = UUID.randomUUID().toString();
                Map<String,Object> doc=new HashMap<>();
               // doc.put("id",id);//id of data
                doc.put("Nom d'événement",event);
                doc.put("Date d'événement",dateevent);
                doc.put("L'heure",timeevent);
                doc.put("Bedget",Bedgetevent+" MAD");

                //add this data
                firebaseFirestore.collection("evenements").document(event).set(doc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //called when data added successfully
                                progressDialog.dismiss();
                                Toast.makeText(AddEvent.this,"uploaded...",Toast.LENGTH_SHORT).show();
                                Intent hometIntent = new Intent(getApplicationContext(), ListEvent.class);
                                startActivity(hometIntent);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //called if there is any error while uploading
                                progressDialog.dismiss();
                                //get and show error message
                                Toast.makeText(AddEvent.this,e.getMessage(),Toast.LENGTH_SHORT).show();


                            }
                        });
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