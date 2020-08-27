package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Longitud extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longitud);

        Button button = (Button)findViewById(R.id.idconvertir);
    }

    public void longitud(View v){
        try{
            TextView tempVal=(TextView)findViewById(R.id.edtCantidad);
            Double cantidad=Double.parseDouble(tempVal.getText().toString());

            Spinner spn=(Spinner)findViewById(R.id.cboLongi);
            Integer de =spn.getSelectedItemPosition();

            Spinner spna=(Spinner)findViewById(R.id.cboLongi2);
            Integer a =spna.getSelectedItemPosition();

            Double []longitud={0.0010,0.01,0.100,10.000,100.00,1000.00,39.370,3.280,1.0936,1.00};

            Double valor3=longitud[a]/longitud[de]*cantidad;
            tempVal=(TextView)findViewById(R.id.txtRespuesta);
            tempVal.setText("Respuesta:"+valor3);
        }catch(Exception error){
            TextView tempVal = (TextView) findViewById(R.id.txtRespuesta);
            tempVal.setText("Ingrese la Cantidad a Convertir ");
        }

    }
}