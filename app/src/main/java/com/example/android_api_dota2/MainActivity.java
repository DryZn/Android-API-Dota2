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
    private SharedPreferences sharedPreferences;
    private FragmentManager manager;
    private int recycler;
    ControllerAPI response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialisation si necessaire
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main);
            sharedPreferences = getBaseContext().getSharedPreferences("DotaAppli", MODE_PRIVATE);
            response  = new ControllerAPI(this, sharedPreferences, "HeroesData");
            response.start();
            manager = getSupportFragmentManager();
            initHeroList();
        }
    }

    private void initHeroList() {
        RecylcerFrag heroesList = new RecylcerFrag();
        heroesList.layout = R.layout.heroes_list;
        manager.beginTransaction().add(R.id.heroes_content, heroesList).commit();
        recycler = R.id.list_heroes;
    }

    // fonction sert a creer le recyclerview
    protected void initRecycler(List<Heroes> itemList){
        list_heroes = findViewById(recycler);
        list_heroes.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list_heroes.setLayoutManager(layoutManager);
        // choix de l'adaptateur en fonction de la liste a instancier
        switch (recycler){
            case R.id.list_heroes:
                adaptateur = new HerosAdapter(itemList);
        }
        list_heroes.setAdapter(adaptateur);
    }
}