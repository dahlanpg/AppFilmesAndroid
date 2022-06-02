package com.appandroid.dahlanfilmes.request;



import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.appandroid.dahlanfilmes.AppExecutors;
import com.appandroid.dahlanfilmes.models.MovieModel;
import com.appandroid.dahlanfilmes.response.MovieSearchResponse;
import com.appandroid.dahlanfilmes.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    // LiveData
    private MutableLiveData<List<MovieModel>> mMovies ;
    private static MovieApiClient instance;

    // Global Runnable
    private RetrieveMoviesRunnable retrieveMoviesRunnable;





    public static MovieApiClient getInstance(){
        if (instance == null){
            instance = new MovieApiClient();
        }
        return  instance;

    }

    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
    }


    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }





    //1- Esse será o método chamado entre as classes _ MVVM
    public void searchMoviesApi(String query, int pageNumber) {

        if (retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelar a chamada retrofit
                myHandler.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);


    }




    // Recebendo os dados da restAPI por meio da classe runnable
    private class RetrieveMoviesRunnable implements Runnable{
        private String query;
        private int pageNumber;
        boolean cancelRequest;


        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            // Pegando os objetos de resposta
            try{
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if (pageNumber == 1){
                        // Enviando data para live data
                        // PostValue: Usado nas background threads
                        // setValue: Para as Não - background thread
                        mMovies.postValue(list);
                    }else{
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }else{
                    String error = response.errorBody().string();
                    Log.v("Tagy", "Error " +error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }

        // Será usado para o Method/Query de pesquisa
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
                    return Servicey.getMovieApi().searchMovie(
                            Credentials.API_KEY,
                            query,
                            pageNumber

                    );
        }
        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request" );
            cancelRequest = true;
        }
    }
}
