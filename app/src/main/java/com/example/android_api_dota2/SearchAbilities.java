package com.example.android_api_dota2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

// Fragment qui permet d'afficher les options de recherches des heros concernant leurs competences
public class SearchAbilities extends Fragment implements AdapterView.OnItemSelectedListener {
    SearchOptionsCB parentFrag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.so_heroes_abilities, container, false);
        // Instanciation de trois spinners
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
        list_attackType.setOnItemSelectedListener(this);

        Spinner list_attributes = view.findViewById(R.id.list_attributes);
        ArrayAdapter<CharSequence>  adapterThird = ArrayAdapter.createFromResource(getActivity(), R.array.attack_types, R.layout.spinner_item);
        adapterThird.setDropDownViewResource(R.layout.spinner_dropdown_item);
        list_attributes.setAdapter(adapterThird);
        list_attributes.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        parentFrag = (SearchOptionsCB) getParentFragment();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // actualisation de la vue du spinner
        parent.setSelection(pos);
        // envoi du filtre selectionne
        parentFrag.filterData(parent.getSelectedItem().toString());
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // implementation obligatoire...
        }
}
