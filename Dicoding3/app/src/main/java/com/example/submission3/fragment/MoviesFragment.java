package com.example.submission3.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.submission3.adapter.MovieAdapter;
import com.example.submission3.model.Movie;
import com.example.submission3.viewModel.MoviesViewModel;
import com.example.submission3.R;
import java.util.ArrayList;
import java.util.Objects;

public class MoviesFragment extends Fragment {
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private MoviesViewModel moviesViewModel;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new MovieAdapter();
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progressBar);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getMovie().observe(this, getMovie);
        moviesViewModel.setMovies();

        showLoading(true);
        setHasOptionsMenu(true);
        return view;
    }
    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setData(movies);
            }
            showLoading(false);
        }
    };

    private void showLoading(boolean b) {
        if(b){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) (menu.findItem(R.id.action_search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint_movie));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    moviesViewModel.getSearchMovies().observe(Objects.requireNonNull(getActivity()), getSearchMovie);
                    moviesViewModel.setSearchMovie(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText)
                {
                    return false;
                }
            });
        }

        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                moviesViewModel.setMovies();
                return true;
            }
        });
    }
    private final Observer<ArrayList<Movie>> getSearchMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movie) {
            if (movie != null) {
                adapter.setData(movie);
                showLoading(false);
                if (movie.size() == 0)
                    Toast.makeText(getContext(), "No Result", Toast.LENGTH_SHORT).show();
            } else {
                showLoading(false);
            }
        }
    };
}

