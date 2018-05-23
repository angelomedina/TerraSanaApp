package com.example.katty.terrasana;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HistorialActivity extends AppCompatActivity{
    LinearLayout historialLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        historialLayout = findViewById(R.id.historialLayout);

        DatabaseReference productos = FirebaseDatabase.getInstance().getReference().child("Compra").child("Compra");
        productos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if(childDataSnapshot.child("email").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()))

                        agregarHistorial(childDataSnapshot.child("fecha").getValue().toString(), childDataSnapshot.child("ubicacion").getValue().toString(), childDataSnapshot.child("lista").toString(), childDataSnapshot.child("monto").getValue().toString());

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void agregarHistorial(String fecha, String ubicacion, String lista, String monto){
        TextView txtFecha = new TextView(this);
        txtFecha.setText("--------------");
        historialLayout.addView(txtFecha);

        TextView txtSeparador = new TextView(this);
        txtSeparador.setText("Fecha: " + fecha + "\nUbicacion: " + ubicacion + "\nLista: " + lista + "\nMonto: " + monto);
        historialLayout.addView(txtSeparador);
    }
}
