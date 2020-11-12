package com.example.programacion_lll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Region;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

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

        FloatingActionButton btnAgregar = (FloatingActionButton)findViewById(R.id.btnNuevoPedido);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // agregarAmigo("nuevo", new String[]{});
            }
        });

        ObtenerPedidos();
       // BuscarPedidos();

    }

    private void ObtenerPedidos() {
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
                startActivity(new Intent(ViewAtivity.this, LoginActivity.class));
                finish();
                return true;
        }
        return true;
    }
}