package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programacion_lll.cnxsqlite.SQLiteDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AgregarPedidos extends AppCompatActivity {
    SQLiteDB miDB;
    String accion ="nuevo";
    String idPedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pedidos);

        FloatingActionButton btnPedido = ( FloatingActionButton) findViewById(R.id.btnGuardarPedidos);
        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarDatosPedidos();
                finish();
            }
        });

        FloatingActionButton btnPet = ( FloatingActionButton) findViewById(R.id.btnMostrar);
        btnPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarListaPedidos();
            }
        });
        MostrarDatos();
    }

    private void MostrarDatos() {
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");
            if (accion.equals("modificar")) {
                String[] dataPedidos = recibirParametros.getStringArray("dataAmigo");

                idPedido = dataPedidos[0];

                TextView tempVal = (TextView) findViewById(R.id.edtNumeroP);
                tempVal.setText(dataPedidos[1]);

                tempVal = findViewById(R.id.edtNombre);
                tempVal.setText(dataPedidos[2]);

                tempVal = findViewById(R.id.edtDireccion);
                tempVal.setText(dataPedidos[3]);

                tempVal = findViewById(R.id.edtPedidos);
                tempVal.setText(dataPedidos[4]);


            }
        } catch (Exception ex) {
            ///
        }
    }


    private void MostrarListaPedidos() {
        startActivity(new Intent(AgregarPedidos.this,MainActivity.class));
    }

    private void GuardarDatosPedidos() {

        TextView tempVal = findViewById(R.id.edtNumeroP);
        String numero = tempVal.getText().toString();

         tempVal = findViewById(R.id.edtNombre);
        String nombre = tempVal.getText().toString();

         tempVal = findViewById(R.id.edtDireccion);
        String direccion = tempVal.getText().toString();

         tempVal = findViewById(R.id.edtPedidos);
        String pedidos = tempVal.getText().toString();

        String[] data = {idPedido, numero, nombre, direccion, pedidos};

        miDB = new SQLiteDB(getApplicationContext(), "", null, 1);
        miDB.MantenimientoPedidos(accion, data);

        Toast.makeText(getApplicationContext(), "Registro de Pedidos insertado con exito", Toast.LENGTH_LONG).show();
        MostrarListaPedidos();

    }
}