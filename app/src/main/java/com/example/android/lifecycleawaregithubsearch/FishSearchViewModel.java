package com.example.android.lifecycleawaregithubsearch;

import com.example.android.lifecycleawaregithubsearch.data.FishData;
import com.example.android.lifecycleawaregithubsearch.data.FishSearchRepository;
import com.example.android.lifecycleawaregithubsearch.data.GitHubRepo;

import com.example.android.lifecycleawaregithubsearch.data.LoadingStatus;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class FishSearchViewModel extends ViewModel {
    private FishSearchRepository repository;
    //private GitHubSearchRepository repository;

    //private LiveData<List<GitHubRepo>> searchResults;

    private LiveData<List<FishData>> searchResults;
    private LiveData<LoadingStatus> loadingStatus;

    public FishSearchViewModel() {
       // this.repository = new GitHubSearchRepository();
        this.repository = new FishSearchRepository();
        this.searchResults = this.repository.getSearchResults();
        this.loadingStatus = this.repository.getLoadingStatus();
    }

    public LiveData<List<FishData>> getSearchResults() {
        return this.searchResults;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

    public void loadSearchResults(String query) {
        this.repository.loadSearchResults(query);
    }
}
