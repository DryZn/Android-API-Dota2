package com.example.android_api_dota2;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

//rajouter mvvm observer voir aussi material design et revoir mise en cache avec get save
public class MainActivity extends AppCompatActivity implements RecyclerFragCB {
    private SharedPreferences sharedPreferences;
    private FragmentManager manager;
    protected RecylcerFrag heroesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialisation si necessaire
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main);
            sharedPreferences = getBaseContext().getSharedPreferences("DotaAppli", MODE_PRIVATE);
            ControllerAPI response  = new ControllerAPI(this, sharedPreferences, "HeroesData");
            response.start();
        manager = getSupportFragmentManager();
        initHeroList();
    }
}

    protected void initHeroList() {
        // regarder si le fragment est deja charge en memoire
        heroesList = (RecylcerFrag) manager.findFragmentByTag("herolist");
        if (heroesList == null) {
            heroesList = new RecylcerFrag();
            heroesList.layout = R.layout.heroes_list;
            manager.beginTransaction().add(R.id.heroes_content, heroesList, "herolist").commit();
        } else {
            manager.popBackStackImmediate("herolist", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().show(heroesList).commit();
        }
    }

    @Override
    public void watchDetails(Heroes hero) {
        PictureHero details = new PictureHero();
        details.hero = hero;
        // mise en backstack du fragment actuel avant de le remplacer par la vue en details
        manager.beginTransaction().replace(R.id.heroes_content, details)
                .addToBackStack("herolist").commit();
    }
}