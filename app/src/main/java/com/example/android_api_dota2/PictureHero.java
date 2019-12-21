package com.example.android_api_dota2;


import android.app.Activity;
import android.content.Context;
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
    public static Heroes hero;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picture_hero, container, false);
        ImageView imageStats = view.findViewById(R.id.hero);
        Picasso.get().load(hero.getImgUrl()).into(imageStats);
        imageStats.setDrawingCacheEnabled(true); //plus d'une heure de recherche pour cette ligne*
        TextView heroName = view.findViewById(R.id.nomHero);
        heroName.setText(hero.localized_name);
        return view;
    }
}
