package at.ac.univie.hci.musicsearchapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AlbumCardAdapter extends ArrayAdapter<Album> {

    private List<Album> albumCardList = new ArrayList<Album>();

    public AlbumCardAdapter(Context context) {
        super(context, R.layout.album_card);
    }

    @Override
    public void add(Album album) {
        albumCardList.add(album);
        super.add(album);
    }

    @Override
    public void clear() {
        albumCardList.clear();
        super.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Album album = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.album_card, parent, false);
        }

        TextView albumTitle = convertView.findViewById(R.id.album_title);
        TextView albumRelease = convertView.findViewById(R.id.album_release);
        TextView albumType = convertView.findViewById(R.id.album_type);

        albumTitle.setText(album.getTitel());
        albumRelease.setText(album.getRelease());
        albumType.setText(album.getAlbumType());

        return convertView;

    }

}
