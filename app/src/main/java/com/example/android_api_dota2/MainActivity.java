package com.example.android_api_dota2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

//rajouter mvvm observer voir aussi material design et revoir mise en cache avec get save
public class MainActivity extends AppCompatActivity implements PictureHeroCB {
    private RecyclerView list_heroes;
    private RecyclerView.Adapter adaptateur;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private FragmentManager manager;
    private int recycler;
    ControllerAPI response;
    RecylcerFrag heroesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        // regarder si le fragment est deja charge
        heroesList = (RecylcerFrag) manager.findFragmentByTag("herolist");
        if (heroesList == null) {
            heroesList = new RecylcerFrag();
            heroesList.layout = R.layout.heroes_list;
            manager.beginTransaction().add(R.id.heroes_content, heroesList, "herolist").commit();
            recycler = R.id.list_heroes;
        } else {
            System.out.println("PAUSE FIN");
            manager.popBackStackImmediate("herolist", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().show(heroesList).commit();
            list_heroes.setAdapter(adaptateur);
            adaptateur.notifyDataSetChanged();
            System.out.println("ouaiiiis");
        }
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
                adaptateur = new HerosAdapter(this, itemList);
        }
        list_heroes.setAdapter(adaptateur);
        heroesList.list_heroes = list_heroes;
        heroesList.adaptateur = adaptateur;
    }

    protected void watchDetails(Heroes hero) {
        PictureHero details = new PictureHero();
        details.hero = hero;
        // mise en backstack du fragment actuel avant de le remplacer par la vue en details
        manager.beginTransaction().replace(R.id.heroes_content, details)
                .addToBackStack("herolist").commit();
    }

    // le bouton precedent ne remet pas automatiquement la vue d'avant
    /*@Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
            this.initHeroList();}
        else
            super.onBackPressed();
        // recuperation du dernier tag mis dans la backstack
        int last_index = manager.getBackStackEntryCount() - 1;
        System.out.println(last_index);
        String last_tag = manager.getBackStackEntryAt(last_index).getName();
        switch (last_tag) {
            case "herolist":
                this.initHeroList();
        }
    }*/

    // pas encore bien compris les callbacks
    @Override
    public void displayHero(int pos) {
        System.out.println("Impressionnant!");
    }
}