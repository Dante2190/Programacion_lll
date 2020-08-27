package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Volumen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volumen);

        Button button = (Button)findViewById(R.id.idconvertir);
    }

    public void volumen(View v) {
        try {
            TextView tempVal = (TextView) findViewById(R.id.edtCantidad);
            Double cantidad = Double.parseDouble(tempVal.getText().toString());

            Spinner spn = (Spinner) findViewById(R.id.cboVol);
            Integer de = spn.getSelectedItemPosition();

            Spinner spna = (Spinner) findViewById(R.id.cboVol2);
            Integer a = spna.getSelectedItemPosition();

            Double[] mundial = {1.00, 0.001, 1000.0, 0.219969, 1.75975,3.51951,61.0237,0.0353147,67.628,0.264172};

            Double valor = mundial[a] / mundial[de] * cantidad;
            tempVal = (TextView) findViewById(R.id.txtRespuesta);
            tempVal.setText("Respuesta:" + valor);
        } catch (Exception error) {
            TextView tempVal = (TextView) findViewById(R.id.txtRespuesta);
            tempVal.setText("Ingrese la Cantidad a Convertir ");
        }
    }
}