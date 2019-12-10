package com.example.android_api_dota2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
//rajouter mvvm observer et fragment (transaction de fragments) voir aussi material design
public class MainActivity extends AppCompatActivity {
    private RecyclerView liste_heros;
    private RecyclerView.Adapter adaptateur;
    private RecyclerView.LayoutManager layoutManager;
    private ControllerAPI reponse;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getBaseContext().getSharedPreferences("HerosData", MODE_PRIVATE);
        reponse  = new ControllerAPI(this, sharedPreferences);
        reponse.start();
    }

    protected void initRecycler(List<Heroes> changesList){
        liste_heros = findViewById(R.id.liste_heros);
        liste_heros.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        liste_heros.setLayoutManager(layoutManager);
        adaptateur = new HerosAdapter(changesList);
        liste_heros.setAdapter(adaptateur);
    }
}