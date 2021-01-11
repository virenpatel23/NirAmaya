package com.example.niramaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Precautions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precautions);

        Intent intent = getIntent();
        String disease = intent.getStringExtra("Disease_Name");
        ScrollView mal = findViewById(R.id.mal);
        ScrollView dengue = findViewById(R.id.dengue);
        ScrollView typhoid = findViewById(R.id.typhoid);
        ScrollView TB = findViewById(R.id.tb);
        ScrollView Gastro = findViewById(R.id.gestro);
        ScrollView Pneumonia = findViewById(R.id.pnemonia);
        ScrollView ARI = findViewById(R.id.ari);
        ScrollView heptatis = findViewById(R.id.hepta);
        ScrollView cholera = findViewById(R.id.cholera);
        ScrollView chikanguniya = findViewById(R.id.chikan);
        ScrollView filaria = findViewById(R.id.filaria);


        switch (disease) {
            case "Malaria":
                Toast.makeText(this, "" + disease, Toast.LENGTH_SHORT).show();
                mal.setVisibility(View.VISIBLE);
                break;
            case "Cholera":
                Toast.makeText(this, "" + disease, Toast.LENGTH_SHORT).show();
                cholera.setVisibility(View.VISIBLE);
            case "Typhoid":
                Toast.makeText(this, "" + disease, Toast.LENGTH_SHORT).show();
                typhoid.setVisibility(View.VISIBLE);
                break;
            case "Gestro_Enteritis":
                Toast.makeText(this, "" + disease, Toast.LENGTH_SHORT).show();
                Gastro.setVisibility(View.VISIBLE);
                break;
            case "Hepatitis":
                Toast.makeText(this, "" + disease, Toast.LENGTH_SHORT).show();
                heptatis.setVisibility(View.VISIBLE);
                break;
            case "Pneumonia":
                Toast.makeText(this, "" + disease, Toast.LENGTH_SHORT).show();
                Pneumonia.setVisibility(View.VISIBLE);
                break;
            case "Plumonory TB":
                Toast.makeText(this, "" + disease, Toast.LENGTH_SHORT).show();
                TB.setVisibility(View.VISIBLE);
                break;
            case "Acute Respiratory Infection":
                Toast.makeText(this, "" + disease, Toast.LENGTH_SHORT).show();
                ARI.setVisibility(View.VISIBLE);
                break;
            case "Chikungunya":
                Toast.makeText(this, "" + disease, Toast.LENGTH_SHORT).show();
                chikanguniya.setVisibility(View.VISIBLE);
                break;
            case "Filaria":
                Toast.makeText(this, "" + disease, Toast.LENGTH_SHORT).show();
                filaria.setVisibility(View.VISIBLE);
                break;
            case "Dengue":
                Toast.makeText(this, "" + disease, Toast.LENGTH_SHORT).show();
                dengue.setVisibility(View.VISIBLE);
                break;

        }
    }
}
