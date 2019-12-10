package com.example.android_api_dota2;


import android.app.Activity;
import android.support.v4.app.Fragment;

public class PictureHero extends Fragment {
    private PictureHeroCB parent;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parent = (PictureHeroCB) activity;
        System.out.println("C'est bon!!");
    }

    public void displayHero (int pos){
        parent.displayHero(pos);
    }
}
