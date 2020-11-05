package com.example.programacion_lll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programacion_lll.cnxsqlite.Pedidos;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    Context context;
    ArrayList<Pedidos> datos;
    LayoutInflater layoutInflater;
    Pedidos ped;

    public Adaptador(Context context, ArrayList<Pedidos> datos){
        this.context = context;
        try {
            this.datos = datos;
        }catch (Exception ex){}
    }
    @Override
    public int getCount() {
        try {
            return datos.size();
        }catch (Exception ex) {
            return 0;
        }
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.lista_pedidos, viewGroup, false);
        TextView textView = (TextView)itemView.findViewById(R.id.txtTitulo);
        ImageView imageView = (ImageView)itemView.findViewById(R.id.img);
        try {
            ped = datos.get(i);
            textView.setText(ped.getNumero_pedido());
            //   Bitmap imageBitmap = BitmapFactory.decodeFile(not.getUrlImg());
            //  imageView.setImageBitmap(imageBitmap);
        }catch (Exception ex){ }
        return itemView;
    }

}