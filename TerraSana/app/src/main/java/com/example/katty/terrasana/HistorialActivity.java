package com.example.katty.terrasana;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.katty.terrasana.objetos.Historial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistorialActivity extends AppCompatActivity{

    List<Historial> model  = new ArrayList<>();
    HistorialAdapter adapter = null;
    private static final String TAGLOG = "firebase-db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        adapter = new HistorialAdapter();
        ListView list = (ListView) findViewById(R.id.lista_historial);
        list.setAdapter(adapter);


        mostrarHistorial();
    }


    class HistorialAdapter extends ArrayAdapter<Historial> {

        HistorialAdapter(){
            super(HistorialActivity.this, R.layout.row_historial, model);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row=convertView;
            HistorialHolder holder=null;
            if(row==null){
                LayoutInflater inflater=getLayoutInflater();
                row=inflater.inflate(R.layout.row_historial, parent,false);
                holder=new HistorialHolder(row);
                row.setTag(holder);
            }
            else{
                holder=(HistorialHolder) row.getTag();
            }
            holder.populateFrom(model.get(position));
            return (row);
        }
    }

    static class HistorialHolder{
        private TextView fecha,ubicacion,monto = null;

        HistorialHolder(View row) {
            fecha        = (TextView)  row.findViewById(R.id.fecha_historial);
            ubicacion    = (TextView) row.findViewById(R.id.ubicacion_historial);
            monto        = (TextView) row.findViewById(R.id.monto_historial);
        }

        void populateFrom(final Historial r) {
            fecha.setText("Nombre: "+r.getFecha());
            ubicacion.setText("Precio: "+r.getUbicacion());
            monto.setText("Cantidad: "+r.getMonto());
        }

    }


    public  void  mostrarHistorial() {
        DatabaseReference productos = FirebaseDatabase.getInstance().getReference().child("Compra").child("Compra");
        productos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String correo = LoginActivity.userEmail.toString();
                adapter.clear();

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    String email       =  childDataSnapshot.child("email").getValue().toString();
                    String fecha       =  childDataSnapshot.child("fecha").getValue().toString();
                    String ubicacion   =  childDataSnapshot.child("ubicacion").getValue().toString();
                    String monto       =  childDataSnapshot.child("monto").getValue().toString();

                    if(email.equals(correo)){

                        Historial historial = new Historial(fecha,ubicacion,monto);
                        adapter.add(historial);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });
    }
}
