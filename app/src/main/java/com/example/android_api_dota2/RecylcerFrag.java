package com.example.android_api_dota2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Fragment qui permet de contenir une liste RecyclerView mais son initialistation se fait dans le mainActivity
public class RecylcerFrag extends Fragment {
    protected int layout;
    protected RecyclerView list_items;
    protected RecyclerView.Adapter adaptater;
    boolean firstCall = true;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerFragCB parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // chargement des donnees du xml associe
        View view = inflater.inflate(layout, container, false);
        // initialisation du recyclerview
        list_items = view.findViewById(R.id.list_heroes);
        list_items.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        list_items.setLayoutManager(layoutManager);
        list_items.setAdapter(null);
        return view;
    }

    // l'adaptateur se detache lorsque l'on met en backstack le fragment
    @Override
    public void onResume() {
        super.onResume();
        if (!firstCall)
            list_items.setAdapter(adaptater);
        firstCall = false;
    }

    // association avec l'interface pour pouvoir faire appel a la fonction du main
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        parent = (RecyclerFragCB) getActivity();
    }

    protected void watchDetails(Heroes hero) {
        parent.watchDetails(hero);
    }
}
