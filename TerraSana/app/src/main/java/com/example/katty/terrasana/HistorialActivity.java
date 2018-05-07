package com.example.katty.terrasana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HistorialActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<>();
        categories.add("25-04-2018");
        categories.add("26-04-2018");
        categories.add("27-04-2018");
        categories.add("28-04-2018");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(item.equals("25-04-2018")) {
            Toast.makeText(parent.getContext(), "25-04-2018: " + item, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
