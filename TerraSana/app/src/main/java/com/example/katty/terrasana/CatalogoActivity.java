package com.example.katty.terrasana;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CatalogoActivity extends AppCompatActivity {

    ArrayList<Producto> dataModels;
    List<Producto> model=new ArrayList<>();
    ListView listView;
    private static CustomAdapter adapter;
    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        listView = findViewById(R.id.list);
        dataModels = new ArrayList<>();
        //debo agregar el evento para le boton del speech

        dataModels.add(new Producto("Lechuga", "0", "Unidad"));
        dataModels.add(new Producto("Oregano", "0","10 Gramos"));

        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catalogo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.car) {
            Intent registrar = new Intent(getApplicationContext(), ListaCompra.class);
            startActivity(registrar);
        }
       else  if (id == R.id.microphone) {
            Intent registrar = new Intent(getApplicationContext(), ListaCompra.class);
            if(isConnected()){
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE , "es-ES");
                startActivityForResult(intent, REQUEST_CODE);
                Toast.makeText(CatalogoActivity.this, "No funciona por ahora", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(StoTMainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!= null && net.isAvailable() && net.isConnected()){
            return true;
        }   else {
            return false;
        }
    }
}