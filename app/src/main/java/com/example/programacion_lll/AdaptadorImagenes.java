package com.example.programacion_lll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programacion_lll.cxnsqlite.Pedidos;

import java.util.ArrayList;

public class AdaptadorImagenes extends BaseAdapter {

    Context context;
    ArrayList<Pedidos> datos;
    LayoutInflater layoutInflater;
    Pedidos pedido;

    public AdaptadorImagenes(Context context, ArrayList<Pedidos> datos){
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
        View itemView = layoutInflater.inflate(R.layout.listview_imaganes, viewGroup, false);
        TextView textView = (TextView)itemView.findViewById(R.id.txtTitulo);
        TextView textView2 = (TextView)itemView.findViewById(R.id.txtTitulo2);
        ImageView imageView = (ImageView)itemView.findViewById(R.id.img);
        try {
            pedido = datos.get(i);
            textView.setText(pedido.getNumero_pedido());
            textView2.setText(pedido.getNombre_cliente());
            Bitmap imageBitmap = BitmapFactory.decodeFile(pedido.getUrlImg());
            imageView.setImageBitmap(imageBitmap);
        }catch (Exception ex){ }
        return itemView;
    }

}
