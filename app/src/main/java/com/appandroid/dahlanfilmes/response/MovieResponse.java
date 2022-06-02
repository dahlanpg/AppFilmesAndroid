package com.appandroid.dahlanfilmes.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.appandroid.dahlanfilmes.models.MovieModel;

// Será utilizada para uma unica requisição de filme
public class MovieResponse {
    // 1- Encontrando o objeto movie

    @SerializedName("results")
    @Expose
    private MovieModel movie;
    public MovieModel getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
