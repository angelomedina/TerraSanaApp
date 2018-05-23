package com.example.katty.terrasana;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.katty.terrasana.objetos.Carrito;
import com.example.katty.terrasana.objetos.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CatalogoActivity extends AppCompatActivity {


    static FirebaseAuth.AuthStateListener mAuthListener;



    Spinner spinnerProductos;
    String[] listaProductos = {"Todos","Fruta","Hierba","Hortaliza","Tubérculo"};

    List<Producto>    model  = new ArrayList<>();
    ProductoAdapter   adapter = null;

    private static final String TAGLOG = "firebase-db";
    private static final int REQ_CODE_SPEECH_INPUT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);


        adapter = new ProductoAdapter();
        ListView list = (ListView) findViewById(R.id.lista_productos);
        list.setAdapter(adapter);

        spinnerProductos = (Spinner) findViewById(R.id.select_producto);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaProductos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductos.setAdapter(dataAdapter);

        spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                adapter.clear();
                String categoria = (String) adapterView.getItemAtPosition(i);

                if(categoria != "Todos") {
                    categoriaProductos(categoria);
                }else{
                    mostrarTodosLosProductos();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void mostrarTodosLosProductos(){
        DatabaseReference productos = FirebaseDatabase.getInstance().getReference().child("Productos");
        productos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot childChildDataSnapshot : childDataSnapshot.getChildren()) {
                        String nombre =  childChildDataSnapshot.getKey();
                        String icono   = childChildDataSnapshot.child("icono").getValue().toString();
                        String imagen1 = childChildDataSnapshot.child("imagen1").getValue().toString();
                        String imagen2 = childChildDataSnapshot.child("imagen2").getValue().toString();
                        String imagen3 = childChildDataSnapshot.child("imagen3").getValue().toString();
                        int    precio =  Integer.parseInt(childChildDataSnapshot.child("precio").getValue().toString());
                        String unidad = childChildDataSnapshot.child("unidad").getValue().toString();

                        Producto producto = new Producto(nombre,icono,imagen1,imagen2,imagen3,precio,unidad);
                        adapter.add(producto);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });
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
            startVoiceInput();
        }
        else if (id == R.id.perfil) {
            Intent registrar = new Intent(getApplicationContext(), PerfilActivity.class);
            startActivity(registrar);
        }

        else if (id == R.id.historial) {
            Intent registrar = new Intent(getApplicationContext(), HistorialActivity.class);
            startActivity(registrar);
        }
        else if(id == R.id.cerrarsesion){
            Intent cerrarSesion = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(cerrarSesion);
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

    class ProductoAdapter extends ArrayAdapter<Producto> {

        ProductoAdapter(){
            super(CatalogoActivity.this, R.layout.row_item, model);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row=convertView;
            ProductoHolder holder=null;
            if(row==null){
                LayoutInflater inflater=getLayoutInflater();
                row=inflater.inflate(R.layout.row_item, parent,false);
                holder=new ProductoHolder(row);
                row.setTag(holder);
            }
            else{
                holder=(ProductoHolder) row.getTag();
            }
            holder.populateFrom(model.get(position));
            return (row);
        }
    }

    static class ProductoHolder extends LoginActivity{


        private ImageView icono, imagen1, imagen2, imagen3 = null;
        private TextView nombre,unidad,precio = null;
        private Button   aumentar,disminuir=null;
        private ImageButton confirmar=null;
        private TextView cantidad=null;


        ProductoHolder(View row) {
            icono     = (ImageView) row.findViewById(R.id.imagen_row);
            nombre    = (TextView)  row.findViewById(R.id.nombre_row);
            unidad    = (TextView) row.findViewById(R.id.unidad_row);
            precio    = (TextView) row.findViewById(R.id.precio_row);
            aumentar  = (Button) row.findViewById(R.id.aumentar);
            disminuir = (Button) row.findViewById(R.id.disminuir);
            confirmar = (ImageButton) row.findViewById(R.id.confirmar);
            cantidad  = (TextView) row.findViewById(R.id.cantidad);

        }

        void populateFrom(final Producto r) {
            final int[] cant = {0};

            Picasso.get().load(r.getIcono()).into(icono);
            nombre.setText("Nombre: "+r.getNombre());
            precio.setText("Precio: " + Integer.toString(r.getPrecio()));
            unidad.setText("Unidad: " + r.getUnodad());

            confirmar.setBackgroundColor(0xFFFFFFFF);

            aumentar.setOnClickListener(new View.OnClickListener() {
                //int cant=0;
                @Override
                public void onClick(View view) {
                    if(cant[0]>=0){
                        cant[0]++;
                        cantidad.setText(String.valueOf(cant[0]));

                        confirmar.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            });

            disminuir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cant[0]>=1) {
                        cant[0]--;
                        cantidad.setText(String.valueOf(cant[0]));

                        confirmar.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            });


            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cant[0]>=1) {

                        String correo = userEmail;

                        agregarCarrito(correo,r,cant[0]);
                        confirmar.setBackgroundColor(0xFFDDF603);

                    }
                }
            });

        }

        void agregarCarrito(String correo,Producto r,int cant){

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference ref      = database.getReference("Carrito");

            Carrito carrito = new Carrito(correo,r.getNombre(),Integer.toString(r.getPrecio()),cant,Boolean.FALSE);
            ref.child("Carrito").push().setValue(carrito);
        }
    }

    public  void categoriaProductos(String categoria){
        DatabaseReference productos = FirebaseDatabase.getInstance().getReference().child("Productos").child(categoria);
        System.out.println(productos.toString());
        productos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String nombre =  childDataSnapshot.getKey();
                    String icono   = childDataSnapshot.child("icono").getValue().toString();
                    String imagen1 = childDataSnapshot.child("imagen1").getValue().toString();
                    String imagen2 = childDataSnapshot.child("imagen2").getValue().toString();
                    String imagen3 = childDataSnapshot.child("imagen3").getValue().toString();
                    int    precio =  Integer.parseInt(childDataSnapshot.child("precio").getValue().toString());
                    String unidad = childDataSnapshot.child("unidad").getValue().toString();
                    Producto producto = new Producto(nombre,icono,imagen1,imagen2,imagen3,precio,unidad);
                    adapter.add(producto);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });
    }

    private void startVoiceInput() {
        System.out.println("Entrando al speech");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast t = Toast.makeText(getApplicationContext(),
                    "Ops! Your device doesn't support Speech to Text",
                    Toast.LENGTH_SHORT);
            t.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String producto = result.get(0);
                    if(producto == "Frutas"){
                        spinnerProductos.setSelection(1);
                        categoriaProductos(result.get(0));
                    }
                    else if(producto == "Hierbas"){
                        categoriaProductos(result.get(0));
                        spinnerProductos.setSelection(2);
                    }
                    else if(producto == "Hortalizas"){
                        categoriaProductos(result.get(0));
                        spinnerProductos.setSelection(3);
                    }
                    else if(producto == "Tubérculos"){
                        categoriaProductos(result.get(0));
                        spinnerProductos.setSelection(4);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Búsqueda no válida. Búsqueda por voz sólo válida para categorías de productos.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    }


}