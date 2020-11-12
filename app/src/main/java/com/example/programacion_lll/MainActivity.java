package com.example.programacion_lll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText edtNombre, edtEmail, edtContra;
    private Button registrar;
    private Button login;

    TextView textView;
    ProgressBar progressBar;

    private String name = "";
    private String email = "";
    private String password = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtNombre = (EditText)findViewById(R.id.editNombre);
        edtEmail = (EditText)findViewById(R.id.editEmail);
        edtContra = (EditText)findViewById(R.id.editcontra);

        textView = findViewById(R.id.textV);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

        login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        registrar = (Button) findViewById(R.id.btnRegistrar);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);

                name = edtNombre.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                password = edtContra.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    if (password.length() >= 6){
                        RegistrarUser();
                    }
                    else {
                        progressBar.setVisibility(View.INVISIBLE);
                        textView.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "la contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "debe llenar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void RegistrarUser() {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){


                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("password", password);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if (task2.isSuccessful()){
                                startActivity(new Intent(MainActivity.this, ViewAtivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "no se pudiieron crear los reegstross", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "no se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}