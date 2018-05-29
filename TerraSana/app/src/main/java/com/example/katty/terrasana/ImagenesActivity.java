package com.example.katty.terrasana;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImagenesActivity extends AppCompatActivity {
    static FirebaseAuth.AuthStateListener mAuthListener;
    imagenesAdapter adapter = null;
    public Context mContext;
    public String i1,i2,i3;
    List<String> imagenes = new ArrayList<>();

    static public int[] mImagesIds=new int[]{R.drawable.lechuga,R.drawable.oregano,R.drawable.lechuga};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenes);
        adapter = new imagenesAdapter();
        ViewPager viewPager = findViewById(R.id.viewPager);
        imagenesAdapter adapter = new imagenesAdapter(this);
        viewPager.setAdapter(adapter);

        i1=getIntent().getExtras().getString("imagen1");
        i2=getIntent().getExtras().getString("imagen2");
        i3=getIntent().getExtras().getString("imagen3");
        imagenes.add(i1);
        imagenes.add(i2);
        imagenes.add(i3);

    }


    class imagenesAdapter extends PagerAdapter {

        imagenesAdapter(Context context){
            mContext=context;
        }


        public imagenesAdapter() {
        }

        @Override
        public int getCount() {
            return mImagesIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            //imageView.setImageResource(mImagesIds[position]);
            String pos = imagenes.get(position);
            Picasso.get().load(pos).into(imageView);
            container.addView(imageView,0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView)object);
        }


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
}

