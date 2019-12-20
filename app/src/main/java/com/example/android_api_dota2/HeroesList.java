package com.example.android_api_dota2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HeroesList extends Fragment {
    public MainActivity parentActivity;
    public SharedPreferences sharedPreferences;
    private View view;
    private RecyclerView list_heroes;
    private RecyclerView.Adapter adaptateur;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // chargement des donnees du xml associe
        view = inflater.inflate(R.layout.heroes_list, container, false);
        return view;
    }
}
