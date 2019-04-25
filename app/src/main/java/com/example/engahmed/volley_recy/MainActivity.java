package com.example.engahmed.volley_recy;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.widget.SearchView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private List<Movie> movieList ;
    private boolean isFragmentLoaded = false;
    View vTabSatu;
    public static List<Movie> movies = new ArrayList<Movie>();
    private Adapter_Fantasia adapter_fantasia;
    RecyclerView recycler;
    private ProgressDialog pDialog;
    private static final String url = "https://api.androidhive.info/json/movies.json";
    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        recycler = findViewById(R.id.recy);
        movieList = new ArrayList<>();
        adapter_fantasia = new Adapter_Fantasia(this, movieList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(mLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
      //  recycler.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recycler.setAdapter(adapter_fantasia);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(this,2));

    //    whiteNotificationBar(recycler);


        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();




        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Movie movie = new Movie();
                                 movie.setTitle(obj.getString("title"));
                                  movie.setThumbnailUrl(obj.getString("image"));
                                  movie.setRating(((Number) obj.get("rating"))
                                      .doubleValue());
                                movie.setYear(obj.getInt("releaseYear"));
                              //  Log.e(TAG,"yyyyyyyyy"+movie.getYear());


                                // Genre is json array
                                JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                movie.setGenre(genre);

                                // adding movie to movies array
                                movieList.add(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter_fantasia.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

        sv=(SearchView)findViewById(R.id.search);


    }

    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu,menu);


        MenuItem seachItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(seachItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("myApp", "onQueryTextSubmit ");


             //  adapter_fantasia.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newtext) {
                Log.d("myApp", "onQueryTextChange ");

                final List<Movie> filtermodelist=filtter(movieList,newtext);
                adapter_fantasia.setfiltter(filtermodelist);

              //  adapter_fantasia.getFilter().filter(newtext);


                return false;
            }
        });


        return true;
    }



    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }




    private List<Movie> filtter(List<Movie> pl,String quary){

        quary=quary.toLowerCase();
        final List<Movie> filttermodel=new ArrayList<>();
        for (Movie model :pl){

            final String text=model.getTitle().toLowerCase();
            if (text.startsWith(quary)){

                filttermodel.add(model);
            }


        }

        return filttermodel;
    }


    /*        class AsyncFetch extends AsyncTask<String, String, String> {
            Context context;

            ProgressDialog pDialog = new ProgressDialog(context);

            HttpURLConnection conn;
            URL url = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog.setMessage("Loading  ...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                try {

                    // Enter URL address where your json file resides
                    // Even you can make call to php file which returns json data
                    url = new URL(AppConfig.URL_FILMS);

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return e.toString();
                }
                try {

                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");

                    // setDoOutput to true as we recieve data from json file
                    conn.setDoOutput(true);

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    return e1.toString();
                }
                try {

                    int response_code = conn.getResponseCode();

                    // Check if successful connection made
                    if (response_code == HttpURLConnection.HTTP_OK) {

                        // Read data sent from server
                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        // Pass data to onPostExecute method
                        return (result.toString());

                    } else {

                        return ("unsuccessful");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return e.toString();
                } finally {
                    conn.disconnect();
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                JsonArrayRequest movieReq = new JsonArrayRequest(AppConfig.URL_FILMS,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                        /*
                        if (response == null) {
                            Toast.makeText(getActivity(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                       */

/*
                                Log.i(TAG, "succcccccccc" + response.toString().equals("title"));

                                // hidePDialog();

                                // Parsing json
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject obj = response.getJSONObject(i);
                                        Movie movie = new Movie();
                                        movie.setTitle(obj.getString("title"));
                                        movie.setThumbnailUrl(obj.getString("image"));
                                        movie.setRating(((Number) obj.get("rating"))
                                                .doubleValue());
                                        movie.setYear(obj.getInt("releaseYear"));


                                        Log.e(TAG, "imageeeeee" + movie.getThumbnailUrl());
                                        Log.e(TAG, "tittle" + movie.getTitle());
                                        Log.e(TAG, "rating" + movie.getRating());
                                        Log.e(TAG, "releaseYear" + movie.getYear());

                                        // Genre is json array
                                        JSONArray genreArry = obj.getJSONArray("genre");
                                        ArrayList<String> genre = new ArrayList<String>();
                                        for (int j = 0; j < genreArry.length(); j++) {
                                            genre.add((String) genreArry.get(j));
                                        }
                                        movie.setGenre(genre);
                                        // adding movie to movies array
                                        movieList.add(movie);
                                        adapter_fantasia.setItems(movieList);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated data


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Errorrrrrrrrrrrrrrrrrrr: " + error.getMessage());


                    }
                });

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(movieReq);

            }
        }
    */}


