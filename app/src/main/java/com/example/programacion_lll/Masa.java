package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Masa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masa);


        Button button =(Button)findViewById(R.id.idconvertir);
    }

    public void masa(View v){
        try{
            TextView tempVal=(TextView)findViewById(R.id.edtCantidad);
            Double cantidad=Double.parseDouble(tempVal.getText().toString());

            Spinner spn=(Spinner)findViewById(R.id.cboMasa);
            Integer de =spn.getSelectedItemPosition();

            Spinner spna=(Spinner)findViewById(R.id.cboMasa2);
            Integer a =spna.getSelectedItemPosition();
            Double []pesos={0.4536,453.6,16.00,45359.27,453592.37,4535.92,4.5359,45.3592,0.04,1.00};

            Double valor2=pesos[a]/pesos[de]*cantidad;
            tempVal=(TextView)findViewById(R.id.txtRespuesta);
            tempVal.setText("Respuesta:"+valor2);
        }catch(Exception error){
            TextView tempVal = (TextView) findViewById(R.id.txtRespuesta);
            tempVal.setText("Ingrese la Cantidad a Convertir ");
        }

    }
}