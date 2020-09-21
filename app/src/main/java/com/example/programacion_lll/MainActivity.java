package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void area(View v) {
        try {
            TextView tempVal = (TextView) findViewById(R.id.edtCantidad2);
            Double cantidad3 = Double.parseDouble(tempVal.getText().toString());

            Spinner spn = (Spinner) findViewById(R.id.cboArea);
            Integer de = spn.getSelectedItemPosition();

            Spinner spna = (Spinner) findViewById(R.id.cboArea2);
            Integer a = spna.getSelectedItemPosition();

            Double[] unidades = {1.00, 0.132230, 0.111111, 0.092903, 0.000147, 0.000013, 0.000009};

            Double valor4 = unidades[a] / unidades[de] * cantidad3;
            tempVal = (TextView) findViewById(R.id.txtRespuesta2);
            tempVal.setText("Respuesta:" + valor4);
        } catch (Exception error) {
            TextView tempVal = (TextView) findViewById(R.id.txtRespuesta2);
            tempVal.setText("Ingrese la Cantidad a Convertir ");
        }

    }
}