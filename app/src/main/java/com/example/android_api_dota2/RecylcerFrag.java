package com.example.android_api_dota2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// Fragment qui permet de contenir une liste RecyclerView mais son initialistation se fait dans le mainActivity
public class RecylcerFrag extends Fragment {
    private View view;
    protected int layout;
    protected RecyclerView list_heroes;
    protected RecyclerView.Adapter adaptateur;
    boolean firstCall = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // chargement des donnees du xml associe
        view = inflater.inflate(layout, container, false);
        return view;
    }

    // l'adaptateur se detache lorsque l'on met en backstack le fragment
    @Override
    public void onResume() {
        super.onResume();
        System.out.println("ON RESUME");
        if (!firstCall)
            list_heroes.setAdapter(adaptateur);
        firstCall = false;
    }
}
