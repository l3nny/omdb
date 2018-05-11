package com.example.yotis.omdb.repository;

import android.content.Context;


public abstract class Repository {

    private BaseRepository repository;

    public Repository() {
        this.repository = new RepositoryHttp();
    }

    public void get( Promise p, String url, Context ctx) {

        this.repository.get( p, this, url, ctx);
    }

    public void getlist( Promise p, String url, Context ctx) {

        this.repository.getlist( p, this, url, ctx);
    }

    public void post(Promise p, Context ctx, String url) {
        this.repository.post(p, this, ctx, url);
    }

    public void postall(Promise p, Context ctx, String url) {
        this.repository.postall(p, this, ctx, url);
    }


    public void put(Promise p, Context ctx, String url) {

        this.repository.put(p, this, ctx, url);
    }

    public void putall(Promise p, Context ctx, String id) {

        this.repository.putall(p, this, ctx, id);
    }

    public void getAll(Promise p, String url, Context ctx) {

        this.repository.getAll(p, this, url, ctx);
    }
    public void getAlllist(Promise p, String url, Context ctx) {

        this.repository.getAlllist(p, this, url, ctx);
    }


    public BaseRepository getRepository() {
        return repository;
    }

    public void setRepository(BaseRepository repository) {
        this.repository = repository;
    }
}
