/*
package com.example.evenement_app;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    Invites invites;
    List<Model> modelList;
    Context context;

    public CustomAdapter(Invites invites, List<Model> modelList) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout,viewGroup,false);


        ViewHolder viewHolder =new ViewHolder(itemView);
        //handle item clicks here
        viewHolder.SetOnClickListener(new ViewHolder.ClickListner() {
            @Override
            public void onItemClick(View view, int position) {
                //this will be called when user click item
                //show data in toast on clicking
                String invite = modelList.get(position).getInvite();
                Toast.makeText(invites,"invites : "+invite,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //this will be called when user long click item
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //bind views / set data
        viewHolder.mTitle.setText(modelList.get(i).getInvite());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}

*/
package com.example.evenement_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Model> modelList;
    private Context context;

    public CustomAdapter(Context context, List<Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(context).inflate(R.layout.model_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to views
        Model model = modelList.get(position);
        holder.mTitle.setText(model.getInvite());

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click
                Toast.makeText(context, "Invite: " + model.getInvite(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.invite); // Assuming "invite" is the ID of TextView in model_layout.xml
        }
    }
}
