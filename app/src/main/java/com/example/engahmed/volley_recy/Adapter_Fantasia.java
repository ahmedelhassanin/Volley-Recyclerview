package com.example.engahmed.volley_recy;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

public class Adapter_Fantasia extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Movie> movies, movieListfilter;
    private LayoutInflater layoutInflater;
    ArrayList<Movie> movieArrayList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    CustomFilter filter;


    public Adapter_Fantasia(Context context, List<Movie> movies) {

        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.movies = movies;
        this.movieListfilter = movies;


    }

//    public void setItems(List<Movie> movies) {
    //    movies = movies;
     //   notifyDataSetChanged();
  //  }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = layoutInflater.inflate(R.layout.list_row, viewGroup, false);
        View vv = layoutInflater.inflate(R.layout.row_diff, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        Adsholder adsholder = new Adsholder(vv);

        if (i == 456) {

            return adsholder;

        } else {

            return itemViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int viewtype = getItemViewType(i);
        if (viewtype == 456) {
            Adsholder adsholder = (Adsholder) viewHolder;
        } else {

            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;


            if (viewtype == 123) {

                viewHolder.itemView.setBackgroundColor(Color.parseColor("#23c486"));
            }


            Movie movie = movies.get(i);


            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();


            itemViewHolder.title.setText(movie.getTitle());
            itemViewHolder.thumbNail.setImageUrl(movie.getThumbnailUrl(), imageLoader);

            itemViewHolder.rating.setText(String.valueOf(movie.getRating()));
            itemViewHolder.year.setText(String.valueOf(movie.getYear()));


            String genreStr = "";
            for (String str : movie.getGenre()) {
                genreStr += str + ", ";
            }
            genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                    genreStr.length() - 2) : genreStr;

            itemViewHolder.genre.setText(genreStr);

            final Movie movie1 = movies.get(i);

            itemViewHolder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "your click on" + movie1.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });


        }

    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 2) {
            return 123;

        } else if (position == 7) {
            return 456;

        } else {


            return super.getItemViewType(position);

        }


    }

 /*   @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    movieListfilter = movies;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie row : filteredList) {


                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())||row.getGenre().contains(charSequence)
                                )
                               {
                            filteredList.add(row);
                        }
                    }

                    movieListfilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = movieListfilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                movieListfilter = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };



    }

    */



    public void setfiltter(List<Movie> movelistfiltter){

        movies=new ArrayList<>();
        movies.addAll(movelistfiltter);
        notifyDataSetChanged();
    }



    class Adsholder extends RecyclerView.ViewHolder{


        public Adsholder(@NonNull View itemView) {
            super(itemView);
        }
    }


}


