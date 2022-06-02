package com.appandroid.dahlanfilmes.repositories;

import androidx.lifecycle.LiveData;

import com.appandroid.dahlanfilmes.models.MovieModel;
import com.appandroid.dahlanfilmes.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    // Essa classe atuará como nosso repositório _ Padrão MVVM

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;


    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;

    }

    private MovieRepository(){

        movieApiClient = MovieApiClient.getInstance();
    }




    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }

    // 2- Calling the method in repository
    public void serachMovieApi(String query, int pageNumber){
        movieApiClient.searchMoviesApi(query, pageNumber);
    }





}




