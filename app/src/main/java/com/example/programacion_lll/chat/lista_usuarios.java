package com.example.programacion_lll.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.programacion_lll.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class lista_usuarios extends AppCompatActivity {

    DatabaseReference mDatabaseReference;
    ListView ltsUsuarios;
    JSONArray datosJSONArray = new JSONArray();
    JSONObject datosJSONObject;
    MyFirebaseInstanceIdService myFirebaseInstanceIdService = new MyFirebaseInstanceIdService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        ltsUsuarios = findViewById(R.id.ltsUsuarios);
        mostrarListadoUsuarios();

        ltsUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("user", datosJSONArray.getJSONObject(position).getString("user"));
                    bundle.putString("to", datosJSONArray.getJSONObject(position).getString("to"));
                    bundle.putString("from", datosJSONArray.getJSONObject(position).getString("from"));
                    bundle.putString("urlFoto", datosJSONArray.getJSONObject(position).getString("urlFoto"));
                    bundle.putString("urlFotoFirestore", datosJSONArray.getJSONObject(position).getString("urlFotoFirestore"));

                    Intent intent = new Intent(getApplicationContext(), chats.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error al seleccionar el usuario a chatear: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void mostrarListadoUsuarios(){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("usuariosChat");
        mDatabaseReference.orderByChild("token").equalTo(myFirebaseInstanceIdService.miToken).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    if( snapshot.getChildrenCount()<=0 ){
                        registrarUsuario();
                        finish();
                    }
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error al saber si estoy registrado: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
                    registrarUsuario();
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    ArrayList<usuarios> stringArrayList = new ArrayList<usuarios>();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        usuarios user = dataSnapshot.getValue(usuarios.class);
                        stringArrayList.add(user);

                        datosJSONObject = new JSONObject();
                        datosJSONObject.put("user", user.getUserName());
                        datosJSONObject.put("to", user.getToken());
                        datosJSONObject.put("from", myFirebaseInstanceIdService.miToken);
                        datosJSONObject.put("urlFoto", user.getUrlFoto());
                        datosJSONObject.put("urlFotoFirestore", user.getUrlFotoFirestore());
                        datosJSONArray.put(datosJSONObject);
                    }
                    adaptadorImagenes adaptadorImg = new adaptadorImagenes(getApplicationContext(), stringArrayList);
                    ltsUsuarios.setAdapter(adaptadorImg);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error al recuperar los amigos: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void registrarUsuario(){
        Intent intent = new Intent(getApplicationContext(), RegisterUserActivity.class);
        startActivity(intent);
    }
}