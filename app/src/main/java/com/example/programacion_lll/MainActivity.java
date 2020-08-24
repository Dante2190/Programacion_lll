package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonMo = (Button)findViewById(R.id.btnMonedas);
        Button buttonMasa = (Button)findViewById(R.id.btnMasa);
        Button buttonVolumen = (Button)findViewById(R.id.btnVolumen);
        Button buttonLongitud = (Button)findViewById(R.id.btnLongitud);
        Button buttonAlmac = (Button)findViewById(R.id.btnAlmacenamiento);
        Button buttoTiempo = (Button)findViewById(R.id.btnTiempo);
        Button buttoDatos = (Button)findViewById(R.id.btnTransDatos);

        buttonMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netxM = new Intent(MainActivity.this,Monedas.class);
                startActivity(netxM);
            }
        });

        buttonMasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netxM = new Intent(MainActivity.this,Masa.class);
                startActivity(netxM);
            }
        });

        buttonVolumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netxM = new Intent(MainActivity.this,Volumen.class);
                startActivity(netxM);
            }
        });

        buttonLongitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netxM = new Intent(MainActivity.this,Longitud.class);
                startActivity(netxM);
            }
        });

        buttonAlmac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netxM = new Intent(MainActivity.this,Almacenamiento.class);
                startActivity(netxM);
            }
        });

        buttoTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netxM = new Intent(MainActivity.this,Tiempo.class);
                startActivity(netxM);
            }
        });

        buttoDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netxM = new Intent(MainActivity.this,TransDatos.class);
                startActivity(netxM);
            }
        });

    }
}