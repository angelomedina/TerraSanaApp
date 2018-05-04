package com.example.katty.terrasana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListaCompra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra);

        final Button mapa = findViewById(R.id.mapa);


        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mapa = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(mapa);
            }

        });
    }

}
