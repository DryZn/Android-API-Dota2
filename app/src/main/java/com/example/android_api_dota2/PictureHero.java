package com.example.android_api_dota2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class PictureHero extends Fragment {
    public static Heroes hero;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picture_hero, container, false);
        TextView heroName = view.findViewById(R.id.nomHero);
        heroName.setText(hero.localized_name);
        ImageView imageStats = view.findViewById(R.id.hero);
        Picasso.get().load(hero.getImgUrl()).into(imageStats);
        imageStats.setDrawingCacheEnabled(true); //plus d'une heure de recherche pour cette ligne*
        TextView heroAttackType = view.findViewById(R.id.attackType);
        heroAttackType.setText(hero.getAttackType());
        TextView heroRoles = view.findViewById(R.id.roles);
        heroRoles.setText(hero.getRoles());
        TextView heroAttrib = view.findViewById(R.id.attributes);
        heroAttrib.setText(hero.attack_type);
        return view;
    }
}
