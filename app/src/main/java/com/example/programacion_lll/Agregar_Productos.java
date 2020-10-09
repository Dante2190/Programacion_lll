package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class Agregar_Productos extends AppCompatActivity {
    String accion = "nuevo";
    String id = "";
    String rev = "";
    JSONObject datosJSON;
    String resp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__productos);

        Bundle parametros = getIntent().getExtras();
        accion = parametros.getString("accion");

        if (accion.equals("modificar")) {
            try {
                datosJSON = new JSONObject(parametros.getString("valores"));

                TextView temp = (TextView) findViewById(R.id.editCodigo);
                temp.setText(datosJSON.getString("codigo"));

                temp = (TextView) findViewById(R.id.editDescrip);
                temp.setText(datosJSON.getString("descripcion"));

                temp = (TextView) findViewById(R.id.editMarca);
                temp.setText(datosJSON.getString("marca"));

                temp = (TextView) findViewById(R.id.editPresen);
                temp.setText(datosJSON.getString("presentacion"));

                temp = (TextView) findViewById(R.id.editPrecio);
                temp.setText(datosJSON.getString("precio"));

                id = datosJSON.getString("_id");
                rev = datosJSON.getString("_rev");
            }catch (Exception ex){
                Toast.makeText(Agregar_Productos.this, "Error al recuperar datos: "+ex.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        }

        Button btn = (Button)findViewById(R.id.btnGuardar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView temp = (TextView) findViewById(R.id.editCodigo);
                String codigo = temp.getText().toString();

                temp = (TextView) findViewById(R.id.editDescrip);
                String descripcion = temp.getText().toString();

                temp = (TextView) findViewById(R.id.editMarca);
                String marca  = temp.getText().toString();

                temp = (TextView) findViewById(R.id.editPresen);
                String presentacion = temp.getText().toString();

                temp = (TextView) findViewById(R.id.editPrecio);
                String precio = temp.getText().toString();

                JSONObject miData = new JSONObject();

                try {
                    if (accion.equals("modificar")){
                        miData.put("_id", id);
                        miData.put("_rev", rev);
                    }
                    miData.put("codigo", codigo);
                    miData.put("descripcion", descripcion);
                    miData.put("marca", marca);
                    miData.put("presentacion", presentacion);
                    miData.put("precio", precio);

                    EnviarDatos objEnviar = new EnviarDatos();
                    objEnviar.execute(miData.toString());
                    // Validar();

                }catch (Exception ex){
                    Toast.makeText(Agregar_Productos.this, "Error al guardar: "+ex.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        FloatingActionButton btnRegresa = (FloatingActionButton)findViewById(R.id.btnRegresar);
        btnRegresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regresar = new Intent(Agregar_Productos.this, MainActivity.class);
                startActivity(regresar);
            }
        });

    }
    private class EnviarDatos extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();

            String JsonResponse = null;
            String JsonDATA = params[0];
            BufferedReader reader = null;

            try {
                URL url = new URL("http://192.168.1.15:5984/db_tienda/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(),"UTF-8"));
                writer.write(JsonDATA);
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();

                if (inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                resp = reader.toString();
                String inputLine;
                StringBuffer buffer = new StringBuffer();

                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");

                if (buffer.length() == 0){
                    return null;
                }

                JsonResponse = buffer.toString();
                Log.i(TAG, JsonResponse);
                return JsonResponse;

            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getBoolean("ok")){
                    Toast.makeText(Agregar_Productos.this, "registro almacenado con exito: ", Toast.LENGTH_LONG).show();
                    Intent regresar = new Intent(Agregar_Productos.this, MainActivity.class);
                    startActivity(regresar);
                }else {
                    Toast.makeText(Agregar_Productos.this, "error al intentar almacenar el registro: ", Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex){
                Toast.makeText(Agregar_Productos.this, "error al enviar ala red: "+ ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

}