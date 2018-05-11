package com.example.yotis.omdb;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.yotis.omdb.dateBase.DB;
import com.example.yotis.omdb.logic.DynamicTable;
import com.example.yotis.omdb.models.movie;
import com.example.yotis.omdb.models.movieSearch;
import com.example.yotis.omdb.repository.Promise;
import com.example.yotis.omdb.repository.models.ErrorModel;
import com.example.yotis.omdb.util.Constants;
import com.example.yotis.omdb.util.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Search extends AppCompatActivity implements View.OnClickListener {
    Context ctx;
    movieSearch m;
    EditText t1, t2;
    Spinner sp;
    String url;
    Button b;
    DB base;
    DynamicTable table;
    LinearLayout panel_traslucido, panel_loading;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        base = new DB(this);
        base.open();

        panel_traslucido = (LinearLayout) findViewById(R.id.panel_traslucido);
        panel_loading = (LinearLayout) findViewById(R.id.panel_loading);
        table = new DynamicTable(this, (TableLayout) findViewById(R.id.table));

        t1 = (EditText) findViewById(R.id.editText);
        t2 = (EditText) findViewById(R.id.editText2);
        sp = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        b = (Button) findViewById(R.id.button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            b.setBackground(getResources().getDrawable(R.drawable.button_blue1));
            t1.setBackground(getResources().getDrawable(R.drawable.button_blue1));
            t2.setBackground(getResources().getDrawable(R.drawable.button_blue1));
            sp.setBackground(getResources().getDrawable(R.drawable.button_blue1));
            // sp.setBackground(getResources().getDrawable(R.drawable.button_fa));
        }
        m = new movieSearch();
        b.setOnClickListener(this);

        panel_traslucido.setOnClickListener(this);
        panel_traslucido.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public boolean onTouch(View v, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;

                    case MotionEvent.ACTION_MOVE:
                        break;

                    case MotionEvent.ACTION_OUTSIDE:
                        if (Build.VERSION.SDK_INT >= 11)

                            break;

                    case MotionEvent.ACTION_UP:

                        break;
                }
                return true;
            }
        });


    }

    @Override
    public void finish() {
        super.finish();
    }


    @Override
    public void onClick(View v) {
        m.setT(t1.getText().toString());
        m.setY(t2.getText().toString());
        m.setPlot(sp.getSelectedItem().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            b.setBackground(getResources().getDrawable(R.drawable.button_blue1));
        }

        if (t1.getText().length() == 0 && t2.getText().length() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                b.setBackground(getResources().getDrawable(R.drawable.button_blue1));
            }
            Toast.makeText(this, "You must to fill at least one parameter to search", Toast.LENGTH_SHORT).show();

        } else if (t1.getText().length() > 0 && t2.getText().length() > 0) {
            url = Constants.DATA_REQUEST + "?t=" + m.getT() + "&y=" + m.getY() + "&apikey=" + Constants.APIKEY;
            if (m.getPlot().toString() == "Full") {
                url = Constants.DATA_REQUEST + "?t=" + m.getT() + "&y=" + m.getY() + "&plot=full&apikey=" + Constants.APIKEY;
            }
            get();
        } else if (t1.getText().length() == 0 && t2.getText().length() > 0) {
            url = Constants.DATA_REQUEST + "?y=" + m.getY() + "&apikey=" + Constants.APIKEY;
            if (m.getPlot().toString() == "Full") {
                url = Constants.DATA_REQUEST + "?y=" + m.getY() + "&plot=full&apikey=" + Constants.APIKEY;
            }
            get();
        } else if (t1.getText().length() > 0 && t2.getText().length() == 0) {
            url = Constants.DATA_REQUEST + "?t=" + m.getT() + "&apikey=" + Constants.APIKEY;
            if (m.getPlot().toString() == "Full") {
                url = Constants.DATA_REQUEST + "?t=" + m.getT() + "&plot=full&apikey=" + Constants.APIKEY;
            }
            get();
        }

