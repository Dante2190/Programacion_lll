package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Agregar_Agenda extends AppCompatActivity {
    String accion = "nuevo";
    String id = "";
    String rev = "";
    JSONObject datosJSON;
    String Resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__agenda);

        Bundle parametros = getIntent().getExtras();
        accion = parametros.getString("accion");

        if (accion.equals("modificar")){
            try {
                datosJSON = new JSONObject(parametros.getString("valores"));

                TextView temp = (TextView) findViewById(R.id.editCodigo);
                temp.setText(datosJSON.getString("codigo"));

                temp = (TextView) findViewById(R.id.editNombre);
                temp.setText(datosJSON.getString("nombre"));

                temp = (TextView) findViewById(R.id.editDirreccion);
                temp.setText(datosJSON.getString("direccion"));

                temp = (TextView) findViewById(R.id.editTelefono);
                temp.setText(datosJSON.getString("telefono"));

                temp = (TextView) findViewById(R.id.editDui);
                temp.setText(datosJSON.getString("dui"));

                id = datosJSON.getString("_id");
                rev = datosJSON.getString("_rev");
            }catch (Exception ex){
                Toast.makeText(Agregar_Agenda.this, "Error al recuperar datos: "+ex.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        }

        Button btn = (Button)findViewById(R.id.btnGuardar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView temp = (TextView) findViewById(R.id.editCodigo);
                String codigo = temp.getText().toString();

                temp = (TextView) findViewById(R.id.editNombre);
                String nombre = temp.getText().toString();

                temp = (TextView) findViewById(R.id.editDirreccion);
                String direccion  = temp.getText().toString();

                temp = (TextView) findViewById(R.id.editTelefono);
                String telefono = temp.getText().toString();

                temp = (TextView) findViewById(R.id.editDui);
                String dui = temp.getText().toString();

                JSONObject miData = new JSONObject();
                try {
                    if (accion.equals("modidficar")){
                        miData.put("_id", id);
                        miData.put("_rev", rev);
                    }
                    miData.put("codigo", codigo);
                    miData.put("nombre", nombre);
                    miData.put("direccion", direccion);
                    miData.put("telefono", telefono);
                    miData.put("dui", dui);
                }catch (Exception ex){
                    Toast.makeText(Agregar_Agenda.this, "Error al guardar: "+ex.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        FloatingActionButton btnRegresa = (FloatingActionButton)findViewById(R.id.btnRegresar);
        btnRegresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regresar = new Intent(Agregar_Agenda.this, MainActivity.class);
                startActivity(regresar);
            }
        });
    }

    private class EnviarDatos extends AsyncTask<String, String, String>{
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();

            String JsonResponse = null;
            String JsonDATA = params[0];
            BufferedReader reader = null;

            try {
                URL url = new URL("http//10.0.2.2:5984/db_agenda/");
                urlConnection = (HttpURLConnection) url.openConnection();
                //aqui
            }catch (Exception ex){

            }
            return null;
        }
    }
}