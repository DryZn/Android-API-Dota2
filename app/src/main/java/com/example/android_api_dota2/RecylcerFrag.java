package com.example.android_api_dota2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

// Fragment qui permet de contenir une liste RecyclerView mais son initialistation se fait dans le mainActivity
public class RecylcerFrag extends Fragment {
    private View view;
    protected int layout;
    protected RecyclerView list_heroes;
    protected RecyclerView.Adapter adaptateur;
    boolean firstCall = true;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerFragCB parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // chargement des donnees du xml associe
        view = inflater.inflate(layout, container, false);


        list_heroes = view.findViewById(R.id.list_heroes);
        list_heroes.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list_heroes.setLayoutManager(layoutManager);
        list_heroes.setAdapter(null);

        return view;
    }

    // l'adaptateur se detache lorsque l'on met en backstack le fragment
    /*@Override
    public void onResume() {
        super.onResume();
        System.out.println("ON RESUME");
        if (!firstCall)
            list_heroes.setAdapter(adaptateur);
        firstCall = false;
    }*/

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        parent = (RecyclerFragCB) getActivity();
    }

    public void watchDetails(Heroes hero) {
        parent.watchDetails(hero);
    }
}
