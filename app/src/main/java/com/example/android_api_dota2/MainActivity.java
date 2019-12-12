package com.example.android_api_dota2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
//rajouter mvvm observer voir aussi material design et revoir mise en cache avec get save
public class MainActivity extends AppCompatActivity {
    private RecyclerView list_heroes;
    private RecyclerView.Adapter adaptateur;
    private RecyclerView.LayoutManager layoutManager;
    private ControllerAPI response;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getBaseContext().getSharedPreferences("DotaData", MODE_PRIVATE);
        response  = new ControllerAPI(this, sharedPreferences);
        response.start();
        // initialisation du fragment
        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            HeroesList heroesList = new HeroesList();
            manager.beginTransaction().add(R.id.heroes_content, heroesList).commit();
        }
    }

    protected void initRecycler(List<Heroes> changesList){
        list_heroes = findViewById(R.id.list_heroes);
        list_heroes.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list_heroes.setLayoutManager(layoutManager);
        adaptateur = new HerosAdapter(changesList);
        list_heroes.setAdapter(adaptateur);
    }
}