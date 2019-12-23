package com.example.android_api_dota2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

// Fragment qui permet de contenir une liste RecyclerView mais son initialistation se fait dans le mainActivity
public class RecyclerFrag extends Fragment implements SearchOptionsCB{
    protected RecyclerView list_items;
    protected List<Heroes> data;
    protected RecyclerView.Adapter adaptater;
    boolean firstCall = true;
    private RecyclerFragCB parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // chargement des donnees du xml associe
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        // initialisation du recyclerview avec un adaptateur vide avant que l'on ne recoive les donnees
        list_items = view.findViewById(R.id.list_heroes);
        list_items.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        list_items.setLayoutManager(layoutManager);
        list_items.setAdapter(null);
        // initialisation du fragment de recherche
        SearchAbilities searchFrag = new SearchAbilities();
        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction().add(R.id.search_options, searchFrag, "searchFrag").commit();
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

    protected void watchDetails(Object hero) {
        parent.watchDetails(hero);
    }

    // lorsque l'on change les filtres de recherche
    @Override
    public void filterData(String sortType) {
        if (data != null) {
            // cette ligne represente beaucoup d'efforts elle aussi
            List<Heroes> newDisplayData = ControllerAPI.filterArray(data, sortType, getResources());
            adaptater = new HerosAdapter(this, newDisplayData);
            list_items.setAdapter(adaptater);
        }
    }
}
