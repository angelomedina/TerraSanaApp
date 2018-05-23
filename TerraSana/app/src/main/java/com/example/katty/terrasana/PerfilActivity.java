package com.example.katty.terrasana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

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
        nombre.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());

    }
}
