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

import java.util.List;

// Fragment qui permet de contenir une liste RecyclerView mais son initialistation se fait dans le mainActivity
public class RecyclerFrag extends Fragment implements SearchOptionsCB{
    protected RecyclerView list_items;
    protected RecyclerView.Adapter adaptater;
    protected List<?> data;
    boolean firstCall = true;
    private RecyclerFragCB parent;
    protected int fragSearchTagID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        if (savedInstanceState == null) {
            // initialisation du recyclerview avec un adaptateur vide avant que l'on ne recoive les donnees
            list_items = view.findViewById(R.id.list_heroes);
            list_items.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            list_items.setLayoutManager(layoutManager);
            list_items.setAdapter(null);
            // initialisation du fragment de recherche
            initSearchFrag();
        }
        return view;
    }

    private void initSearchFrag() {
        String fragSearchTag = getResources().getString(fragSearchTagID);
        FragmentManager manager = getChildFragmentManager();
        Fragment  searchFrag = manager.findFragmentByTag(fragSearchTag);
        if (searchFrag == null) {
            // l'identifier de fragment d'options de recherches sert a cela
            switch (fragSearchTagID) {
                case R.string.frag_search_abilities:
                    searchFrag = new SearchAbilities();
                    break;
                case R.string.frag_search_community:
                    System.out.println("okay");
                    searchFrag = new SearchCommunity();
                    break;
            }
            manager.beginTransaction().add(R.id.search_options, searchFrag, fragSearchTag).commit();
        } else {
            manager.popBackStackImmediate(fragSearchTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().show(searchFrag).commit();
    }
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

    protected void watchDetails(Object object) {
        parent.watchDetails(object);
    }

    // lorsque l'on change les filtres de recherche
    @Override
    public void filterData(String sortType) {
        try {
            List<Heroes> data = (List<Heroes>) this.data;
            //List<Heroes> data = Arrays.asList(((HerosAdapter) adaptater).mDataset);
            /*if ((dataFiltered == null) || (dataFiltered.size() == 0))
                dataFiltered = new ArrayList<>(data);
            dataFiltered = ControllerAPI.filterArray(data, dataFiltered, sortType, getResources());*/
            // cette ligne represente beaucoup d'efforts elle aussi
            data = ControllerAPI.filterArray(data, sortType, getResources());
            adaptater = new HerosAdapter(this, data);
            list_items.setAdapter(adaptater);
        } catch (Exception e) {}
    }
}
