package com.example.programacion_lll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    JSONArray datosJSON;
    JSONObject jsonObject;
    Bundle parametros = new Bundle();
    int posicion = 0;
    InputStreamReader isReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ObtenerDatos myAsync = new ObtenerDatos();
        myAsync.execute();

        FloatingActionButton btn = (FloatingActionButton)findViewById(R.id.btnAgregar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parametros.putString("accion" , "nuevo");
                Nueva_Tienda();
            }
        });
    }

    public void Nueva_Tienda() {
        Intent agregar = new Intent(MainActivity.this, Agregar_Productos.class);
        agregar.putExtras(parametros);
        startActivity(agregar);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        try {
            datosJSON.getJSONObject(info.position);
            menu.setHeaderTitle(datosJSON.getJSONObject(info.position).getJSONObject("value").getString("marca").toString());
            posicion = info.position;
        }catch (Exception ex) {
            Toast.makeText(MainActivity.this, "Error pasa algo raro", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnxAgregar:
                parametros.putString("accion", "nuevo");
                Nueva_Tienda();
                return true;
            case R.id.mnxModificar:
                parametros.putString("accion", "modificar");
                try {
                    parametros.putString("valores", datosJSON.getJSONObject(posicion).getJSONObject("value").toString());
                    Nueva_Tienda();
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "error pasa algo raro", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.mnxEliminar:
                JSONObject miData = new JSONObject();
                try {
                    miData.put("_id", datosJSON.getJSONObject(posicion).getJSONObject("value").getString("_rev"));
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "error al eliminar  ", Toast.LENGTH_SHORT).show();
                }
                EliminarDatos objEliminar = new EliminarDatos();
                objEliminar.execute(miData.toString());
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private class ObtenerDatos extends AsyncTask<Void, Void, String> {
        HttpURLConnection URLConnection;
        @Override
        protected String doInBackground(Void... params) {
            StringBuilder result = new StringBuilder();
            try{
                //oneccion al servidor
                URL url = new URL("http://192.168.1.15:5984/db_tienda/_design/tienda/_view/mi-tienda");
                URLConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(URLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }
            }catch (Exception ex){
                Log.e("Mi Error", "Error", ex);
                ex.printStackTrace();
            }finally {
                URLConnection.disconnect();
            }
            return result.toString();
        }
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try {
                jsonObject = new JSONObject(s);
                datosJSON= jsonObject.getJSONArray("rows");
                ListView ltsTienda = (ListView)findViewById(R.id.ltsTienda);

                final ArrayList<String> alTienda= new ArrayList<>();
                final ArrayAdapter<String> aaTienda = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, alTienda);
                ltsTienda.setAdapter(aaTienda);

                for (int i = 0; i < datosJSON.length(); i++){
                    alTienda.add(datosJSON.getJSONObject(i).getJSONObject("value").getString("marca").toString());

                }
                aaTienda.notifyDataSetChanged();
                registerForContextMenu(ltsTienda);
            }catch (Exception ex){
                Toast.makeText(MainActivity.this, "Error: q paso aqui  " + ex.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

    }
    private class EliminarDatos  extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();

            String JsonResponce = null;
            String JsonData = params[0];
            BufferedReader reader = null;

            try {
                //conexion al servidor
                String uri = "http://192.168.1.15:5984/db_tienda/"+
                        datosJSON.getJSONObject(posicion).getJSONObject("value").getString("_id")+"?rev="+
                        datosJSON.getJSONObject(posicion).getJSONObject("value").getString("_rev");
                URL url = new URL(uri);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("DELETE");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }
            }catch (Exception ex){
                Log.e("Mi Error", "Error", ex);
                ex.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("ok")){
                    Toast.makeText(MainActivity.this, "registro de eliminacion con exito", Toast.LENGTH_SHORT).show();
                    Intent regresar = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(regresar);
                }else {
                    Toast.makeText(MainActivity.this, "error al intentar eliminar el registro ", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception ex){
                Toast.makeText(MainActivity.this, "error al enviar a la red"+ ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}