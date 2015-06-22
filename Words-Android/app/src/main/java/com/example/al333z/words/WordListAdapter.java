package com.example.al333z.words;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.al333z.words.viewmodel.WordViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewActions;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

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
    private PublishSubject<WordViewModel> mAdapterSelectedItemSubject;

    /**
     * Constructor that returns a WordListAdapter, with an observable containing a list of view models
     *
     * @param viewModelsObservable an observable with the list of view models
     */
    public WordListAdapter(Observable<List<WordViewModel>> viewModelsObservable) {
        super();

        viewModelsObservable.subscribe(next -> updateItems(next));
        mAdapterSelectedItemSubject = PublishSubject.create();
    }

    public Observable<WordViewModel> getSelectedWordViewModelObservable() {
        return mAdapterSelectedItemSubject.asObservable();
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
    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private WordViewModel mItem;

        private TextView mDayTextView;
        private TextView mMonthTextView;
        private TextView mYearTextView;
        private TextView mWordTextView;
        private ImageView mImageView;

        BehaviorSubject<Void> viewRecycledBehavior;

        public WordViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mDayTextView = (TextView) itemView.findViewById(R.id.dayTextView);
            mMonthTextView = (TextView) itemView.findViewById(R.id.monthTextView);
            mYearTextView = (TextView) itemView.findViewById(R.id.yearTextView);
            mWordTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        public void bindItem(WordViewModel item) {
            mItem = item;

            mItem.day.observe().map(d -> d.toString()).subscribe(ViewActions.setText(mDayTextView));
            mItem.month.observe().map(m -> m.toString()).subscribe(ViewActions.setText(mMonthTextView));
            mItem.year.observe().map(y -> y.toString()).subscribe(ViewActions.setText(mYearTextView));
            mItem.wordTitle.observe().subscribe(ViewActions.setText(mWordTextView));

            viewRecycledBehavior = BehaviorSubject.create();

            mImageView.setImageDrawable(null);
            item.imageUrl.observe()
                    .takeUntil(viewRecycledBehavior.asObservable())
                    .subscribeOn(Schedulers.io())
                    .map(url -> downloadImage(url))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(drawable -> mImageView.setImageDrawable(drawable));
        }

        private Drawable downloadImage(String imageUrl) {
            InputStream is = null;
            Drawable d = null;
            try {
                URL url = new URL(imageUrl);
                // this crash on the emulator, leaving these lines commented for now..
//                is = (InputStream) url.getContent();
//                d = Drawable.createFromStream(is, "image");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                }
            }
            return d;
        }

        @Override
        public void onClick(View v) {
            mAdapterSelectedItemSubject.onNext(mItem);
        }
    }
}