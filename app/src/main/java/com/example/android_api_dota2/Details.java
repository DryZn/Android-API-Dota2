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
    public String pictureheroTag = "pictureheroTag";
    public static Heroes hero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            // regarder si le fragment est deja charge
            PictureHero frgm = (PictureHero) manager.findFragmentByTag(pictureheroTag);
            if (frgm == null)
                frgm = new PictureHero();
            // Passage des arguments
            frgm.hero = this.hero;
            // ajout sur la view
            manager.beginTransaction().add(R.id.picture_hero, frgm).commit();
        }
    }

    @Override
    public void displayHero(int pos) {
        System.out.println("Impressionnant!");
    }
}