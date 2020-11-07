package com.example.programacion_lll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programacion_lll.cnxsqlite.SQLiteDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AgregarPedidos extends AppCompatActivity {
    private EditText nPedido, edtNombre, edtDireccion, edtPedido;
    SQLiteDB miDB;
    String accion ="nuevo";
    String idPedido;

    private String numero = "";
    private String nombre = "";
    private String direccion = "";
    private String pedido = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pedidos);

        nPedido = (EditText)findViewById(R.id.edtNumeroP) ;
        edtNombre = (EditText)findViewById(R.id.edtNombre) ;
        edtDireccion = (EditText)findViewById(R.id.edtDireccion) ;
        edtPedido = (EditText)findViewById(R.id.edtPedidos) ;

        FloatingActionButton btnPedido = ( FloatingActionButton) findViewById(R.id.btnGuardarPedidos);
        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numero= nPedido.getText().toString();
                nombre= edtNombre.getText().toString();
                direccion=  edtDireccion.getText().toString();
                pedido = edtPedido.getText().toString();
                if (!numero.isEmpty() && !nombre.isEmpty() && direccion.isEmpty() && pedido.isEmpty() ) {
                    GuardarDatosPedidos();
                    finish();
                }else Toast.makeText(AgregarPedidos.this, "llene los campos por favor", Toast.LENGTH_SHORT).show();
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mi_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_save:
               GuardarDatosPedidos();
                return true;
            case R.id.icon_regresar:
                MostrarListaPedidos();
                return true;
        }
        return true;
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