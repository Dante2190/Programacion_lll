package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Monedas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monedas);

        Button btnCover = (Button) findViewById(R.id.idconvertir);
    }

    public void monedas(View v) {
        try {
            TextView tempVal = (TextView) findViewById(R.id.edtCantidad);
            Double cantidad = Double.parseDouble(tempVal.getText().toString());

            Spinner spn = (Spinner) findViewById(R.id.cboPaises);
            Integer de = spn.getSelectedItemPosition();

            Spinner spna = (Spinner) findViewById(R.id.cboPaises2);
            Integer a = spna.getSelectedItemPosition();

            Double[] mundial = {0.85, 9.98750, 0.763786, 21.9740, 3834.30, 793.132, 42.9228, 594.669, 6956.43, 1.0};

            Double valor = mundial[a] / mundial[de] * cantidad;
            tempVal = (TextView) findViewById(R.id.txtRespuesta);
            tempVal.setText("Respuesta:" + valor);
        } catch (Exception error) {
            TextView tempVal = (TextView) findViewById(R.id.txtRespuesta);
            tempVal.setText("Ingrese la Cantidad a Convertir ");
        }
    }
}