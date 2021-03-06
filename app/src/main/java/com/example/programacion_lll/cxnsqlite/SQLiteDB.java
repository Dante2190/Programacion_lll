package com.example.programacion_lll.cxnsqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDB extends SQLiteOpenHelper {
    static String nameDBPedidos = "db_pedidos";
    static String tblPedios = "CREATE TABLE pedidos(idPedidos integer primary key autoincrement, numeroP text, nombre text,  direccion text, pedidos text, url text)";

    public SQLiteDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nameDBPedidos, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblPedios);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor MantenimientoPedidos(String accion, String[] data){
        SQLiteDatabase sqLiteDatabaseReadable=getReadableDatabase();
        SQLiteDatabase sqLiteDatabaseWritable=getWritableDatabase();
        Cursor cursor = null;
        switch (accion){
            case "consultar":
                cursor=sqLiteDatabaseReadable.rawQuery("SELECT * FROM pedidos ORDER BY numeroP ASC", null);
                break;
            case "nuevo":
                sqLiteDatabaseWritable.execSQL("INSERT INTO pedidos (numeroP,nombre,direccion,pedidos,url) VALUES('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"')");
                break;
            case "modificar":
                sqLiteDatabaseWritable.execSQL("UPDATE pedidos SET numeroP='"+ data[1] +"',nombre='"+data[2]+"',direccion='"+data[3]+"',pedidos='"+data[4]+"', url='"+data[5]+"' WHERE idPedidos='"+data[0]+"'");
                break;
            case "eliminar":
                sqLiteDatabaseWritable.execSQL("DELETE FROM pedidos WHERE idPedidos='"+ data[0] +"'");
                break;
        }
        return cursor;
    }
}
