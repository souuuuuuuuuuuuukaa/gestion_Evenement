package com.example.evenement_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText emailedit,passwordedit;
    private Button connexion;
    private FirebaseAuth mAuth;
    private ProgressBar progress;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailedit=findViewById(R.id.Email);
        passwordedit=findViewById(R.id.Password);
        connexion=findViewById(R.id.buttonLogin);
        progress=findViewById(R.id.progressBar1);



        connexion.setOnClickListener(v -> {
            progress.setVisibility(View.VISIBLE);
            String email,password;
            email=String.valueOf(emailedit.getText());
            password=String.valueOf(passwordedit.getText());
            if (TextUtils.isEmpty(email)){
                Toast.makeText(Login.this,"enter email",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(Login.this,"enter password",Toast.LENGTH_SHORT).show();
                return;
            }

            Task<AuthResult> authResultTask = mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progress.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                               /* Toast.makeText(Connexion.this, "Authentication success.",
                                        Toast.LENGTH_SHORT).show();*/
                                Intent intent = new Intent(getApplicationContext(),Accueil.class);
                                startActivity(intent);
                                finish();


                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        });
    }
}