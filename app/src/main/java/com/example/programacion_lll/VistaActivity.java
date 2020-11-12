package com.example.programacion_lll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programacion_lll.cnxsqlite.Pedidos;
import com.example.programacion_lll.cnxsqlite.SQLiteDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class VistaActivity extends AppCompatActivity {
    SQLiteDB miDB;
    Cursor misPedidos;
    Pedidos pedidos;

    ArrayList<Pedidos> stringArrayList = new ArrayList<Pedidos>();
    ArrayList<Pedidos> copyStringArrayList = new ArrayList<Pedidos>();
    ListView listaPedidos;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista);

        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton AgregarPedidos = (FloatingActionButton) findViewById(R.id.btnNuevoPedido);
        AgregarPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarPedido("nuevo", new String[]{});
            }
        });
        ObtenerPedidos();
        BuscarPedidos();

    }

    private void BuscarPedidos() {
        final TextView tempVal = (TextView)findViewById(R.id.edtBuscar);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    stringArrayList.clear();
                    if (tempVal.getText().toString().trim().length() < 1) {
                        stringArrayList.addAll(copyStringArrayList);
                    } else {
                        for (Pedidos am : copyStringArrayList) {
                            String pedidos = am.getNumero_pedido();
                            if (pedidos.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())) {
                                stringArrayList.add(am);
                            }
                        }
                    }
                    Adaptador adapter = new Adaptador(getApplicationContext(),stringArrayList);
                    listaPedidos.setAdapter(adapter);
                    //  AdaptadorImagenes adaptadorImg = new AdaptadorImagenes(getApplicationContext(), stringArrayList);
                    //  listProduc.setAdapter(adaptadorImg);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
        stringArrayList.clear();
        listaPedidos = (ListView)findViewById(R.id.listPedidos);
        do {
            pedidos = new Pedidos(misPedidos.getString(0),misPedidos.getString(1), misPedidos.getString(2), misPedidos.getString(3), misPedidos.getString(4), misPedidos.getString(5));
            stringArrayList.add(pedidos);
        }while(misPedidos.moveToNext());
        Adaptador adapter = new Adaptador(getApplicationContext(),stringArrayList);
        listaPedidos.setAdapter(adapter);
        //  AdaptadorImagenes adaptadorImg = new AdaptadorImagenes(getApplicationContext(), stringArrayList);
        //  listProduc.setAdapter(adaptadorImg);

        copyStringArrayList.clear();
        copyStringArrayList.addAll(stringArrayList);
        registerForContextMenu(listaPedidos);
    }

    private void AgregarPedido(String accion, String[] dataPedidos) {
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion", accion);
        enviarParametros.putStringArray("dataAmigo", dataPedidos);
        Intent agregarPedi = new Intent(VistaActivity.this, PedidosActivity.class);
        agregarPedi.putExtras(enviarParametros);
        startActivity(agregarPedi);

    }

    //cerrar sesion
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ini,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_add:
                mAuth.signOut();
                startActivity(new Intent(VistaActivity.this, LoginActivity.class));
                finish();
                return true;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        misPedidos.moveToPosition(adapterContextMenuInfo.position);
        menu.setHeaderTitle(misPedidos.getString(1));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnxAgregar:
                AgregarPedido("nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataNota = {
                        misPedidos.getString(0),//idPedioo
                        misPedidos.getString(1),//nuemeroPedido
                        misPedidos.getString(2),//nombreciente
                        misPedidos.getString(3),//direccion
                        misPedidos.getString(4)//pedidos


                };
                AgregarPedido("modificar", dataNota);
                return true;

            case R.id.mnxEliminar:
                AlertDialog eliminarNotas = eliminarPedidos();
                eliminarNotas.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }
    AlertDialog eliminarPedidos(){
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(VistaActivity.this);
        confirmacion.setTitle(misPedidos.getString(1));
        confirmacion.setMessage("Esta seguro de eliminar el pedido?");
        confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miDB.MantenimientoPedidos("eliminar",new String[]{misPedidos.getString(0)});
                ObtenerPedidos();
                Toast.makeText(getApplicationContext(), "Pedido eliminado con exito.",Toast.LENGTH_SHORT).show();
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
}