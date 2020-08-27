package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class TransDatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_datos);

        Button button = (Button)findViewById(R.id.idconvertir);
    }

    public void unidades(View v){
        try{
            TextView tempVal=(TextView)findViewById(R.id.edtCantidad);
            Double cantidad3=Double.parseDouble(tempVal.getText().toString());

            Spinner spn=(Spinner)findViewById(R.id.cboDato);
            Integer de =spn.getSelectedItemPosition();

            Spinner spna=(Spinner)findViewById(R.id.cboDato2);
            Integer a =spna.getSelectedItemPosition();

            Double[]unidades={1.00, 0.125, 0.001, 0.000125,0.000001,1.25e-7,1e-9,1.25e-10,1e-12,1.25e-13};

            Double valor4=unidades[a]/unidades[de]*cantidad3;
            tempVal=(TextView)findViewById(R.id.txtRespuesta);
            tempVal.setText("Respuesta:"+valor4);
        }catch(Exception error){
            TextView tempVal = (TextView) findViewById(R.id.txtRespuesta);
            tempVal.setText("Ingrese la Cantidad a Convertir ");
        }

    }
}