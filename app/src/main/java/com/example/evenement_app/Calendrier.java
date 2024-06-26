package com.example.evenement_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calendrier extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EditText taskEditText;
    Button addTaskButton;
    ListView taskListView;
    List<String> taskList;
    ArrayAdapter<String> adapter;
    FirebaseFirestore db;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier2);

        /*taskEditText = findViewById(R.id.task);
        addTaskButton = findViewById(R.id.addtask);
        taskListView = findViewById(R.id.tasklist);

        // Initialize task list and adapter
        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(adapter);

        // Progress dialog
        pd = new ProgressDialog(this);

        // Firestore
        db = FirebaseFirestore.getInstance();

        // Set click listener to upload data
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Input data
                String taskText = taskEditText.getText().toString().trim();

                // Function call to upload data
                uploadData(taskText);
            }

            private void uploadData(String taskText) {
                // Set title of progress bar
                pd.setTitle("Adding data to Firestore");
                // Show progress bar when user clicks save button
                pd.show();

                // Prepare data to be uploaded
                Map<String, Object> doc = new HashMap<>();
                doc.put("task", taskText);

                // Add this data to Firestore
                db.collection("tasks").document(taskText).set(doc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // Called when data added successfully
                                pd.dismiss();
                                Toast.makeText(Calendrier.this, "Uploaded...", Toast.LENGTH_SHORT).show();
                                fetchTasks(); // Fetch tasks again to refresh the list
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Called if there is any error while uploading
                                pd.dismiss();
                                // Get and show error message
                                Toast.makeText(Calendrier.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Fetch tasks from Firestore
        fetchTasks();
*/
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
    /*private void fetchTasks() {
        db.collection("tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<com.google.firebase.firestore.QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.firestore.QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            taskList.clear(); // Clear the list before adding new items
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Retrieve the task information
                                String taskText = document.getString("task");
                                if (taskText != null) {
                                    taskList.add(taskText);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            // Handle the case where fetching data from Firestore failed
                            Exception e = task.getException();
                            if (e != null) {
                                Toast.makeText(Calendrier.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }*/
}