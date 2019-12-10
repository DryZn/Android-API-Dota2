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
    private Heroes[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is justsecondLine a string in this case
        public ImageView iconHero;
        public TextView nomHero;
        private Context context;
        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            iconHero = v.findViewById(R.id.iconHero);
            nomHero = v.findViewById(R.id.nomHero);
            v.setOnClickListener(this);
        }

        //On change les attributs du contexte pour lancer la nouvelle activité
        @Override
        public void onClick(View v) {
            int position = this.getPosition();
            Details.hero = mDataset[position];
            Intent intent = new Intent(context, Details.class);
            context.startActivity(intent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HerosAdapter(List<Heroes> myDataset){
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String url = mDataset[position].getImgUrl();
        if (url != null && !url.isEmpty()){
            Picasso.get().load(mDataset[position].getImgUrl()).into(holder.iconHero);
            holder.nomHero.setText(mDataset[position].getName());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}