package com.example.yotis.omdb.repository;


import com.example.yotis.omdb.models.movie;
import com.example.yotis.omdb.repository.models.ErrorModel;

public interface Promise {

    public void success(movie Response);
    public void error(ErrorModel message);

}
