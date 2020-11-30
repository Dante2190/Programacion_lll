package com.example.programacion_lll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Region;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programacion_lll.cxnsqlite.Pedidos;
import com.example.programacion_lll.cxnsqlite.SQLiteDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewAtivity extends AppCompatActivity {
    SQLiteDB miBD;
    Cursor misPedidos;
    Pedidos pedidos;
    Pedidos pSelected;
    String nombre_cliente;

    EditText numPedido, nomCliente, direccion, pedids;

    JSONArray datosJSON; // para guardar los datos que vienen del servidor en formato
    JSONObject jsonObject;
    Bundle parametros = new Bundle();
    int posicion = 0;


    // ArrayList<Pedidos> stringArrayList = new ArrayList<Pedidos>();
    // ArrayList<Pedidos> copyStringArrayList = new ArrayList<Pedidos>();

    private ArrayList<Pedidos> listPedidos = new ArrayList<Pedidos>();
    ArrayAdapter<Pedidos> arrayAdapterPedidos;

    ListView listaPedidos;

    private FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtivity);



        listaPedidos = findViewById(R.id.listPedidos);



        FloatingActionButton btnAgregar = (FloatingActionButton) findViewById(R.id.btnNuevoPedido);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarPedidos("nuevo", new String[]{});
            }
        });

        IniciarFirebase();
        ListarPedidos();
        //  ObtenerPedidos();
        //  BuscarPedidos();

     /*   listaPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // pedidos = (Pedidos) parent.getItemAtPosition(position);
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("usuario", "Juan Perez");
                    bundle.putString("to", "12345678");

                    Intent intent = new Intent(getApplicationContext(), PedidosActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error al seleccionar el usuario a chatear: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });*/




      /*  listaPedidos.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Agregar").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        startActivity(new Intent(ViewAtivity.this, PedidosActivity.class));
                        return false;
                    }
                });

                menu.add("Editar").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                     /*   FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference1 = firebaseDatabase.getReference("Pedidos");
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                   // Pedidos pedidos = new Pedidos();

                                    String key = dataSnapshot1.getKey();
                                    Log.d("res1", "onCreate: key :" + key);
                                    if(pedidos.getId() == key){
                                        String direccion = dataSnapshot1.child("direccion").getValue(String.class);
                                        String nombre_cliente =dataSnapshot1.child("numero_cliente").getValue(String.class);
                                        String numero_pedido = dataSnapshot1.child("numero_pedido").getValue(String.class);
                                        String pedido = dataSnapshot1.child("Pedidos").getValue(String.class);
                                        String urlImg = dataSnapshot1.child("urlImg").getValue(String.class);
                                        // Log.d("res2", "onDataChange: email: " + email);
                                    }

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });*/


                /*        return false;
                    }
                });

                menu.add("Eliminar").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                      /* Query query = databaseReference.orderByChild("nombre_cliente").equalTo(nombre_cliente);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                    dataSnapshot1.getRef().removeValue();
                                    Toast.makeText(ViewAtivity.this, "exito al eliminar ", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });*/
                   /*     Pedidos pedidos = new Pedidos();
                        Query query = databaseReference.child("Pedidos").orderByChild("Pedidos").equalTo((pedidos.getId()));
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                                    objeto.getRef().removeValue();
                                }
                                Toast.makeText(ViewAtivity.this, "Se elimino el usuario", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });*/



        //      return false;
        //   }
        //  });
        //  }
        // });*/
    }

    // iniciar firebase
    private void IniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void ListarPedidos() {
        databaseReference.child("Pedidos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPedidos.clear();

                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Pedidos p = objSnaptshot.getValue(Pedidos.class);
                    listPedidos.add(p);

                    AdaptadorImagenes adaptadorImg = new AdaptadorImagenes(getApplicationContext(), listPedidos);
                    listaPedidos.setAdapter(adaptadorImg);

                    // arrayAdapterPedidos = new ArrayAdapter<Pedidos>(ViewAtivity.this, android.R.layout.simple_list_item_1,listPedidos);
                    // listaPedidos.setAdapter(arrayAdapterPedidos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
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
                            String numeroP = am.getNumero_pedido();
                            String nombre = am.getNombre_cliente();
                            String direccion = am.getDirecion();
                            if (numeroP.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase()) ||
                                    nombre.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase()) ||
                                    direccion.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())) {
                                stringArrayList.add(am);
                            }
                        }
                    }
                    AdaptadorImagenes adaptadorImg = new AdaptadorImagenes(getApplicationContext(), stringArrayList);
                    listPedidos.setAdapter(adaptadorImg);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }*/

  /*  private void ObtenerPedidos() {

        miBD = new SQLiteDB(getApplicationContext(), "", null, 1);
        misPedidos = miBD.MantenimientoPedidos("consultar", null);
        if( misPedidos.moveToFirst() ){ //hay registro en la BD que mostrar
            mostrarDatosProductos();
        } else{ //No tengo registro que mostrar.
            Toast.makeText(getApplicationContext(), "No hay registros de amigos que mostrar",Toast.LENGTH_LONG).show();
            AgregarPedidos("nuevo", new String[]{});
        }
    }*/

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
                //  EliminarPedFire();
                //   AlertDialog eliminarPedidos = EliminarPedidos();
                //    eliminarPedidos.show();
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

  /*  AlertDialog EliminarPedidos(){
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
    }*/


  /*  void mostrarDatosProductos(){
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
    }*/

    public void EliminarPedFire(){
        Pedidos p = new Pedidos();
        p.setId(p.getId());
        databaseReference.child("Pedidos").child(p.getId()).removeValue();
    }
}