package com.example.katty.terrasana;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CatalogoActivity extends AppCompatActivity {

    ArrayList<Producto> dataModels;
    List<Producto> model=new ArrayList<>();
    ListView listView;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        listView = findViewById(R.id.list);
        dataModels = new ArrayList<>();

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

        return super.onOptionsItemSelected(item);
    }
}