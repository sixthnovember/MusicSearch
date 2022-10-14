package at.ac.univie.hci.musicsearchapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    public ArrayList<String> d = Database.getInstance();
    public ArtistCardAdapter artistListAdapter;

    public FavoritesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        ListView accountsListView =  rootView.findViewById(R.id.artist_list);

        artistListAdapter = new ArtistCardAdapter(getActivity().getApplicationContext());
        accountsListView.setAdapter(artistListAdapter);
        artistListAdapter.addAll(d);

        return rootView;
    }

}