package com.example.yotis.omdb.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.yotis.omdb.models.movie;
import com.example.yotis.omdb.repository.models.ErrorModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.Collection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by lenny.becerra on 23/06/2016.
 */

public class RepositoryHttp implements BaseRepository {
    String url;
    MediaType MediaType;


    @Override
    public void get(final Promise p, final Repository repo, final String url, final Context ctx) {

        MediaType = MediaType.parse("application/json; charset=utf-8");

        AsyncTask<Object, Response, Response> t = new AsyncTask<Object, Response, Response>() {
            protected Response doInBackground(Object... params) {

                try {
                    OkHttpClient httpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .get()
                            .addHeader("Cache-Control", "no-cache")
                            .build();
                    return httpClient.newCall(request).execute();
                } catch (Exception e) {
                    Log.d(e.getMessage(), "error");
                    p.error(new ErrorModel(0, e.getMessage()));
                    return null;

                }
            }


            @Override
            protected void onPostExecute(Response result) {

                if (result.code() == 201 || result.code() == 200) {

                    Gson gson = new Gson();

                    try {


                        result.body().close();

                    } catch (Exception e) {
                        Log.d(e.getMessage(), "error");
                    }
                } else {
                    p.error(new ErrorModel(result.code(), result.message()));
                }
            }

        }.execute(repo);
    }

    @Override
    public void getlist(final Promise p, final Repository repo, final String url, final Context ctx) {

        MediaType = MediaType.parse("application/json; charset=utf-8");

        AsyncTask<Object, Response, Response> t = new AsyncTask<Object, Response, Response>() {
            protected Response doInBackground(Object... params) {

                try {
                    OkHttpClient httpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url + repo.getClass().getSimpleName() + "s/")
                            .get()
                            .build();
                    return httpClient.newCall(request).execute();
                } catch (Exception e) {

                    Log.d(e.getMessage(), "error");
                    p.error(new ErrorModel(0, e.getMessage()));
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Response result) {

                if (result.code() == 201 || result.code() == 200) {

                    Gson gson = new Gson();

                    try {


                        result.body().close();


                    } catch (Exception e) {
                        Log.d(e.getMessage(), "error");
                    }
                } else {
                    p.error(new ErrorModel(result.code(), result.message()));
                }
            }

        }.execute(repo);
    }

    @Override
    public void getAll(final Promise p, final Repository repo, final String url, final Context ctx) {

        final int[] code = new int[1];
        Gson gson = new Gson();


        MediaType = MediaType.parse("application/json; charset=utf-8");

        AsyncTask<Object, Response, Response> t = new AsyncTask<Object, Response, Response>() {
            protected Response doInBackground(Object... params) {

                try {
                    Gson gson = new Gson();
                    OkHttpClient httpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .get()
                            .build();
                    Response result = httpClient.newCall(request).execute();

                    code[0] = result.code();

                  if (code[0] == 201 || code[0] == 200) {

                        try {

                            movie m = gson.fromJson(result.body().string(), movie.class);
                            p.success(m);
                            result.body().close();

                        } catch (Exception e) {
                             Log.d(e.getMessage(), "error");
                        }
                    } else {
                        p.error(new ErrorModel(code[0], result.message()));
                    }
                } catch (final ConnectException e) {

                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Toast.makeText(ctx, "Hay problemas de conexion con el servidor porfavor disculpanos:" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, 1000);
                    Log.d(e.getMessage(), "error");

                } catch (Exception e) {
                    Log.d(e.getMessage(), "error");
                    p.error(new ErrorModel(code[0], e.getMessage()));

                }
                return null;
            }
        }
                .execute(repo);
    }

    @Override
    public void getAlllist(final Promise p, final Repository repo, final String url, final Context ctx) {

        final int[] code = new int[1];
        Gson gson = new Gson();


        MediaType = MediaType.parse("application/json; charset=utf-8");

        AsyncTask<Object, Response, Response> t = new AsyncTask<Object, Response, Response>() {
            protected Response doInBackground(Object... params) {

                try {
                    Gson gson = new Gson();
                    OkHttpClient httpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .get()
                            .build();
                    Response result = httpClient.newCall(request).execute();

                    code[0] = result.code();

                    if (code[0] == 201 || code[0] == 200) {

                        try {


                            result.body().close();

                        } catch (Exception e) {
                            Log.d(e.getMessage(), "error");
                        }
                    } else {
                        p.error(new ErrorModel(code[0], result.message()));
                    }
                } catch (final ConnectException e) {
                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Toast.makeText(ctx, "Hay problemas de conexion con el servidor porfavor disculpanos:" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, 1000);
                    Log.d(e.getMessage(), "error");


                } catch (Exception e) {

                    Log.d(e.getMessage(), "error");
                    p.error(new ErrorModel(code[0], e.getMessage()));

                }
                return null;
            }
        }
                .execute(repo);
    }


