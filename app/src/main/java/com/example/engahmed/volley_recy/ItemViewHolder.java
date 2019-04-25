package com.example.engahmed.volley_recy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    NetworkImageView thumbNail ;
    TextView title ;
    TextView rating ;
    TextView genre ;
    TextView year ;
    Context c;


    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        thumbNail=itemView.findViewById(R.id.thumbnail);
        title=itemView.findViewById(R.id.title);
        rating=itemView.findViewById(R.id.rating);
        genre=itemView.findViewById(R.id.genre);
        year=itemView.findViewById(R.id.releaseYear);



    }


}
