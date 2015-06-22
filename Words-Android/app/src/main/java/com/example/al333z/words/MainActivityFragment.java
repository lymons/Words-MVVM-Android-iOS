package com.example.al333z.words;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.al333z.words.service.WordService;
import com.example.al333z.words.viewmodel.WordListViewModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewActions;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private WordListAdapter mAdapter;


    public MainActivityFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.recycleview_fragment, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mRecyclerView.getAdapter() != null) return; // the adapter has already been set
        final WordService wordService = new WordService();
        final WordListViewModel wordListViewModel = new WordListViewModel(wordService);

        // bind ui to current loading status
        wordListViewModel.isLoading.observe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ViewActions.setVisibility(mProgressBar));

        // bind the adapter view with the view model
        mAdapter = new WordListAdapter(wordListViewModel.words.observe());
        mRecyclerView.setAdapter(mAdapter);

        // observe selection event and show another view with the selected content
        mAdapter.getSelectedWordViewModelObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(selectedWordViewModel ->
                        Toast.makeText(getActivity(), selectedWordViewModel.wordTitle.value(), Toast.LENGTH_SHORT).show());
    }
}
