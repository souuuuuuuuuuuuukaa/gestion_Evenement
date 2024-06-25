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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Ajouter_Acceuil extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;

    ProgressDialog pd;
    EditText descriptionEditText;
    Spinner eventSpinner;
    Button publier;
    ImageView selectImageView, selectedImageView;
    Uri imageUri;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_acceuil);

        descriptionEditText = findViewById(R.id.description);
        selectImageView = findViewById(R.id.select_image_view);
        selectedImageView = findViewById(R.id.selected_image_view);
        publier = findViewById(R.id.publier);
        eventSpinner = findViewById(R.id.event_spinner);

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading...");

        db = FirebaseFirestore.getInstance();

        selectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        publier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = descriptionEditText.getText().toString().trim();
                String eventId = eventSpinner.getSelectedItem().toString();

                if (imageUri != null) {
                    uploadAcceuilData(description, eventId);
                } else {
                    Toast.makeText(Ajouter_Acceuil.this, "Select an image first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadEventSpinner();
    }

    private void loadEventSpinner() {
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Ajouter_Acceuil.this, android.R.layout.simple_spinner_item, evenementsIds);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            eventSpinner.setAdapter(adapter);
                        } else {
                            Toast.makeText(Ajouter_Acceuil.this, "Error fetching events: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
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

    private void uploadAcceuilData(String description, String eventId) {
        pd.show(); // Show progress dialog

        // Generate a random unique ID for the image
        String imageId = UUID.randomUUID().toString();

        // Create a reference to store image in Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + imageId);

        // Upload image to Firebase Storage
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Image uploaded successfully
                    // Get the download URL of the uploaded image
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();

                        // Create a new document in Firestore
                        Map<String, Object> data = new HashMap<>();
                        data.put("description", description);
                        data.put("eventId", eventId);
                        data.put("imageUrl", imageUrl);

                        // Add document with a generated ID
                        db.collection("acceuils")
                                .add(data)
                                .addOnSuccessListener(documentReference -> {
                                    pd.dismiss(); // Dismiss progress dialog
                                    Toast.makeText(Ajouter_Acceuil.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                                    // Clear fields and reset UI
                                    descriptionEditText.setText("");
                                    selectedImageView.setImageURI(null);
                                    selectedImageView.setVisibility(View.GONE);
                                    selectImageView.setVisibility(View.VISIBLE);
                                })
                                .addOnFailureListener(e -> {
                                    pd.dismiss(); // Dismiss progress dialog
                                    Toast.makeText(Ajouter_Acceuil.this, "Failed to upload data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    });
                })
                .addOnFailureListener(e -> {
                    pd.dismiss(); // Dismiss progress dialog
                    Toast.makeText(Ajouter_Acceuil.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
