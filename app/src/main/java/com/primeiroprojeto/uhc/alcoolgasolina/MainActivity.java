package com.primeiroprojeto.uhc.alcoolgasolina;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.MaskFormatter;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String NOME_ARQUIVO = "historico.txt";

    private Button btnCalcular;

    private EditText txtValueAlcool;

    private EditText txtValueGasolina;

    private TextView txtResultado;

    private ListView listaHistorico;

    private ArrayList<String>  historicoResultado = new ArrayList<>();

    private String [] Test = {"Test1","Test2"};
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

                //Convertendo valores para float para fazer os calculos
                final float valorAlcool = Float.valueOf(txtValueAlcool.getText().toString());
                final float valorGasolina = Float.valueOf(txtValueGasolina.getText().toString());

                if((valorAlcool/valorGasolina)<0.7) {
                    txtResultado.setText("Compensa Alcool!");
                }

                else {
                    txtResultado.setText("Compensa Gasolina!");
                }

                //Salvando o resultado no arquivo
                gravarNoArquivo(String.valueOf(txtResultado.getText()));

                Toast.makeText(MainActivity.this, "Calculado e salvo no histórico.", Toast.LENGTH_SHORT).show();

                preencherListView();
            }
        });

        preencherListView();


    }

    private void preencherListView() {

        //Recuperando o que foi gravado
        if((historicoResultado = lerHistorico())!=null){
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    historicoResultado
            );

            listaHistorico.setAdapter(adaptador);
        }
    }

    private ArrayList<String> lerHistorico(){

        //String[] historicoResultado;

        try {
            InputStream historico = openFileInput(NOME_ARQUIVO);

            //Checando se o arquivo existe
            if(historico!=null){

                Log.d(">>lerHistorico", "Arquivo existe");
                //Lendo o arquivo
                InputStreamReader inputStreamReader = new InputStreamReader(historico);

                //Gerando buffer do arquivo lido
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String linhaHistorico = "";

                //Percorrendo o arquivo enquanto tiver texto no buffer
                while( (linhaHistorico = bufferedReader.readLine()) != null ){
                    historicoResultado.add(linhaHistorico);
                }
                historico.close();
            }


        }
        catch (IOException e){
            Log.v("MainActivity", e.toString());
        }

        return historicoResultado;
    }

    private void gravarNoArquivo(String resultado) {

        Calendar rightNow = Calendar.getInstance();

        resultado += " - " + String.valueOf(rightNow.get(Calendar.DATE));

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE));
            outputStreamWriter.write(resultado);
            outputStreamWriter.close();
        }
        catch (IOException e){
            Log.v("MainActivity", e.toString());
        }

    }

}
