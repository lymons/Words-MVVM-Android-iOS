package com.example.al333z.words;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.al333z.words.service.Word;

import java.util.List;

public class WordListAdapter extends ArrayAdapter<WordListAdapter.WordListItem> {

    private static final String TAG = WordListAdapter.class.getName();

    public WordListAdapter(Context context, List<WordListItem> objects) {
        super(context, R.layout.row_wordlist, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        WordListItem item = getItem(position);
        View rowView = inflater.inflate(R.layout.row_wordlist, parent, false);

        TextView dayNumTextView = (TextView) rowView.findViewById(R.id.dayNumTextView);
        TextView dayNameTextView = (TextView) rowView.findViewById(R.id.dayNameTextView);
        TextView wordTextView = (TextView) rowView.findViewById(R.id.abTitleTextView);

        wordTextView.setText(item.word.word);
        return rowView;
    }

    public static class WordListItem {

        private Word word;

        public WordListItem(Word word) {
            this.word = word;
        }

    }
}
