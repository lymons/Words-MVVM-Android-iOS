package com.example.al333z.words;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.al333z.words.viewmodel.WordViewModel;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final String TAG = WordListAdapter.class.getSimpleName();

    /**
     * Update the items of the datasource
     * NB: Only this method must be used to update the items of the adapter!
     *
     * @param items
     */
    public void updateItems(List<WordViewModel> items) {
        mViewModels.removeAll(mViewModels);
        mViewModels.addAll(items);
        notifyDataSetChanged();
    }

    private List<WordViewModel> mViewModels = new LinkedList<>();

    /**
     * Constructor that returns a WordListAdapter, with the given list a predefined layout
     *
     * @param viewModelsObservable the list
     */
    public WordListAdapter(Observable<List<WordViewModel>> viewModelsObservable) {
        super();

        viewModelsObservable.subscribe(next -> {
                    updateItems(next);
                }
        );
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.word_list_item, viewGroup, false);
        return new WordViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mViewModels.size();
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int i) {
        WordViewModel item = mViewModels.get(i);
        holder.bindItem(item);
    }

    /**
     * ViewHolder for Word list items
     */
    class WordViewHolder extends RecyclerView.ViewHolder {
        public WordViewModel mItem;

        TextView dayNumTextView;
        TextView dayNameTextView;
        TextView wordTextView;

        public WordViewHolder(View itemView) {
            super(itemView);

            dayNumTextView = (TextView) itemView.findViewById(R.id.dayNumTextView);
            dayNameTextView = (TextView) itemView.findViewById(R.id.dayNameTextView);
            wordTextView = (TextView) itemView.findViewById(R.id.abTitleTextView);
        }

        public void bindItem(WordViewModel item) {
            mItem = item;
            item.wordTitle.subscribe(next -> wordTextView.setText(next));
        }
    }
}