//que primero lo busque en la base de datos y que si no lo encuentra entonces intente buscarlo en el api
        /*
        if (base.emptyTable()) {
            if (tools.isConnect(ctx)) {
                get();
            } else {
                Toast.makeText(ctx, "Error: You must have internet to search", Toast.LENGTH_SHORT).show();
            }
        } else {
            //mostrar lo que tenga en la base de datos
        }*/

    }

    public void get() {
        panel_loading.setVisibility(View.VISIBLE);
        panel_traslucido.setVisibility(View.VISIBLE);

        m.getAll(new Promise() {
            InputStream is = null;
            Bitmap bm = null;
            ArrayList<String> pru;
            byte[] bitmapdata;


            @Override
            public void success(movie Response) {
                pru = new ArrayList<>();
                pru.add(Response.getTitle());
                pru.add("Genre: " + Response.getGenre());
                pru.add("Year: " + Response.getYear());
                pru.add("Country: " + Response.getCountry());
                pru.add("Languaje: " + Response.getLanguage());
                pru.add("Type: " + Response.getType());
                pru.add("Runtime: " + Response.getRuntime());
                pru.add("Actors: " + Response.getActors());
                pru.add("Director: " + Response.getDirector());
                pru.add("Plot: " + Response.getPlot());
                pru.add("Awards: " + Response.getAwards());
                pru.add("Box Office: " + Response.getBoxOffice());
                pru.add("DVD: " + Response.getDVD());
                pru.add("Production: " + Response.getProduction());
                pru.add("Website: " + Response.getWebsite());
                pru.add("Rated: " + Response.getRated());
                pru.add("Relased: " + Response.getReleased());
                pru.add("Writer: " + Response.getWriter());
                pru.add("imdbID: " + Response.getImdbID());
                pru.add("imdbRating: " + Response.getImdbRating());
                pru.add("ImdbVotes: " + Response.getImdbVotes());
                pru.add("Metascore: " + Response.getMetascore());
                for (int i = 0; i < Response.getRatings().size(); i++) {
                    pru.add(Response.getRatings().get(i).getSource() + ": " + Response.getRatings().get(i).getValue());
                }

                try {
                    is = (InputStream) new URL(Response.Poster).getContent();
                    bm = BitmapFactory.decodeStream(is);
                    ByteArrayOutputStream blob = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, blob);
                    bitmapdata = blob.toByteArray();

                /*
                    for (int i = 0; Response.Ratings.size() > i; i++) {
                        ratings += ", " + Response.Ratings.get(i).getSo.6;urce() + " : " + Response.Ratings.get(i).getValue();
                    }

                    base.insertMovies(Response.Title, Response.Year, Response.Rated, Response.Released, Response.Runtime, Response.Genre, Response.Director, Response.Writer, Response.Actors, Response.Plot, Response.Language, Response.Country, Response.Awards, ratings, Response.Metascore, Response.imdbRating, Response.imdbVotes, Response.imdbID, Response.Type, Response.DVD, Response.BoxOffice, Response.Production, Response.Website, bitmapdata);

                */

                } catch (
                        Exception e)

                {
                    e.printStackTrace();
                }

                for (int i = 0; i < pru.size(); i++) {
                    if (pru.get(i).toCharArray().length > 50) {
                        int pro = pru.get(i).toCharArray().length / 2;
                        String n1 = pru.get(i).substring(0, pro);
                        String n2 = pru.get(i).substring(pro, pru.get(i).toCharArray().length);
                        pru.add(n1);
                        pru.add(n2);
                        pru.remove(pru.get(i));
                    }
                }

                showResult(pru, bitmapdata);

            }

            @Override
            public void error(ErrorModel message) {
                Toast.makeText(ctx, "Error" + message.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        }, url, ctx);

    }

    public void showResult(final ArrayList<String> Response, final byte[] bitmapdata) {

        ctx = this;
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(5000);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Bitmap bitmap;
                                    bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                                    table.addRowImage(bitmap);
                                    table.addRowName(Response);
                                }
                            });

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    panel_loading.setVisibility(View.INVISIBLE);
                    panel_traslucido.setVisibility(View.INVISIBLE);
                }

                ;
            };
            thread.start();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
