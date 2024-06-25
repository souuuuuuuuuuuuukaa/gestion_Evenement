/*
package com.example.evenement_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class EventDetails extends AppCompatActivity {

    Button Listpart,invite;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // Récupérer les données passées depuis ListEvent
        String eventName = getIntent().getStringExtra("eventName");
        Date eventDate = (Date) getIntent().getSerializableExtra("eventDate");
        Date eventTime = (Date) getIntent().getSerializableExtra("eventTime");
        String budget = getIntent().getStringExtra("budget");

        // Afficher le nom de l'événement
        TextView eventNameTextView = findViewById(R.id.event_name);
        eventNameTextView.setText(eventName);

        // Afficher la date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(eventDate);
        TextView eventDateTextView = findViewById(R.id.event_date);
        eventDateTextView.setText(formattedDate);

        // Afficher l'heure
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedTime = timeFormat.format(eventTime);
        TextView eventTimeTextView = findViewById(R.id.event_time);
        eventTimeTextView.setText(formattedTime);

        // Afficher le budget
        TextView budgetTextView = findViewById(R.id.budget);
        budgetTextView.setText(budget);



        //botton
        Listpart= findViewById(R.id.ListEvent);
        invite= findViewById(R.id.addpersone);

        Listpart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Liste participants", Toast.LENGTH_SHORT).show();
                Intent hometIntent = new Intent(getApplicationContext(), Invites.class);
                startActivity(hometIntent);
                finish();

            }
        });


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
*//*

*/
/*
package com.example.evenement_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // Récupérer les données passées depuis ListEvent
        Intent intent = getIntent();
        if (intent != null) {
            String eventName = intent.getStringExtra("eventName");
            String imageUrl = intent.getStringExtra("imageUrl");
            long eventDateMillis = intent.getLongExtra("eventDate", -1);
            long eventTimeMillis = intent.getLongExtra("eventTime", -1);
            String budget = intent.getStringExtra("budget");

            // Vérifier si eventDateMillis et eventTimeMillis sont valides (-1 signifie null dans ce contexte)
            Date eventDate = eventDateMillis != -1 ? new Date(eventDateMillis) : null;
            Date eventTime = eventTimeMillis != -1 ? new Date(eventTimeMillis) : null;

            // Afficher le nom de l'événement
            TextView eventNameTextView = findViewById(R.id.event_name);
            eventNameTextView.setText(eventName);

            // Afficher l'image de l'événement
            ImageView eventImageView = findViewById(R.id.event_image);
            Glide.with(this).load(imageUrl).into(eventImageView);

            // Afficher la date si elle n'est pas null
            if (eventDate != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(eventDate);
                TextView eventDateTextView = findViewById(R.id.event_date);
                eventDateTextView.setText(formattedDate);
            } else {
                // Gérer le cas où eventDate est null
                // Par exemple, afficher un message par défaut ou masquer la vue
            }

            // Afficher l'heure si elle n'est pas null
            if (eventTime != null) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String formattedTime = timeFormat.format(eventTime);
                TextView eventTimeTextView = findViewById(R.id.event_time);
                eventTimeTextView.setText(formattedTime);
            } else {
                // Gérer le cas où eventTime est null
                // Par exemple, afficher un message par défaut ou masquer la vue
            }

            // Afficher le budget
            TextView budgetTextView = findViewById(R.id.budget);
            budgetTextView.setText(budget);
        } else {
            // Gérer le cas où intent est null
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
            finish(); // Terminer l'activité si intent est null
        }
    }
}
*//*

package com.example.evenement_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventDetails extends AppCompatActivity {

    Button Listpart, invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Intent intent = getIntent();
        if (intent != null) {
            String eventName = intent.getStringExtra("eventName");
            String imageUrl = intent.getStringExtra("imageUrl");
            String eventDateStr = intent.getStringExtra("eventDate");
            String eventTimeStr = intent.getStringExtra("eventTime");
            String budget = intent.getStringExtra("budget");

            // Afficher le nom de l'événement
            TextView eventNameTextView = findViewById(R.id.event_name);
            eventNameTextView.setText(eventName);

            // Afficher l'image de l'événement
            ImageView eventImageView = findViewById(R.id.event_image);
            Glide.with(this).load(imageUrl).into(eventImageView);

            // Afficher la date
            TextView eventDateTextView = findViewById(R.id.event_date);
            eventDateTextView.setText(eventDateStr);

            // Afficher l'heure
            TextView eventTimeTextView = findViewById(R.id.event_time);
            eventTimeTextView.setText(eventTimeStr);

            // Afficher le budget
            TextView budgetTextView = findViewById(R.id.budget);
            budgetTextView.setText(budget);
        } else {
            // Gérer le cas où intent est null
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
            finish(); // Terminer l'activité si intent est null
        }
        Listpart= findViewById(R.id.ListEvent);
        invite= findViewById(R.id.addpersone);

        Listpart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Liste participants", Toast.LENGTH_SHORT).show();
                Intent hometIntent = new Intent(getApplicationContext(), Invites.class);
                startActivity(hometIntent);
                finish();

            }
        });


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

*/
package com.example.evenement_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventDetails extends AppCompatActivity {

    Button Listpart, invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // Récupérer les données passées depuis ListEvent
        Intent intent = getIntent();
        if (intent != null) {
            String eventName = intent.getStringExtra("eventName");
            String eventDate = intent.getStringExtra("eventDate");
            String eventTime = intent.getStringExtra("eventTime");
            String budget = intent.getStringExtra("budget");
            String imageUrl = intent.getStringExtra("imageUrl");

            // Afficher le nom de l'événement
            TextView eventNameTextView = findViewById(R.id.event_name);
            if (eventNameTextView != null && eventName != null) {
                eventNameTextView.setText(eventName);
            }

            // Afficher la date
            TextView eventDateTextView = findViewById(R.id.event_date);
            if (eventDateTextView != null && eventDate != null) {
                eventDateTextView.setText(eventDate);
            }

            // Afficher l'heure
            TextView eventTimeTextView = findViewById(R.id.event_time);
            if (eventTimeTextView != null && eventTime != null) {
                eventTimeTextView.setText(eventTime);
            }

            // Afficher le budget
            TextView budgetTextView = findViewById(R.id.budget);
            if (budgetTextView != null && budget != null) {
                budgetTextView.setText(budget);
            }

            // Charger l'image
            ImageView eventImageView = findViewById(R.id.event_image);
            if (imageUrl != null) {
                Glide.with(this)
                        .load(imageUrl)
                        .into(eventImageView);
            }
        }

        // Boutons

        invite = findViewById(R.id.addpersone);



        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Envoyer une invitation", Toast.LENGTH_SHORT).show();
                Intent hometIntent = new Intent(getApplicationContext(), Email.class);
                startActivity(hometIntent);
                finish();
            }
        });
    }
}
