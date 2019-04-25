package com.example.engahmed.volley_recy;


import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {
    Adapter_Fantasia adapter_fantasia;
    ArrayList<Movie> movies;


    public CustomFilter(ArrayList<Movie> movies,Adapter_Fantasia adapter_fantasia){

        this.adapter_fantasia=adapter_fantasia;
        this.movies=movies;

    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        return null;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter_fantasia.movieArrayList= (ArrayList<Movie>) results.values;

        //REFRESH
        adapter_fantasia.notifyDataSetChanged();

    }
}
