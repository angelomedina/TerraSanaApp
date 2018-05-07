package com.example.katty.terrasana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    TextView nombre;
    TextView direccion;
    TextView telefono;
    TextView correoE;
    TextView usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        TextView nombre = findViewById(R.id.nombre);
        nombre.setText("Katherine Salazar Pérez");
        TextView direccion = findViewById(R.id.direccion);
        direccion.setText("Zarcero");
        TextView telefono = findViewById(R.id.telefono);
        telefono.setText("8410-8269");
        TextView correoE = findViewById(R.id.correoE);
        correoE.setText("Kas@gmail.com");
        TextView usuario = findViewById(R.id.usuario);
        usuario.setText("Kas");
    }
}
