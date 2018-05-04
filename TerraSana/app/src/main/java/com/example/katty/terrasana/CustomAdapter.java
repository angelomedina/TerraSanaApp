package com.example.katty.terrasana;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Producto> {


    ArrayList<Producto> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView nombre;
        TextView unidad;
        TextView cantidad;
        ImageView imagen;
        Button aumentar;
        Button disminuir;
        Button carrito;


    }

    public CustomAdapter(ArrayList<Producto> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }
    private int lastPosition = -1;

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {


        // Get the data item for this position
        final Producto dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;


        if (convertView == null) {
            final int[] cant = {0};
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.nombre = convertView.findViewById(R.id.nombre);
            viewHolder.unidad = convertView.findViewById(R.id.unidad);
            viewHolder.cantidad = convertView.findViewById(R.id.cantidad);
            viewHolder.imagen = convertView.findViewById(R.id.imagen);

            viewHolder.carrito = convertView.findViewById(R.id.carrito);
            viewHolder.carrito.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                      Toast.makeText(getContext(), viewHolder.nombre.getText().toString() + " " + viewHolder.cantidad.getText().toString(), Toast.LENGTH_SHORT).show();

              }
          });

            viewHolder.aumentar = (Button) convertView.findViewById(R.id.aumentar);
            viewHolder.aumentar.setOnClickListener(new View.OnClickListener() {
                //int cant=0;
                @Override
                public void onClick(View view) {
                    if(cant[0]>=0){
                        cant[0]++;
                        viewHolder.cantidad.setText(String.valueOf(cant[0]));
                    }
                }
            });

            viewHolder.disminuir = (Button) convertView.findViewById(R.id.disminuir);
            viewHolder.disminuir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cant[0]>=1) {
                        cant[0]--;
                        viewHolder.cantidad.setText(String.valueOf(cant[0]));
                    }
                }
            });
            result = convertView;

            convertView.setTag(viewHolder);


        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

            viewHolder.nombre.setText(dataModel.getNombre());
            viewHolder.cantidad.setText(dataModel.getCantidad());
            viewHolder.unidad.setText(dataModel.getUnidad());

        // Return the completed view to render on screen
        if (dataModel.getNombre().equals("Lechuga")) {
            viewHolder.imagen.setImageResource(R.drawable.lechuga);
        }
        else if (dataModel.getNombre().equals("Oregano")) {
            viewHolder.imagen.setImageResource(R.drawable.oregano);
        }
         /*  mRegisterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent registrar = new Intent(getApplicationContext(), RegistroActivity.class);
                    startActivity(registrar);
                }
            });*/



        return convertView;
    }

}
