package com.example.evenement_app;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    Invites invites;
    List<Model> modelList;
    Context context;

    public CustomAdapter(Invites invites, List<Model> modelList) {
        this.invites = invites;
        this.modelList = modelList;

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
