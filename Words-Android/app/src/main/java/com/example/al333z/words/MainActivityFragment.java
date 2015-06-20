package com.example.al333z.words;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.al333z.words.service.WordService;
import com.example.al333z.words.viewmodel.WordListViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    public MainActivityFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.recycleview_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mRecyclerView.getAdapter() != null) return; // the adapter has already been set

        WordService wordService = new WordService();
        WordListViewModel wordListViewModel = new WordListViewModel(wordService);

        // init and set the adapter
        mAdapter = new WordListAdapter(wordListViewModel.words);
        mRecyclerView.setAdapter(mAdapter);
    }
}
