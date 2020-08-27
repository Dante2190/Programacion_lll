package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Tiempo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo);

        Button button = (Button)findViewById(R.id.idconvertir);
    }

    public void tiempo(View v) {
        try {
            TextView tempVal = (TextView) findViewById(R.id.edtCantidad);
            Double cantidad = Double.parseDouble(tempVal.getText().toString());

            Spinner spn = (Spinner) findViewById(R.id.cboTime);
            Integer de = spn.getSelectedItemPosition();

            Spinner spna = (Spinner) findViewById(R.id.cboTime2);
            Integer a = spna.getSelectedItemPosition();

            Double[] mundial = {1.00, 0.0166667, 0.000277778, 1.1574e-5, 1.6534e-6,3.80508076156e-7, 3.17090410959293262e-8, 3.171e-9, 3.171e-10,1e+9 };

            Double valor = mundial[a] / mundial[de] * cantidad;
            tempVal = (TextView) findViewById(R.id.txtRespuesta);
            tempVal.setText("Respuesta:" + valor);
        } catch (Exception error) {
            TextView tempVal = (TextView) findViewById(R.id.txtRespuesta);
            tempVal.setText("Ingrese la Cantidad a Convertir ");
        }

    }
}