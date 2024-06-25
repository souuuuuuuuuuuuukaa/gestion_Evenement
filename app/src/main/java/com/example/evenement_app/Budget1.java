package com.example.evenement_app;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Budget1 extends AppCompatActivity {

    FirebaseFirestore db;
    private Button ajouterDepensesButton;
    private Button afficherDepensesButton;
    private EditText depensesEditText;
    private TextView budgetEventTextView;
    private TextView depensesListTextView;
    ProgressDialog pd;
    private Spinner eventSpinner;
    BottomNavigationView bottomNavigationView;

    private String selectedEventId;
    private int currentBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget1);

        ajouterDepensesButton = findViewById(R.id.ajouter_depences);
        afficherDepensesButton = findViewById(R.id.afficher_depences);
        eventSpinner = findViewById(R.id.event_spinner);
        budgetEventTextView = findViewById(R.id.budget_event);
        depensesEditText = findViewById(R.id.Depenses_event);
        depensesListTextView = findViewById(R.id.depenses_list);

        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        // Fetch events from Firestore and populate spinner
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Budget1.this, android.R.layout.simple_spinner_item, evenementsIds);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            eventSpinner.setAdapter(adapter);
                        } else {
                            Toast.makeText(Budget1.this, "Error fetching events: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Handle item selection in spinner
        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEventId = parent.getItemAtPosition(position).toString();
                // Query Firestore to get budget for selected event
                db.collection("evenements")
                        .document(selectedEventId)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                // Assuming your budget is stored as a field "budget" in the document
                                String budgetAmount = documentSnapshot.getString("Bedget");
                                if (budgetAmount != null) {
                                    // Extract the integer part from the budget string
                                    currentBudget = extractIntFromBudgetString(budgetAmount);
                                    // Update TextView with budget amount
                                    budgetEventTextView.setText(String.format("%,d ", currentBudget));
                                } else {
                                    Toast.makeText(Budget1.this, "Budget not available for " + selectedEventId, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Budget1.this, "Document not found: " + selectedEventId, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Budget1.this, "Error fetching budget: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no item selected scenario if needed
            }
        });

        // Add click listener for the ajouter_depenses button
        ajouterDepensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the expense amount
                String depensesString = depensesEditText.getText().toString();
                int depenses;
                try {
                    depenses = Integer.parseInt(depensesString);
                } catch (NumberFormatException e) {
                    Toast.makeText(Budget1.this, "Please enter a valid expense amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a map to store the data
                Map<String, Object> budgetData = new HashMap<>();
                budgetData.put("eventId", selectedEventId);
                budgetData.put("budget", currentBudget);
                budgetData.put("depenses", depenses);

                // Save the data to Firestore
                db.collection("budget")
                        .add(budgetData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Budget1.this, "Expense added successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Budget1.this, "Error adding expense: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Add click listener for the afficher_depenses button
        afficherDepensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("budget")
                        .whereEqualTo("eventId", selectedEventId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    StringBuilder depensesList = new StringBuilder();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        int depenses = document.getLong("depenses").intValue();
                                        depensesList.append(depenses).append("\n");
                                    }
                                    depensesListTextView.setText("-"+depensesList.toString());
                                } else {
                                    Toast.makeText(Budget1.this, "Error fetching expenses: " + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_Budget);
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
    }

    // Method to extract integer part from budget string (e.g., "3000 MAD")
    private int extractIntFromBudgetString(String budgetString) {
        try {
            // Remove non-numeric characters and trim whitespace
            String numericPart = budgetString.replaceAll("[^0-9]", "").trim();
            // Parse the numeric part to an integer
            return Integer.parseInt(numericPart);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0; // Default return if parsing fails
        }
    }
}
