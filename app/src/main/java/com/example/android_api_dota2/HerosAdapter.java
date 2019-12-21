package com.example.android_api_dota2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class HerosAdapter extends android.support.v7.widget.RecyclerView.Adapter<HerosAdapter.ViewHolder> {
    private RecylcerFrag vmain;
    private Heroes[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just secondLine a string in this case
        public ImageView picture;
        public TextView name, attackType, roles;
        private Context context;

        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            picture = v.findViewById(R.id.picture);
            name = v.findViewById(R.id.name);
            attackType = v.findViewById(R.id.attackType);
            roles = v.findViewById(R.id.role);
            v.setOnClickListener(this);
        }

        //On change les attributs du contexte pour lancer la nouvelle activite
        @Override
        public void onClick(View v) {
            int position = this.getPosition();
            vmain.watchDetails(mDataset[position]);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HerosAdapter(RecylcerFrag vmain, List<Heroes> myDataset){
        this.vmain = vmain;
        mDataset = myDataset.toArray(new Heroes[0]);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Heroes hero = mDataset[pos];
        String url = hero.getImgUrl();
        if (url != null && !url.isEmpty()){
            Picasso.get().load(hero.getImgUrl()).into(holder.picture);
            holder.name.setText(hero.localized_name);
            holder.attackType.setText(hero.getAttackType());
            holder.roles.setText(hero.getRoles());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}