    @Override
    public void put(final Promise p, final Repository repo, final Context ctx,
                    final String url) {
        MediaType = MediaType.parse("application/json; charset=utf-8");
        final int[] code = new int[1];

        AsyncTask<Object, Response, Response> t = new AsyncTask<Object, Response, Response>() {
            protected Response doInBackground(Object... params) {

                try {
                    Gson gson = new Gson();
                    OkHttpClient httpClient = new OkHttpClient();
                    RequestBody body = RequestBody.create(MediaType, gson.toJson(params[0]));
                    Request request = new Request.Builder()
                            .url(url + repo.getClass().getSimpleName().toLowerCase() + "s/")
                            .put(body)
                            .build();
                    Response result = httpClient.newCall(request).execute();

                    code[0] = result.code();
                    if (result.code() == 201 || result.code() == 200) {

                        try {


                            result.body().close();


                        } catch (Exception e) {

                            Log.d(e.getMessage(), "error");
                        }
                    } else {
                        p.error(new ErrorModel(result.code(), result.message()));
                    }
                } catch (final ConnectException e) {

                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Toast.makeText(ctx, "Hay problemas de conexion con el servidor porfavor disculpanos:" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, 1000);
                    Log.d(e.getMessage(), "error");


                } catch (Exception e) {

                    Log.d(e.getMessage(), "error");
                    p.error(new ErrorModel(code[0], e.getMessage()));

                }
                return null;
            }

        }.execute(repo);
    }

    @Override
    public void putall(final Promise p, final Repository repo, final Context ctx,
                       final String url) {
        final int[] error = new int[1];
        MediaType = MediaType.parse("application/json; charset=utf-8");


        AsyncTask<Object, Response, Response> t = new AsyncTask<Object, Response, Response>() {
            protected Response doInBackground(Object... params) {

                try {
                    Gson gson = new Gson();
                    OkHttpClient httpClient = new OkHttpClient();
                    RequestBody body = RequestBody.create(MediaType, gson.toJson(params[0]));
                    Request request = new Request.Builder()
                            .url(url)
                            .put(body)
                            .build();
                    Response result = httpClient.newCall(request).execute();
                    error[0] = result.code();
                    if (result.code() == 201 || result.code() == 200) {

                        try {


                            result.body().close();
                        } catch (Exception e) {

                            Log.d(e.getMessage(), "error");
                        }
                    } else {
                        p.error(new ErrorModel(result.code(), result.message()));
                    }
                } catch (final ConnectException e) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Toast.makeText(ctx, "Hay problemas de conexion con el servidor porfavor disculpanos:" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, 1000);
                    Log.d(e.getMessage(), "error");
                } catch (Exception e) {

                    Log.d(e.getMessage(), "error");
                    p.error(new ErrorModel(error[0], e.getMessage()));
                }
                return null;
            }
        }.execute(repo);
    }

    @Override
    public void post(final Promise p, final Repository repo, final Context ctx,
                     final String url) {
        final int[] error = new int[1];

        MediaType = MediaType.parse("application/json; charset=utf-8");

        AsyncTask<Object, Response, Response> t = new AsyncTask<Object, Response, Response>() {

            protected Response doInBackground(Object... params) {
                try {
                    Gson gson = new Gson();
                    OkHttpClient httpClient = new OkHttpClient();
                    RequestBody body = RequestBody.create(MediaType, gson.toJson(params[0]));
                    Request request = new Request.Builder()
                            .url(url + repo.getClass().getSimpleName().toLowerCase() + "s/")
                            .post(body)
                            .build();
                    final Response result = httpClient.newCall(request).execute();
                    error[0] = result.code();
                    if (result.code() == 201 || result.code() == 200) {
                        try {


                            result.body().close();
                        } catch (Exception e) {

                            Log.d(e.getMessage(), "error");
                        }
                    } else {
                        p.error(new ErrorModel(result.code(), result.message()));
                    }

                } catch (final ConnectException e) {

                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Toast.makeText(ctx, "Hay problemas de conexion con el servidor porfavor disculpanos:" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, 1000);
                    Log.d(e.getMessage(), "error");
                } catch (Exception e) {

                    Log.d(e.getMessage(), "error");
                    p.error(new ErrorModel(error[0], e.getMessage()));
                }
                return null;
            }
        }.execute(repo);
    }

    @Override
    public void postall(final Promise p, final Repository repo, final Context ctx,
                        final String url) {
        final int[] code = new int[1];

        MediaType = MediaType.parse("application/json; charset=utf-8");

        AsyncTask<Object, Response, Response> t = new AsyncTask<Object, Response, Response>() {

            protected Response doInBackground(Object... params) {
                try {
                    Gson gson = new Gson();
                    OkHttpClient httpClient = new OkHttpClient();
                    RequestBody body = RequestBody.create(MediaType, gson.toJson(params[0]));
                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    final Response result = httpClient.newCall(request).execute();

                    code[0] = result.code();
                    if (code[0] == 201 || code[0] == 200) {
                        try {

                            result.body().close();
                        } catch (Exception e) {

                            Log.d(e.getMessage(), "error");
                        }
                    } else {
                        p.error(new ErrorModel(0, result.message()));
                    }
                } catch (final ConnectException e) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Toast.makeText(ctx, "Hay problemas de conexion con el servidor porfavor disculpanos:" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, 1000);
                    Log.d(e.getMessage(), "error");
                } catch (Exception e) {

                    Log.d(e.getMessage(), "error");
                    p.error(new ErrorModel(code[0], e.getMessage()));
                }
                return null;
            }
        }.execute(repo);

    }


}
