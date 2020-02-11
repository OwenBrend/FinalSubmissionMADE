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

import com.example.submission3.adapter.TvShowAdapter;
import com.example.submission3.R;
import com.example.submission3.model.TVShow;
import com.example.submission3.viewModel.TvShowViewModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment {
    private TvShowAdapter adapter;
    private ProgressBar progressBar;
    private TvShowViewModel tvShowViewModel;
    public TVShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        adapter = new TvShowAdapter();
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rc_tvShow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progressBar);

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvShow().observe(this, getTvShow);
        tvShowViewModel.setTVShow("EXTRA_TV_SHOW");

        setHasOptionsMenu(true);
        showLoading(true);

        return view;
    }
    private Observer<ArrayList<TVShow>> getTvShow = new Observer<ArrayList<TVShow>>() {
        @Override
        public void onChanged(ArrayList<TVShow> tvShows) {
            if (tvShows != null) {
                adapter.refill(tvShows);
            }

            showLoading(false);
        }
    };

    private void showLoading(boolean b){
        if(b){
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main_menu,menu);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity().getSystemService(Context.SEARCH_SERVICE));

        if(searchManager !=null){
            androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView)(menu.findItem(R.id.action_search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint_movie));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    tvShowViewModel.getSearchTvShow().observe(Objects.requireNonNull(getActivity()), getSearchTvShow);
                    tvShowViewModel.setSearchTvShow(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
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
                return true;
            }
        });
    }
    private final Observer<ArrayList<TVShow>> getSearchTvShow = new Observer<ArrayList<TVShow>>() {
        @Override
        public void onChanged(ArrayList<TVShow> tvShows) {
            if(tvShows !=null){
                adapter.refill(tvShows);
                showLoading(false);
                if(tvShows.size()==0){
                    Toast.makeText(getContext(),"No result",Toast.LENGTH_SHORT).show();
                }else {
                    showLoading(false);
                }
            }
        }
    };


}
