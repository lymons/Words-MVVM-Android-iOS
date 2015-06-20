package com.example.al333z.words;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.al333z.words.viewmodel.WordViewModel;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewActions;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

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
     * Constructor that returns a WordListAdapter, with an observable containing a list of view models
     *
     * @param viewModelsObservable an observable with the list of view models
     */
    public WordListAdapter(Observable<List<WordViewModel>> viewModelsObservable) {
        super();

        viewModelsObservable.subscribe(next -> updateItems(next));
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

    @Override
    public void onViewRecycled(WordViewHolder holder) {
        holder.viewRecycledBehavior.onNext(null);
        holder.viewRecycledBehavior = BehaviorSubject.create();
    }

    /**
     * ViewHolder for the item
     */
    class WordViewHolder extends RecyclerView.ViewHolder {
        public WordViewModel mItem;

        TextView dayTextView;
        TextView monthTextView;
        TextView yearTextView;
        TextView wordTextView;
        ImageView imageView;

        BehaviorSubject<Void> viewRecycledBehavior;

        public WordViewHolder(View itemView) {
            super(itemView);

            dayTextView = (TextView) itemView.findViewById(R.id.dayTextView);
            monthTextView = (TextView) itemView.findViewById(R.id.monthTextView);
            yearTextView = (TextView) itemView.findViewById(R.id.yearTextView);
            wordTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        public void bindItem(WordViewModel item) {
            mItem = item;
            item.day.observe().map(d -> d.toString()).subscribe(ViewActions.setText(dayTextView));
            item.month.observe().map(m -> m.toString()).subscribe(ViewActions.setText(monthTextView));
            item.year.observe().map(y -> y.toString()).subscribe(ViewActions.setText(yearTextView));
            item.wordTitle.observe().subscribe(ViewActions.setText(wordTextView));

            viewRecycledBehavior = BehaviorSubject.create();

            imageView.setImageDrawable(null);
            item.imageUrl.observe()
                    .takeUntil(viewRecycledBehavior)
                    .subscribeOn(Schedulers.io())
                    .map(url -> downloadImage(url))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(drawable -> {
                        imageView.setImageDrawable(drawable);
                    });
        }

        private Drawable downloadImage(String imageUrl) {
            InputStream is = null;
            Drawable d = null;
            try {
//                URL url = new URL(imageUrl);
//                is = (InputStream) url.getContent();
//                d = Drawable.createFromStream(is, "image");
//                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return d;
        }
    }
}