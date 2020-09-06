package com.example.programacion_lll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText edtMonto, edtCantidad, edtRes;
    private Button btnConvertir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tbconversor = (TabHost) findViewById(R.id.tbConversor);
        tbconversor.setup();
        //agregar pesta�as al tabhost
        tbconversor.addTab(tbconversor.newTabSpec("tab1").setIndicator("Conversor Propio", null).setContent(R.id.tabPropio));
        tbconversor.addTab(tbconversor.newTabSpec("tab2").setIndicator("Conversor De Area", null).setContent(R.id.tabArea));

        edtMonto = (EditText) findViewById(R.id.edtCantidad01);
        edtCantidad = (EditText) findViewById(R.id.edtCantidad02);
        edtRes = (EditText) findViewById(R.id.edtCantidad03);

        btnConvertir = (Button)findViewById(R.id.idconvertir);
        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int countRes = 0;
                    int resp = 0;
                    int tot = 0;
                    // int CountM = Integer.parseInt(edtMonto.getText().toString());
                    int CountC = Integer.parseInt(edtCantidad.getText().toString());

                    if (!edtMonto.getText().toString().isEmpty() && !edtCantidad.getText().toString().isEmpty()){

                        int CountM = Integer.parseInt(edtMonto.getText().toString());

                        for (int i = tot; i <= CountM; i++){
                            if (CountM >= CountC){
                                resp = CountM - CountC;
                                countRes += 1;
                                CountM = resp;
                            }
                        }
                        edtRes.setText(countRes + "/" + resp);

                    }else if (edtRes.getText().toString().contains("/")) {
                        String[] separated = edtRes.getText().toString().split("/");

                        int posicion0 = Integer.parseInt(separated[0]);
                        int posicion1 = Integer.parseInt(separated[1]);

                        int Resultado = CountC * posicion0 + posicion1;

                        edtMonto.setText(String.valueOf(Resultado));

                    }
                }catch (Exception error){
                    TextView textView = (TextView)findViewById(R.id.txtRespuesta);
                    textView.setText("introducir datos por favor");
                }
            }
        });
    }
}