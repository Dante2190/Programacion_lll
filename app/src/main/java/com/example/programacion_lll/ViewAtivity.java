package com.example.programacion_lll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Region;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.programacion_lll.cxnsqlite.Pedidos;
import com.example.programacion_lll.cxnsqlite.SQLiteDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ViewAtivity extends AppCompatActivity {
    SQLiteDB miBD;
    Cursor misPedidos;
    Pedidos pedidos;
    ArrayList<Pedidos> stringArrayList = new ArrayList<Pedidos>();
    ArrayList<Pedidos> copyStringArrayList = new ArrayList<Pedidos>();
    ListView listPedidos;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtivity);

        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton btnAgregar = (FloatingActionButton) findViewById(R.id.btnNuevoPedido);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarPedidos("nuevo", new String[]{});
            }
        });

        ObtenerPedidos();
        // BuscarPedidos();

    }

    private void ObtenerPedidos() {

        miBD = new SQLiteDB(getApplicationContext(), "", null, 1);
        misPedidos = miBD.MantenimientoPedidos("consultar", null);
        if( misPedidos.moveToFirst() ){ //hay registro en la BD que mostrar
            mostrarDatosProductos();
        } else{ //No tengo registro que mostrar.
            Toast.makeText(getApplicationContext(), "No hay registros de amigos que mostrar",Toast.LENGTH_LONG).show();
            AgregarPedidos("nuevo", new String[]{});
        }
    }

    // menu cerrar sesion
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ini, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_add:
                mAuth.signOut();
                startActivity(new Intent(ViewAtivity.this, LoginActivity.class));
                finish();
                return true;
        }
        return true;
    }

    // menu  2
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        misPedidos.moveToPosition(adapterContextMenuInfo.position);
        menu.setHeaderTitle(misPedidos.getString(1));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnxAgregar:
                AgregarPedidos("nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataProducto = {
                        misPedidos.getString(0),//idProducto
                        misPedidos.getString(1),//codigo
                        misPedidos.getString(2),//descripcion
                        misPedidos.getString(3),//marca
                        misPedidos.getString(4), //presentacion
                        misPedidos.getString(5)  //urlImg
                };
                AgregarPedidos("modificar", dataProducto);
                return true;

            case R.id.mnxEliminar:
                AlertDialog eliminarPedidos = EliminarPedidos();
                eliminarPedidos.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    void AgregarPedidos(String accion, String[] dataAmigo) {
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion", accion);
        enviarParametros.putStringArray("dataAmigo", dataAmigo);
        Intent agregarAmigos = new Intent(ViewAtivity.this, PedidosActivity.class);
        agregarAmigos.putExtras(enviarParametros);
        startActivity(agregarAmigos);

    }

    AlertDialog EliminarPedidos(){
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(ViewAtivity.this);
        confirmacion.setTitle(misPedidos.getString(1));
        confirmacion.setMessage("Esta seguro de eliminar el producto?");
        confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miBD.MantenimientoPedidos("eliminar",new String[]{misPedidos.getString(0)});
                ObtenerPedidos();
                Toast.makeText(getApplicationContext(), "Producto eliminado con exito.",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Eliminacion cancelada por el usuario.",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        return confirmacion.create();
    }


    void mostrarDatosProductos(){
        stringArrayList.clear();
        listPedidos = (ListView)findViewById(R.id.listPedidos);
        do {
            pedidos = new Pedidos(misPedidos.getString(0),misPedidos.getString(1), misPedidos.getString(2), misPedidos.getString(3), misPedidos.getString(4), misPedidos.getString(5));
            stringArrayList.add(pedidos);
        }while(misPedidos.moveToNext());
        AdaptadorImagenes adaptadorImg = new AdaptadorImagenes(getApplicationContext(), stringArrayList);
        listPedidos.setAdapter(adaptadorImg);

        copyStringArrayList.clear();
        copyStringArrayList.addAll(stringArrayList);
        registerForContextMenu(listPedidos);
    }
}