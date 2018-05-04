package com.example.katty.terrasana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Redirección al Login
        if (true) {
            //startActivity(new Intent(this, LoginActivity.class));
            startActivity(new Intent(this, MapsActivity.class));
            finish();
            return;
        }
    }
}
