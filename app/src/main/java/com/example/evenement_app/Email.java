/*
package com.example.evenement_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Email extends AppCompatActivity {
    EditText sujet, content,email;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        sujet=findViewById(R.id.sujet);
        content=findViewById(R.id.content);
        email=findViewById(R.id.to_email);
        button=findViewById(R.id.SendEmail);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject, content1, email1;
                subject=sujet.getText().toString();
                content1=content.getText().toString();
                email1=email.getText().toString();
                if(subject.equals("") && content1.equals("")&& email1.equals("")){
                    Toast.makeText(Email.this, "ALL FIELDS ARE REQUIRED ", Toast.LENGTH_SHORT).show();
                }else {
                    sendemail(subject,content1,email1);
                }




            }
        });
    }

    public void sendemail(String subject, String content,String Email){
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{Email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"choose email client : "));
    }
}*/
package com.example.evenement_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*
public class Email extends AppCompatActivity {
    EditText sujet, content, email;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        sujet = findViewById(R.id.sujet);
        content = findViewById(R.id.content);
        email = findViewById(R.id.to_email);
        button = findViewById(R.id.SendEmail);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = sujet.getText().toString();
                String content1 = content.getText().toString();
                String email1 = email.getText().toString();
                if (subject.equals("") || content1.equals("") || email1.equals("")) {
                    Toast.makeText(Email.this, "TOUS LES CHAMPS SONT REQUIS", Toast.LENGTH_SHORT).show();
                } else {
                    sendemail(subject, content1, email1);
                    Intent hometIntent = new Intent(getApplicationContext(), ListEvent.class);
                    startActivity(hometIntent);
                    finish();
                }
            }
        });
    }

    public void sendemail(String subject, String content, String Email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{Email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        // Corps de l'e-mail avec des liens vers une page Web pour répondre au formulaire
        String messageBody = "Bonjour,\n\n" +
                "Merci de confirmer votre présence    :\n\n" +
                "https://docs.google.com/forms/d/e/1FAIpQLSdwSCAEi0diF7W8cCSBcNdwseL6qSJxHMpZwEj7I_dZgyOStg/viewform?usp=sf_link";

        intent.putExtra(Intent.EXTRA_TEXT, messageBody);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Choisissez le client de messagerie : "));
    }

}
*/
public class Email extends AppCompatActivity {

    EditText sujet, content, email;
    Button button;
    String eventName; // Variable pour stocker le nom de l'événement

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        sujet = findViewById(R.id.sujet);
        content = findViewById(R.id.content);
        email = findViewById(R.id.to_email);
        button = findViewById(R.id.SendEmail);

        // Récupérer le nom de l'événement passé depuis EventDetails
        Intent intent = getIntent();
        if (intent != null) {
            eventName = intent.getStringExtra("eventName");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = sujet.getText().toString();
                String content1 = content.getText().toString();
                String email1 = email.getText().toString();
                if (subject.equals("") || content1.equals("") || email1.equals("")) {
                    Toast.makeText(Email.this, "TOUS LES CHAMPS SONT REQUIS", Toast.LENGTH_SHORT).show();
                } else {
                    // Inclure le nom de l'événement dans le sujet de l'e-mail
                    subject = "Invitation à l'événement : " + eventName;

                    sendemail(subject, content1, email1);
                    Intent hometIntent = new Intent(getApplicationContext(), ListEvent.class);
                    startActivity(hometIntent);
                    finish();
                    Toast.makeText(Email.this, "Invitation envoyer ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void sendemail(String subject, String content, String Email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{Email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        // Corps de l'e-mail avec des liens vers une page Web pour répondre au formulaire
        String messageBody = "Bonjour,\n\n" +
                "Merci de confirmer votre présence    :\n\n" +
                "https://docs.google.com/forms/d/e/1FAIpQLSdwSCAEi0diF7W8cCSBcNdwseL6qSJxHMpZwEj7I_dZgyOStg/viewform?usp=sf_link";

        intent.putExtra(Intent.EXTRA_TEXT, messageBody);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Choisissez le client de messagerie : "));
    }
}
