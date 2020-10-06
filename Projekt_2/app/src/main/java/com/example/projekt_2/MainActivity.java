package com.example.projekt_2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView lvListaZakupow;
    private ArrayList<String> alListaZakupow;
    private ArrayAdapter<String> adLista;
    private TextView tvTytul;
    private EditText edElementDoDodania;
    private Button bDodajProdukt;
    private Button bUsunProdukt;
    private Button bZapis;
    private Button bOdczyt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alListaZakupow = new ArrayList<String>();
        alListaZakupow.add("Mleko");
        alListaZakupow.add("Ser");
        alListaZakupow.add("Maslo");

        adLista =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,alListaZakupow);

        lvListaZakupow = (ListView) findViewById(R.id.lvListaZakupow);
        lvListaZakupow.setAdapter(adLista);

        tvTytul = (TextView) findViewById(R.id.tvTytul);
        edElementDoDodania = (EditText) findViewById(R.id.edElementDoDodania);

        bDodajProdukt = (Button) findViewById(R.id.bDodajProdukt);
        bUsunProdukt = (Button) findViewById(R.id.bUsunProdukt);
        bDodajProdukt.setOnClickListener(this);
        bUsunProdukt.setOnClickListener(this);
        bZapis = (Button) findViewById(R.id.bZapis);
        bZapis .setOnClickListener(this);
        bOdczyt = (Button) findViewById(R.id.bOdczyt);
        bOdczyt.setOnClickListener(this);

        lvListaZakupow.setOnItemClickListener(this);
        lvListaZakupow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adLista.remove(adLista.getItem(position));
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bDodajProdukt:
                String elem = edElementDoDodania.getText().toString();
                if (elem.length() > 0) {
                    adLista.add(elem);
                    setHint();
                } else {
                    Toast.makeText(this, "Nic nie dodajesz do listy", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.bUsunProdukt:
                lvListaZakupow.setOnItemClickListener(this);
                break;

            case R.id.bOdczyt:
                ArrayList <String > nowaLista = ReadFromFile("Lista.txt");
                adLista.clear();
                adLista.addAll(nowaLista);
                break;

            case R.id.bZapis:
                WriteToFile("Lista.txt");
                break;
        }


    }

    public void setHint(){edElementDoDodania.setHint("Dodaj Imie ");}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adLista.remove(adLista.getItem(position));
    }

    private void WriteToFile(String fileName) {
        try {
            OutputStreamWriter outWriter = new OutputStreamWriter(openFileOutput(fileName,
                    Context.MODE_PRIVATE));
            for (int i = 0; i < adLista.getCount(); i++) {
                outWriter.write(adLista.getItem(i).toString() + "\n");
            }
            outWriter.close();
        }
        catch (IOException e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<String> ReadFromFile(String fileName) {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {     InputStream inputStream = openFileInput(fileName);
            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String receivedString = "";
                while ((receivedString = bufferedReader.readLine()) != null) {
                    arrayList.add(receivedString);
                }
                inputStream.close();
            }    }
        catch (IOException  e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
        return arrayList;  }


}
