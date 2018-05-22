package com.example.katty.terrasana;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.katty.terrasana.objetos.Pedido;

import java.util.ArrayList;
import java.util.List;

public class InfoPedidoActivity extends AppCompatActivity {

    List<Pedido>  model  = new ArrayList<>();
    PedidoAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pedido);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Extraer lat. y lng.
        Intent intent = getIntent();
        String latlng = String.format(
                getString(R.string.marker_detail_latlng),
                intent.getDoubleExtra(MapsActivity.EXTRA_LATITUD, 0),
                intent.getDoubleExtra(MapsActivity.EXTRA_LONGITUD, 0));

        // Poblar
        //TextView coordenadas = (TextView) findViewById(R.id.tv_latlng);
        //coordenadas.setText(latlng);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    class PedidoAdapter extends ArrayAdapter<Pedido> {

        PedidoAdapter(){
            super(InfoPedidoActivity.this, R.layout.row_pedido, model);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row=convertView;
            PedidoHolder holder=null;
            if(row==null){
                LayoutInflater inflater=getLayoutInflater();
                row=inflater.inflate(R.layout.row_pedido, parent,false);
                holder=new InfoPedidoActivity.PedidoHolder(row);
                row.setTag(holder);
            }
            else{
                holder=(InfoPedidoActivity.PedidoHolder) row.getTag();
            }
            holder.populateFrom(model.get(position));
            return (row);
        }
    }

    static class PedidoHolder extends LoginActivity{

        private TextView nombre,precio,cantidad = null;

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
}

