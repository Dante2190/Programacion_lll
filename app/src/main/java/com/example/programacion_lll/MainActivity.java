
package com.example.programacion_lll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.programacion_lll.cnxsqlite.Pedidos;
import com.example.programacion_lll.cnxsqlite.SQLiteDB;
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
    SQLiteDB miDB;
    Cursor misPedidos;
    Pedidos pedidos;

    ArrayList<Pedidos> stringArrayList = new ArrayList<Pedidos>();
    ArrayList<Pedidos> copyStringArrayList = new ArrayList<Pedidos>();
    ListView listNota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton AgregarPedidos = (FloatingActionButton) findViewById(R.id.btnNuevoPedido);
        AgregarPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarPedido("nuevo", new String[]{});
            }
        });
      ObtenerPedidos();
     //   BuscarPedidos();

    }

    private void BuscarPedidos() {
    }

    private void ObtenerPedidos() {
        miDB = new SQLiteDB(getApplicationContext(), "", null, 1);
        misPedidos = miDB.MantenimientoPedidos("consultar", null);
        if( misPedidos.moveToFirst() ){ //hay registro en la BD que mostrar
            mostrarDatosPedidos();
        } else{ //No tengo registro que mostrar.
            Toast.makeText(getApplicationContext(), "No hay registros de notas que mostrar",Toast.LENGTH_LONG).show();
            AgregarPedido("nuevo", new String[]{});
        }
    }

    private void mostrarDatosPedidos() {
    }

    private void AgregarPedido(String accion, String[] dataPedidos) {
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion",accion);
        enviarParametros.putStringArray("dataAmigo",dataPedidos);
        Intent agregarPedi = new Intent(MainActivity.this, AgregarPedidos.class);
        agregarPedi.putExtras(enviarParametros);
        startActivity(agregarPedi);

    }


}

