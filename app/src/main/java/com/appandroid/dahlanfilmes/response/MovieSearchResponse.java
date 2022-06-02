package com.appandroid.dahlanfilmes.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.appandroid.dahlanfilmes.models.MovieModel;

import java.util.List;

// Utilizado para multiplos filmes (Movies list) - Categoria: Popular Movies - TMDB
public class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose()
    private int total_count;

    @SerializedName("results")
    @Expose()
    private List<MovieModel> movies;

    public int getTotal_count(){
        return total_count;
    }

    public List<MovieModel> getMovies(){
        return movies;

    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", movies=" + movies +
                '}';
    }
}
