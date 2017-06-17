package com.primeiroprojeto.uhc.alcoolgasolina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnCalcular;

    private EditText txtValueAlcool;

    private EditText txtValueGasolina;

    private TextView txtResultado;

    private ListView listaHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalcular = (Button) findViewById(R.id.btnCalcular);

        txtValueAlcool = (EditText) findViewById(R.id.inputAlcool);

        txtValueGasolina = (EditText) findViewById(R.id.inputGasolina);

        txtResultado = (TextView) findViewById(R.id.txtResultado);

        listaHistorico = (ListView) findViewById(R.id.listViewHistorico);
        
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final float valorAlcool = Float.valueOf(txtValueAlcool.getText().toString());

                final float valorGasolina = Float.valueOf(txtValueGasolina.getText().toString());

                if((valorAlcool/valorGasolina)<0.7) {
                    txtResultado.setText("Compensa Alcool!");
                }

                else {
                    txtResultado.setText("Compensa Gasolina!");
                }
            }
        });
    }
}
