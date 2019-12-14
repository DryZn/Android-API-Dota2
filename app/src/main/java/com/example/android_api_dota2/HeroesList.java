package com.example.android_api_dota2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HeroesList extends Fragment {
    private RecyclerView list_heroes;
    private RecyclerView.Adapter adaptateur;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Reussi");
        // chargement des donnees du xml associe
        View view = inflater.inflate(R.layout.heroes_list, container, false);
        return view;
    }
}
