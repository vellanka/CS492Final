package com.example.android.lifecycleawaregithubsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.lifecycleawaregithubsearch.data.GitHubRepo;
import com.example.android.lifecycleawaregithubsearch.utils.GitHubUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GitHubSearchAdapter.OnSearchResultClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView searchResultsRV;
    private EditText searchBoxET;
    private ProgressBar loadingIndicatorPB;
    private TextView errorMessageTV;

    private GitHubSearchAdapter githubSearchAdapter;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.searchBoxET = findViewById(R.id.et_search_box);
        this.searchResultsRV = findViewById(R.id.rv_search_results);
        this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        this.errorMessageTV = findViewById(R.id.tv_error_message);

        this.searchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        this.searchResultsRV.setHasFixedSize(true);

        this.githubSearchAdapter = new GitHubSearchAdapter(this);
        this.searchResultsRV.setAdapter(this.githubSearchAdapter);

        this.requestQueue = Volley.newRequestQueue(this);

        Button searchButton = (Button)findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchBoxET.getText().toString();
                if (!TextUtils.isEmpty(searchQuery)) {
                    doGitHubSearch(searchQuery);
                }
            }
        });
    }

    private void doGitHubSearch(String query) {
        String url = GitHubUtils.buildGitHubSearchURL(query);
        Log.d(TAG, "searching with this URL: " + url);

        StringRequest req = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<GitHubRepo> searchResultsList = GitHubUtils.parseGitHubSearchResults(response);
                        githubSearchAdapter.updateSearchResults(searchResultsList);
                        loadingIndicatorPB.setVisibility(View.INVISIBLE);
                        searchResultsRV.setVisibility(View.VISIBLE);
                        errorMessageTV.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        loadingIndicatorPB.setVisibility(View.INVISIBLE);
                        searchResultsRV.setVisibility(View.INVISIBLE);
                        errorMessageTV.setVisibility(View.VISIBLE);
                    }
                }
        );

        loadingIndicatorPB.setVisibility(View.VISIBLE);
        this.requestQueue.add(req);
    }

    @Override
    public void onSearchResultClicked(GitHubRepo repo) {
        Log.d(TAG, "Search result clicked: " + repo.fullName);
        Intent intent = new Intent(this, RepoDetailActivity.class);
        intent.putExtra(RepoDetailActivity.EXTRA_GITHUB_REPO, repo);
        startActivity(intent);
    }
}