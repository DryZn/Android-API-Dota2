package com.example.android_api_dota2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Details extends FragmentActivity implements PictureHeroCB {
    public String Fragments = "pictureheroTag";
    public static Heroes hero;
    private ImageView imageStats;
    private TextView nomHero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            // regarder si le fragment est deja charge
            ListFragment frgm = (ListFragment) manager.findFragmentByTag(Fragments);
            if (frgm == null)
                frgm = new ListFragment();
            // ajout sur la view
            manager.beginTransaction().add(R.id.picture_hero, frgm).commit();
        }
    }

    @Override
    public void displayHero(int pos) {
        imageStats = findViewById(R.id.hero);
        Picasso.get().load(hero.getStatsUrl()).into(imageStats);
        imageStats.setDrawingCacheEnabled(true); //plus d'une heure de recherche pour cette ligne*
        nomHero = findViewById(R.id.nomHero);
        nomHero.setText(hero.getName());
    }
}