package com.example.evenement_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView mTitle ;
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
                return true;
            }
        }
        );
        //initialize views avec model_layout.xml
        mTitle = itemView.findViewById(R.id.invite);
    }
    private  ViewHolder.ClickListner mClickListener;
    //interface for click listner
    public interface ClickListner{
        void onItemClick(View view , int position);
        void onItemLongClick(View view , int position);
    }
    public  void SetOnClickListener(ViewHolder.ClickListner clickListner){
        mClickListener = clickListner;
    }
}
