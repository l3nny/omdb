package com.example.yotis.omdb.dateBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.yotis.omdb.models.movie;

import java.util.ArrayList;


public class DB {

    private SQLiteDatabase DB;
    private SQLiteHelper dbHelper;


    public DB(Context context) {
        dbHelper = SQLiteHelper.getInstance(context);
    }

    public void open() {
        DB = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertMovies(String title, String year, String rated, String released, String runtime, String genre, String director, String writer, String actors, String plot, String language, String country, String awards, String ratings, String metascore, String imdbrating, String imdbvotes, String imdbid, String type, String dvd, String boxoffice, String production, String website, byte[] image) {


        try {
            ContentValues cv = new ContentValues();
            cv.put("title", title);
            cv.put("year", year);
            cv.put("rated", rated);
            cv.put("released", released);
            cv.put("runtime", runtime);
            cv.put("genre", genre);
            cv.put("director", director);
            cv.put("writer", writer);
            cv.put("actors", actors);
            cv.put("plot", plot);
            cv.put("language", language);
            cv.put("image", image);
            cv.put("country", country);
            cv.put("image", image);
            cv.put("awards", awards);
            cv.put("image", image);
            cv.put("ratings", ratings);
            cv.put("metascore", metascore);
            cv.put("imdbrating", imdbrating);
            cv.put("imdbvotes", imdbvotes);
            cv.put("imdbid", imdbid);
            cv.put("type", type);
            cv.put("dvd", dvd);
            cv.put("boxoffice", boxoffice);
            cv.put("production", production);
            cv.put("website", website);
            cv.put("image", image);


            DB.insert("movie", null, cv);


        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }


    public void deleteAll() {
        try {
            DB.delete("movie", null, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public boolean emptyTable() {
        boolean flag;
        String quString = "select exists(select 1 from " + "movie" + ");";
        Cursor cursor = DB.rawQuery(quString, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count == 1) {
            flag = false;
        } else {
            flag = true;
        }
        cursor.close();
        DB.close();

        return flag;
    }

    public ArrayList<movie> listaClient() {
        movie f = new movie();
        ArrayList<movie> ff = new ArrayList<movie>();

        try {
            Cursor cursor = DB.rawQuery("SELECT name, url, image FROM client", null);

            if (cursor.moveToFirst()) {
                do {

                    f.setTitle(cursor.getString(0));
                    f.setImdbID(cursor.getString(1));
                    //f.setImage(cursor.getBlob(2));

                    ff.add(f);
                    f = new movie();


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ff;
    }
/*


    public Feader[] listCategory() {


        Cursor cursor = DB.rawQuery("SELECT category FROM apps GROUP BY category HAVING (COUNT(*) >= 1)", null);

        List<Feader> list = new ArrayList<Feader>();


        if (cursor.moveToFirst()) {
            do {
                list.add(new Feader(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        Feader apps[] = new Feader[list.size()];
        try {
            for (int i = 0; i < list.size(); i++) {

                apps[i] = ((Feader) list.get(i));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apps;

    }

    public ArrayList<Feader> information(String info) {

        Cursor cursor = DB.rawQuery("SELECT name, summary FROM apps WHERE urlImage ='" + info + "'", null);

        ArrayList<Feader> list = new ArrayList<Feader>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    list.add(new Feader(cursor.getString(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
*/
}
