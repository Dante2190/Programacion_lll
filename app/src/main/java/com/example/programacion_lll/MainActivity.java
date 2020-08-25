package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    // variables para enlazar con las vistas en el activity
    Button play_pause, btn_repetir;
    MediaPlayer mp;
    ImageView iv;

    // para determinar cuál canción sigue
    int repetir = 2, posicion = 0;

    // esta es una matriz para almacenar las canciones
    MediaPlayer vectormp [] = new MediaPlayer[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Todos los enlaces.
        play_pause = (Button)findViewById(R.id.btn_play);
        btn_repetir = (Button)findViewById(R.id.btn_repetir);
        iv = (ImageView)findViewById(R.id.imageView);
        // lista de canciones puedes agregar mas
        vectormp[0] = MediaPlayer.create(this,R.raw.audio1);
        vectormp[1] = MediaPlayer.create(this,R.raw.audio2);
        vectormp[2] = MediaPlayer.create(this,R.raw.audio3);

        Button buttonMo = (Button)findViewById(R.id.Btnbutton);

        buttonMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netxM = new Intent(MainActivity.this,Multimedia3.class);
                startActivity(netxM);
            }
        });

    }
    // Metodo para el botón PlayPause
    public void PLayPause(View view){
        if(vectormp[posicion].isPlaying()){
            vectormp[posicion].pause();
            play_pause.setBackgroundResource(R.drawable.music);
            Toast.makeText(this,"Pausa",Toast.LENGTH_SHORT).show();
        }else {
            vectormp[posicion].start();
            play_pause.setBackgroundResource(R.drawable.music2);
            Toast.makeText(this,"Play",Toast.LENGTH_SHORT).show();
        }
    }

    // Método para el botón Stop
    public void Stop(View view){
        if(vectormp[posicion] != null){
            vectormp[posicion].stop();
            vectormp[0] = MediaPlayer.create(this,R.raw.audio1);
            vectormp[1] = MediaPlayer.create(this,R.raw.audio2);
            vectormp[2] = MediaPlayer.create(this,R.raw.audio3);
            posicion = 0;
            play_pause.setBackgroundResource(R.drawable.music2);
            iv.setImageResource(R.drawable.android_dj);
            Toast.makeText(this,"Stop",Toast.LENGTH_SHORT).show();
        }
    }

    // Método repetir pista
    public void Repetir(View view){
        if(repetir == 1) {
            btn_repetir.setBackgroundResource(R.drawable.music3);
            Toast.makeText(this, "No repetir",Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping((false));
            repetir = 2;
        }else{
            btn_repetir.setBackgroundResource(R.drawable.music);
            Toast.makeText(this,
                    "Repetir",Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping((true));
            repetir = 1;
        }
    }

    //Método siguiente
    public void Siguiente(View view){
        if(posicion < vectormp.length -1){
            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();
                posicion++;
                vectormp[posicion].start();
                if(posicion == 0){
                    iv.setImageResource(R.drawable.music);
                }else if (posicion == 1){
                    iv.setImageResource(R.drawable.music2);
                }else if (posicion == 2){
                    iv.setImageResource(R.drawable.music3);
                }
            }else{
                posicion++;
                if(posicion == 0){
                    iv.setImageResource(R.drawable.music);
                }else if (posicion == 1){
                    iv.setImageResource(R.drawable.music2);
                }else if (posicion == 2){
                    iv.setImageResource(R.drawable.music3);
                }
            }
        }else {
            Toast.makeText(this,"No hay más canciones",Toast.LENGTH_SHORT).show();
        }
    }

    // Método para regresar a la canción anterior
    public void Anterior(View view){
        if(posicion >= 1) {
            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();
                vectormp[0] = MediaPlayer.create(this,R.raw.audio);
                vectormp[1] = MediaPlayer.create(this,R.raw.audio2);
                    vectormp[2] = MediaPlayer.create(this,R.raw.audio3);
                posicion--;
                if(posicion == 0){
                    iv.setImageResource(R.drawable.music);
                }else if (posicion == 1){
                    iv.setImageResource(R.drawable.music2);
                }else if (posicion == 2){
                    iv.setImageResource(R.drawable.music3);
                }
                vectormp[posicion].start();
            }else {
                posicion--;
                if(posicion == 0){
                    iv.setImageResource(R.drawable.music);
                }else if (posicion == 1){
                    iv.setImageResource(R.drawable.music2);
                }else if (posicion == 2){
                    iv.setImageResource(R.drawable.music3);
                }
            }
            }else{
            Toast.makeText(this,"No hay más canciones",Toast.LENGTH_SHORT).show();
        }
    }
}








