package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tbconversor = (TabHost) findViewById(R.id.tbConversor);
        tbconversor.setup();
        //agregar pesta�as al tabhost
        tbconversor.addTab(tbconversor.newTabSpec("tab1").setIndicator("Conversor Propio", null).setContent(R.id.tabPropio));
        tbconversor.addTab(tbconversor.newTabSpec("tab2").setIndicator("Conversor De Area", null).setContent(R.id.tabArea));


    }
}