package com.example.programacion_lll.cnxsqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDB extends SQLiteOpenHelper {
    static String NAME_DB = "db_pedido";
    static String NAME_TB = "CREATE TABLE pedidos(idPedidos integer primary key autoincrement, numeroP text, nombre text,  direccion text, pedidos text)";

    public SQLiteDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NAME_DB, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NAME_TB);
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
                sqLiteDatabaseWritable.execSQL("INSERT INTO pedidos (numeroP,nombre,direccion,pedidos) VALUES('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"')");
                break;
            case "modificar":
                sqLiteDatabaseWritable.execSQL("UPDATE pedidos SET numeroP='"+ data[1] +"',nombre='"+data[2]+"',direccion='"+data[3]+"',pedidos='"+data[4]+"' WHERE idPedidos='"+data[0]+"'");
                break;
            case "eliminar":
                sqLiteDatabaseWritable.execSQL("DELETE FROM pedidos WHERE idPedidos='"+ data[0] +"'");
                break;
        }
        return cursor;
    }
}
