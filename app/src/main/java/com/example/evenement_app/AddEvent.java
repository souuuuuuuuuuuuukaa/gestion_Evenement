package com.example.evenement_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddEvent extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;

    ProgressDialog progressDialog;

    TextInputEditText EventName, DateEvent, EventTime, Bedget;
    Button button;
    ImageView selectImageView, selectedImageView;

    FirebaseFirestore firebaseFirestore;
    StorageReference storageRef;

    Uri imageUri;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // Initialize views
        EventName = findViewById(R.id.EventName);
        DateEvent = findViewById(R.id.DateEvent);
        EventTime = findViewById(R.id.EventTime);
        Bedget = findViewById(R.id.Bedget);
        button = findViewById(R.id.button);
        selectImageView = findViewById(R.id.select_image_view);
        selectedImageView = findViewById(R.id.selected_image_view); // assuming you have an ImageView in your layout for showing selected image

        // Initialize Progress dialog
        progressDialog = new ProgressDialog(this);

        // Initialize Firestore and Storage
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        // Click button to upload data
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = EventName.getText().toString().trim();
                String dateEvent = DateEvent.getText().toString().trim();
                String eventTime = EventTime.getText().toString().trim();
                String bedget = Bedget.getText().toString().trim();

                // Perform validations and upload data to Firestore
                // Implement your logic here for uploading data
                uploadEventData(eventName, dateEvent, eventTime, bedget);
            }
        });

        // Click select_image_view to choose an image
        selectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.nav_ajouter_event);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_ajouter_event) {
                // Handle navigation item clicks
                return true;
            } else if (itemId == R.id.nav_profil) {
                // Handle navigation item clicks
                return true;
            } else if (itemId == R.id.nav_home) {
                // Handle navigation item clicks
                return true;
            } else if (itemId == R.id.nav_Budget) {
                // Handle navigation item clicks
                return true;
            } else if (itemId == R.id.nav_Menu_y) {
                // Handle navigation item clicks
                return true;
            }
            return false;
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                selectedImageView.setImageURI(imageUri);
                selectedImageView.setVisibility(View.VISIBLE);
                selectImageView.setVisibility(View.GONE);
            }
        }
    }

    private void uploadEventData(String eventName, String dateEvent, String eventTime, String bedget) {
        // Show progress dialog
        progressDialog.setTitle("Adding event data...");
        progressDialog.show();

        // Generate a random UUID for the image file name
        String imageFileName = UUID.randomUUID().toString();

        // Check if an image is selected
        if (imageUri != null) {
            // Get reference to Firebase Storage
            StorageReference imageRef = storageRef.child("event_images/" + imageFileName);

            // Upload image to Firebase Storage
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully, now get its download URL
                        imageRef.getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    // Image download URL obtained
                                    String imageUrl = uri.toString();

                                    // Prepare data to be uploaded to Firestore
                                    Map<String, Object> eventData = new HashMap<>();
                                    eventData.put("Nom d'événement", eventName);
                                    eventData.put("Date d'événement", dateEvent);
                                    eventData.put("L'heure", eventTime);
                                    eventData.put("Bedget", bedget + " MAD");
                                    eventData.put("ImageURL", imageUrl); // Add image URL to Firestore

                                    // Upload event data to Firestore
                                    firebaseFirestore.collection("evenements").document(eventName)
                                            .set(eventData)
                                            .addOnSuccessListener(aVoid -> {
                                                // Data uploaded successfully
                                                progressDialog.dismiss();
                                                Toast.makeText(AddEvent.this, "Event data uploaded", Toast.LENGTH_SHORT).show();
                                                Intent homeIntent = new Intent(getApplicationContext(), ListEvent.class);
                                                startActivity(homeIntent);
                                                finish();
                                            })
                                            .addOnFailureListener(e -> {
                                                // Error uploading data
                                                progressDialog.dismiss();
                                                Toast.makeText(AddEvent.this, "Failed to upload event data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
                                })
                                .addOnFailureListener(e -> {
                                    // Error getting image download URL
                                    progressDialog.dismiss();
                                    Toast.makeText(AddEvent.this, "Failed to get image URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    })
                    .addOnFailureListener(e -> {
                        // Error uploading image
                        progressDialog.dismiss();
                        Toast.makeText(AddEvent.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            // If no image is selected, upload event data without image URL
            Map<String, Object> eventData = new HashMap<>();
            eventData.put("Nom d'événement", eventName);
            eventData.put("Date d'événement", dateEvent);
            eventData.put("L'heure", eventTime);
            eventData.put("Bedget", bedget + " MAD");

            // Upload event data to Firestore
            firebaseFirestore.collection("evenements").document(eventName)
                    .set(eventData)
                    .addOnSuccessListener(aVoid -> {
                        // Data uploaded successfully
                        progressDialog.dismiss();
                        Toast.makeText(AddEvent.this, "Event data uploaded", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(getApplicationContext(), ListEvent.class);
                        startActivity(homeIntent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Error uploading data
                        progressDialog.dismiss();
                        Toast.makeText(AddEvent.this, "Failed to upload event data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
