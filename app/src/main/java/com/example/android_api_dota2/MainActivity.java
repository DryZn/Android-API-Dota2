package com.example.android_api_dota2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
//rajouter mvvm observer et fragment
public class MainActivity extends AppCompatActivity {
    private RecyclerView liste_persos;
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
        liste_persos = findViewById(R.id.liste_heros);
        liste_persos.setHasFixedSize(true);
        //De manière à ce qu'il ne reste pas une ligne à moitié vide(/pleine)
        layoutManager = new GridLayoutManager(this, 2);
        liste_persos.setLayoutManager(layoutManager);
        adaptateur = new HerosAdapter(changesList);
        liste_persos.setAdapter(adaptateur);
    }
}