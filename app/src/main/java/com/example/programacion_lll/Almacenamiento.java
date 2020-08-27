package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Almacenamiento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenamiento);

        Button button = (Button)findViewById(R.id.idconvertir);
    }

    public void almacenamiento(View v){
        try{
            TextView tempVal=(TextView)findViewById(R.id.edtCantidad);
            Double cantidad3=Double.parseDouble(tempVal.getText().toString());

            Spinner spn=(Spinner)findViewById(R.id.cboalma);
            Integer de =spn.getSelectedItemPosition();

            Spinner spna=(Spinner)findViewById(R.id.cboalma2);
            Integer a =spna.getSelectedItemPosition();

            Double[]unidades={1.00, 0.13, 0.001, 1e-6, 1e-9, 1e-12, 1e-15, 0.0001, 1.25e-13,1.25e-10};

            Double valor4=unidades[a]/unidades[de]*cantidad3;
            tempVal=(TextView)findViewById(R.id.txtRespuesta);
            tempVal.setText("Respuesta:"+valor4);
        }catch(Exception error){
            TextView tempVal = (TextView) findViewById(R.id.txtRespuesta);
            tempVal.setText("Ingrese la Cantidad a Convertir ");
        }
    }
}