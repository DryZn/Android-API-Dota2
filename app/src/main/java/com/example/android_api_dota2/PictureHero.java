package com.example.android_api_dota2;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PictureHero extends Fragment {
    private PictureHeroCB parent;
    public PictureHero fragPictHero;
    public static Heroes hero;
    private ImageView imageStats;
    private TextView heroName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picture_hero, container, false);
        imageStats = view.findViewById(R.id.hero);
        Picasso.get().load(hero.getImgUrl()).into(imageStats);
        imageStats.setDrawingCacheEnabled(true); //plus d'une heure de recherche pour cette ligne*
        heroName = view.findViewById(R.id.nomHero);
        heroName.setText(hero.localized_name);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parent = (PictureHeroCB) activity;
    }

    public void displayHero (int pos){
        parent.displayHero(pos);
    }
}
