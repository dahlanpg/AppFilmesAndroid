package com.appandroid.dahlanfilmes.viewmodels;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.appandroid.dahlanfilmes.models.MovieModel;
import com.appandroid.dahlanfilmes.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    // this class is used for VIEWMODEL


    private MovieRepository movieRepository;


    // Constructor
    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();

    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }



    // 3- Calling method in view-model
    public void searchMovieApi(String query, int pageNumber){
        movieRepository.serachMovieApi(query, pageNumber);
    }

}
