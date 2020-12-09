package com.example.programacion_lll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programacion_lll.cxnsqlite.Pedidos;
import com.example.programacion_lll.cxnsqlite.SQLiteDB;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PedidosActivity extends AppCompatActivity {
    private EditText nPedido, edtNombre, edtDireccion, edtPedido;
    SQLiteDB miDB;
    String accion ="nuevo";
    String idPedido;

    private String numero = "";
    private String nombre = "";
    private String direccion = "";
    private String pedido = "";

    ImageView imgProducto;
    String urlCompletaImg;
    Intent takePictureIntent;
    private ImageView mUploadPicture;
    private ImageView mImageView;
    private Bitmap mCaptureOrUploadBitmap=null;
    private String mProductImagePath=null;
    String productImagePath;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private static final int UPLOAD_PICTURE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        mImageView = (ImageView) findViewById(R.id.tomarFotos);
        imgProducto = findViewById(R.id.tomarFotos);

        nPedido = (EditText)findViewById(R.id.edtNumeroP) ;
        edtNombre = (EditText)findViewById(R.id.edtNombre) ;
        edtDireccion = (EditText)findViewById(R.id.edtDireccion) ;
        edtPedido = (EditText)findViewById(R.id.edtPedidos) ;

        mUploadPicture = (ImageView) findViewById(R.id.suburImage);
        mUploadPicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent;
                if (Build.VERSION.SDK_INT > 19) {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                } else {
                    intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }

                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), UPLOAD_PICTURE);
            }
        });

        IniciarFirebase();

        TomarFotoPedidos();
        MostrarDatosPedidos();
    }

    //firebase

    public void GuardarFirebase(){
        numero= nPedido.getText().toString();
        nombre= edtNombre.getText().toString();
        direccion=  edtDireccion.getText().toString();
        pedido = edtPedido.getText().toString();

        if (!numero.isEmpty() && !nombre.isEmpty() && !direccion.isEmpty() && !pedido.isEmpty() ) {
            Pedidos p = new Pedidos();
            p.setId(UUID.randomUUID().toString());
            p.setNumero_pedido(numero);
            p.setNombre_cliente(nombre);
            p.setDirecion(direccion);
            p.setPedidos(pedido);
            p.setUrlImg(productImagePath);

            databaseReference.child("Pedidos").child(p.getId()).setValue(p);
            // Toast.makeText(this, "", Toast.LENGTH_SHORT).show();


            //Snackbar snackbar = Snackbar.make(layout,"exito con firebase",Snackbar.LENGTH_LONG);
            // snackbar.show();

        }else Toast.makeText(PedidosActivity.this, "llene los campos por favor", Toast.LENGTH_SHORT).show();

    }
    // iniciar firebase
    private void IniciarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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
                numero= nPedido.getText().toString();
                nombre= edtNombre.getText().toString();
                direccion=  edtDireccion.getText().toString();
                pedido = edtPedido.getText().toString();
                if (!numero.isEmpty() && !nombre.isEmpty() && !direccion.isEmpty() && !pedido.isEmpty() ) {
                    Bundle recibirParametros = getIntent().getExtras();
                    accion = recibirParametros.getString("accion");

                    if (accion.equals("nuevo")){
                        GuardarDatosPedidos();
                        GuardarFirebase();
                    }else if(accion.equals("modificar")){

                        GuardarDatosPedidos();
                    }
                    finish();
                }else Toast.makeText(PedidosActivity.this, "llene los campos por favor", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.icon_regresar:
                MostrarListaPedidos();
                return true;
        }
        return true;
    }

    private void MostrarListaPedidos() {
        Intent mostrarProductos = new Intent(PedidosActivity.this, ViewAtivity.class);
        startActivity(mostrarProductos);
    }

    private void GuardarDatosPedidos() {
        if(mImageView.getDrawable() !=null){
            createProductImageFile();
            productImagePath = mProductImagePath;
        }else {
            productImagePath =  urlCompletaImg;
        }

        TextView tempVal = findViewById(R.id.edtNumeroP);
        String numeroP = tempVal.getText().toString();

        tempVal = findViewById(R.id.edtNombre);
        String nombre = tempVal.getText().toString();

        tempVal = findViewById(R.id.edtDireccion);
        String direccion = tempVal.getText().toString();

        tempVal = findViewById(R.id.edtPedidos);
        String pedidos = tempVal.getText().toString();

        String[] data = {idPedido,numeroP,nombre,direccion,pedidos, productImagePath};

        miDB = new SQLiteDB(getApplicationContext(),"", null, 1);
        miDB.MantenimientoPedidos(accion, data);

        Toast.makeText(getApplicationContext(),"Registro de productos insertado con exito", Toast.LENGTH_LONG).show();
        MostrarListaPedidos();
    }

    void MostrarDatosPedidos(){
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");
            if (accion.equals("modificar")){
                String[] dataAmigo = recibirParametros.getStringArray("dataAmigo");

                idPedido = dataAmigo[0];

                TextView tempVal = (TextView)findViewById(R.id.edtNumeroP);
                tempVal.setText(dataAmigo[1]);

                tempVal = (TextView)findViewById(R.id.edtNombre);
                tempVal.setText(dataAmigo[2]);

                tempVal = (TextView)findViewById(R.id.edtDireccion);
                tempVal.setText(dataAmigo[3]);

                tempVal = (TextView)findViewById(R.id.edtPedidos);
                tempVal.setText(dataAmigo[4]);

                productImagePath= dataAmigo[5];
                Bitmap imageBitmap = BitmapFactory.decodeFile(productImagePath);
                imgProducto.setImageBitmap(imageBitmap);
            }
        }catch (Exception ex){
            ///
        }
    }

    //fotos des aqui
    void TomarFotoPedidos(){
        imgProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    }catch (Exception ex){}

                    if (photoFile != null) {
                        try {
                            Uri photoURI = FileProvider.getUriForFile(PedidosActivity.this, "com.example.programacion_lll.fileprovider", photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, 1);
                        }catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Error Toma Foto: "+ ex.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgProducto.setImageBitmap(imageBitmap);
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (resultCode == RESULT_OK&&requestCode==UPLOAD_PICTURE) {

            Uri targetURI = data.getData();
            Bitmap bitmap=null;

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetURI));
                mImageView.setImageBitmap(bitmap);
                mCaptureOrUploadBitmap = bitmap;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "imagen_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if( storageDir.exists()==false ){
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        urlCompletaImg = image.getAbsolutePath();
        return image;
    }

    private void createProductImageFile(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir("Pictures", Context.MODE_PRIVATE);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File myPath = new File(directory, imageFileName + ".jpg");

        mProductImagePath = myPath.toString();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            mCaptureOrUploadBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}