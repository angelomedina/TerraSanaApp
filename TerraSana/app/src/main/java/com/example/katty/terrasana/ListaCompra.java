package com.example.katty.terrasana;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.katty.terrasana.objetos.Compra;
import com.example.katty.terrasana.objetos.Pedido;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListaCompra extends AppCompatActivity implements View.OnClickListener {

    List<Pedido>  model  = new ArrayList<>();
    PedidoAdapter adapter = null;
    private static final String TAGLOG = "firebase-db";

    Button  btnConfirmar,btnMapa;
    public static TextView txtTotal,txtUbicacion;
    LatLng miUbicacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra);



        adapter = new PedidoAdapter();
        ListView list = (ListView) findViewById(R.id.lista_pedidos);
        list.setAdapter(adapter);


        btnMapa = (Button) findViewById(R.id.mapa_compra);
        btnConfirmar = (Button) findViewById(R.id.btn_realizar_compra);
        txtTotal = (TextView) findViewById(R.id.txt_monto_total);
        txtUbicacion = (TextView) findViewById(R.id.txt_ubicacion);

        btnConfirmar.setOnClickListener(this);


        carritoUsuario();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_realizar_compra:
                procesarCompra();
                break;
            case R.id.mapa_compra:
                mostrarMapa();
                break;
        }
    }

    public  void procesarCompra() {

        DatabaseReference productos = FirebaseDatabase.getInstance().getReference().child("Carrito").child("Carrito");
        productos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String          correo       = LoginActivity.userEmail.toString();
                List<Pedido>    lista        = new ArrayList<>();
                int             monto        = 0;

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    String key      =  childDataSnapshot.getKey().toString();
                    String email    =  childDataSnapshot.child("email").getValue().toString();
                    String cantidad =  childDataSnapshot.child("catidad").getValue().toString();
                    String precio   =  childDataSnapshot.child("precio").getValue().toString();
                    String producto =  childDataSnapshot.child("producto").getValue().toString();
                    Boolean estado  = (Boolean) childDataSnapshot.child("estado").getValue();

                    int    cantidaINT = Integer.parseInt(cantidad);
                    int    precioINT  = Integer.parseInt(precio);
                    int    totalINT   =  cantidaINT *  precioINT;


                    if(email.equals(correo) && estado == false){

                        Pedido pedido = new Pedido(producto,precio,cantidad);
                        lista.add(pedido);
                        monto=monto+totalINT;

                        FirebaseDatabase.getInstance().getReference().child("Carrito").child("Carrito").child(key).child("estado").setValue(true);
                    }
                }
                realizarCompra( lista,correo,monto);
                lista.clear();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });
    }

    void realizarCompra(List<Pedido> lista,String email,int  monto){

        if(monto > 0) {

            Date fecha = Calendar.getInstance().getTime();

            String montoTotal = Integer.toString(monto);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference ref = database.getReference("Compra");

            Compra compra = new Compra(lista, montoTotal, txtUbicacion.getText().toString(), email, fecha.toString());
            ref.child("Compra").push().setValue(compra);

            mensaje("Compra realizada");
        }

    }

    public  void carritoUsuario() {
        DatabaseReference productos = FirebaseDatabase.getInstance().getReference().child("Carrito").child("Carrito");
        productos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String correo = LoginActivity.userEmail.toString();
                int    monto  = 0;
                adapter.clear();

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    String email    =  childDataSnapshot.child("email").getValue().toString();
                    String cantidad =  childDataSnapshot.child("catidad").getValue().toString();
                    String precio   =  childDataSnapshot.child("precio").getValue().toString();
                    String producto =  childDataSnapshot.child("producto").getValue().toString();
                    Boolean estado  = (Boolean) childDataSnapshot.child("estado").getValue();

                    int    cantidaINT = Integer.parseInt(cantidad);
                    int    precioINT  = Integer.parseInt(precio);
                    int    totalINT   =  cantidaINT *  precioINT;

                    if(email.equals(correo)  && estado == false){
                        Pedido pedido = new Pedido(producto,precio,cantidad);
                        monto=monto+totalINT;
                        adapter.add(pedido);
                    }
                }
                txtTotal.setText("Total:  "+Integer.toString(monto));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });
    }


    public void mensaje(String mensaje){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje)
                .setTitle("Mensaje")
                .setCancelable(false)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    class PedidoAdapter extends ArrayAdapter<Pedido> {

        PedidoAdapter(){
            super(ListaCompra.this, R.layout.row_pedido, model);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row=convertView;
            ListaCompra.PedidoHolder holder=null;
            if(row==null){
                LayoutInflater inflater=getLayoutInflater();
                row=inflater.inflate(R.layout.row_pedido, parent,false);
                holder=new ListaCompra.PedidoHolder(row);
                row.setTag(holder);
            }
            else{
                holder=(ListaCompra.PedidoHolder) row.getTag();
            }
            holder.populateFrom(model.get(position));
            return (row);
        }
    }

    static class PedidoHolder{
        private TextView    nombre,precio,cantidad = null;

        PedidoHolder(View row) {
            nombre    = (TextView)  row.findViewById(R.id.nombre_pedido);
            precio    = (TextView) row.findViewById(R.id.precio_pedido);
            cantidad  = (TextView) row.findViewById(R.id.cantidad_pedido);
        }

        void populateFrom(final Pedido r) {
            nombre.setText("Nombre: "+r.getNombre());
            precio.setText("Precio: "+r.getPrecio());
            cantidad.setText("Cantidad: "+r.getCantidad());
        }

    }

    public void mostrarMapa(){
        Intent maps = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(maps);
    }
}