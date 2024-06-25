package com.example.evenement_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Menu extends AppCompatActivity {
     BottomNavigationView bottomNavigationView;
     NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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

        //menu navigation
        navigationView=findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home){
                Intent hometIntent = new Intent(getApplicationContext(), Accueil.class);
                startActivity(hometIntent);
                finish();
                return true;
                }
                /*if (itemId == R.id.recherche){
                Intent hometIntent = new Intent(getApplicationContext(), Recherches.class);
                startActivity(hometIntent);
                finish();
                return true;
                }*/
                if (itemId == R.id.tache){
                Intent hometIntent = new Intent(getApplicationContext(), task.class);
                startActivity(hometIntent);
                finish();
                return true;
                }
                /*if (itemId == R.id.notification){
                Intent hometIntent = new Intent(getApplicationContext(), notification.class);
                startActivity(hometIntent);
                finish();
                return true;
                }*/
                if (itemId == R.id.invite){
                Intent hometIntent = new Intent(getApplicationContext(), Invites.class);
                startActivity(hometIntent);
                finish();
                return true;
                }
                if (itemId == R.id.Ajouter_post){
                Intent hometIntent = new Intent(getApplicationContext(), Ajouter_Acceuil.class);
                startActivity(hometIntent);
                finish();
                return true;
                }
                if (itemId == R.id.Budget){
                Intent hometIntent = new Intent(getApplicationContext(), Budget1.class);
                startActivity(hometIntent);
                finish();
                return true;
                }
                if (itemId == R.id.calendrier){
                Intent hometIntent = new Intent(getApplicationContext(), Calendrier.class);
                startActivity(hometIntent);
                finish();
                return true;
                }
                if (itemId == R.id.formulair){
                Intent hometIntent = new Intent(getApplicationContext(), formulaire.class);
                startActivity(hometIntent);
                finish();
                return true;
                }
                 if (itemId == R.id.ListEvent) {
                 Intent hometIntent = new Intent(getApplicationContext(), ListEvent.class);
                 startActivity(hometIntent);
                 finish();
                 return true;
                }
                if (itemId == R.id.logout){
                Intent hometIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(hometIntent);
                finish();
                return true;
                }

                return false;
            }
        });
    }
}