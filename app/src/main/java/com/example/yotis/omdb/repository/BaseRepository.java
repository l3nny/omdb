package com.example.yotis.omdb.repository;

import android.content.Context;

/**
 * Created by lenny.becerra on 23/06/2016.
 */
public interface BaseRepository {

    public void get(Promise p, Repository repo, String url,Context ctx);

    public void getlist(Promise p, Repository repo, String url,Context ctx);

    public void getAll(Promise p, Repository repo, String url,Context ctx);

    public void getAlllist(Promise p, Repository repo, String url,Context ctx);

    public void put(Promise p, Repository repo, Context ctx, String url);

    public void putall(Promise p, Repository repo, Context ctx, String url);

    public void post(Promise p, Repository repo, Context ctx, String url);

    public void postall(Promise p, Repository repo, Context ctx, String url);



}
