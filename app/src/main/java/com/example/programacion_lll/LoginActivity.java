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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtContra;
    private Button btnLogin;

    TextView textView;
    ProgressBar progressBar;

    private String email = "";
    private String password = "";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = findViewById(R.id.textV);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = (EditText)findViewById(R.id.editEmail);
        edtContra = (EditText)findViewById(R.id.editcontra);

        progressBar.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

        btnLogin = (Button) findViewById(R.id.btnIniciarSesion);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);

                email = edtEmail.getText().toString().trim();
                password = edtContra.getText().toString().trim();
                if (!email.isEmpty() && !password.isEmpty()){

                    LoginUsers();
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "complete los campos por favor crt", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void LoginUsers() {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, VistaActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "no se pudo iniciar sesion compruebe bien los datos wey ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}