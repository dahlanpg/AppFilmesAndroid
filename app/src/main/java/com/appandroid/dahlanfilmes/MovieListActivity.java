package com.appandroid.dahlanfilmes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.appandroid.dahlanfilmes.adapters.MovieRecyclerView;
import com.appandroid.dahlanfilmes.adapters.OnMovieListener;
import com.appandroid.dahlanfilmes.models.MovieModel;
import com.appandroid.dahlanfilmes.viewmodels.MovieListViewModel;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {
    // RecyclerView
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;

    // ViewModel
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();
        searchMovieApi("fast", 1);
    }

    // Observando qualquer mudança de data
    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observar
                if (movieModels != null){
                    for (MovieModel movieModel: movieModels){
                        // Get data log
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }
    // 4- Chamando o método em Main Activity
    private void searchMovieApi(String query, int pageNumber){
        movieListViewModel.searchMovieApi(query, pageNumber);
    }

    // 5- Inicializando o recyclerView e adicionando dados nele
    private void ConfigureRecyclerView(){
        // Live Data não pode ser passado via construtor
        movieRecyclerAdapter = new MovieRecyclerView( this);
        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void onMovieClick(int position) {
        Toast.makeText(this, "The Position "  +position, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCategoryClick(String category) {
    }






}

