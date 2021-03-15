package com.example.android.lifecycleawaregithubsearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.lifecycleawaregithubsearch.data.FishData;
import com.example.android.lifecycleawaregithubsearch.data.GitHubRepo;

import java.util.List;

public class FishSearchAdapter extends RecyclerView.Adapter<FishSearchAdapter.FishItemViewHolder> {
    private List<FishData> searchResultsList;
    private FishSearchAdapter.OnSearchResultClickListener resultClickListener;

    interface OnSearchResultClickListener {
        void onSearchResultClicked(FishData data);
    }
    public FishSearchAdapter(OnSearchResultClickListener listener) {
        this.resultClickListener = listener;
    }

    public void updateSearchResults(List<FishData> searchResultsList) {
        this.searchResultsList = searchResultsList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (this.searchResultsList != null) {
            return this.searchResultsList.size();
        } else {
            return 0;
        }
    }
    @NonNull
    @Override
    public FishItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.search_result_item, parent, false);
        return new FishItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FishItemViewHolder holder, int position) {
        holder.bind(this.searchResultsList.get(position));
    }

    class FishItemViewHolder extends RecyclerView.ViewHolder {
        private TextView searchResultTV;

        FishItemViewHolder(View itemView) {
            super(itemView);
            this.searchResultTV = itemView.findViewById(R.id.tv_search_result);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    resultClickListener.onSearchResultClicked(
                            searchResultsList.get(getAdapterPosition())
                    );
                }
            });
        }

        void bind(FishData fish){
          //  this.searchResultTV.setText(fish.species_name);

    }

}}
