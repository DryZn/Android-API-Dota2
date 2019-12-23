package com.example.android_api_dota2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Fragment qui permet d'afficher les options de recherches des heros concernant leurs competences
public class SearchAbilities extends Fragment implements AdapterView.OnItemSelectedListener {
    protected int layout;
    protected RecyclerView list_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // chargement des donnees du xml associe
        View view = inflater.inflate(R.layout.so_heroes_abilities, container, false);
        Spinner list_roles = view.findViewById(R.id.list_roles);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.roles, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        list_roles.setAdapter(adapter);
        list_roles.setOnItemSelectedListener(this);
        Spinner list_attackType = view.findViewById(R.id.list_attackType);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.attack_types, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        list_attackType.setAdapter(adapter);

        Spinner list_attributes = view.findViewById(R.id.list_attributes);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.attack_types, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        list_attributes.setAdapter(adapter);
        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        parent.setSelection(pos);
        System.out.println("LAAAA");
        System.out.println(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
