package at.ac.univie.hci.musicsearchapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArtistCardAdapter extends ArrayAdapter<String> {

    private List<String> artistCardList = new ArrayList<>();

    public ArtistCardAdapter(Context context) {
        super(context, R.layout.artist_card);
    }

    @Override
    public void add(String artist) {
        artistCardList.add(artist);
        super.add(artist);
    }

    @Override
    public void clear() {
        artistCardList.clear();
        super.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String artist = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.artist_card, parent, false);
        }

        TextView artistName = convertView.findViewById(R.id.artist_favorite_name);

        artistName.setText(artist);

        return convertView;

    }

}

