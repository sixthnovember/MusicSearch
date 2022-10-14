package at.ac.univie.hci.musicsearchapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private RequestQueue requestQueue;

    public static Artist artist;
    public AlbumCardAdapter albumListAdapter;

    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        ListView accountsListView = rootView.findViewById(R.id.album_list);

        albumListAdapter = new AlbumCardAdapter(getActivity().getApplicationContext());
        accountsListView.setAdapter(albumListAdapter);

        // API
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        SearchView simpleSearchView = rootView.findViewById(R.id.searchView);

        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                String URL = "https://musicbrainz.org/ws/2/artist/?query=" + query + "&fmt=json";

                JsonRequest<JSONObject> artistRequest = new JsonObjectRequest(
                        Request.Method.GET, URL, null,
                        response -> {
                            Log.i("API_RESPONSE", response.toString());
                            artist = processArtistResult(response, container);
                            String URL2 = "https://musicbrainz.org/ws/2/artist/" + artist.getId() + "?inc=release-groups&fmt=json";

                            JsonRequest<JSONObject> albumRequest = new JsonObjectRequest(
                                    Request.Method.GET, URL2, null,
                                    response2 -> {
                                        Log.i("API_RESPONSE", response2.toString());
                                        artist.setAlbums(processAlbumResult(response2, container));
                                    },
                                    error -> {
                                        Toast.makeText(getContext(), "Please try again!", Toast.LENGTH_LONG).show();
                                        Log.e("API_ERROR", error.getMessage());
                                    });

                            requestQueue.add(albumRequest);

                        },
                        error -> {
                            Toast.makeText(getContext(), "Please try again!", Toast.LENGTH_LONG).show();
                            Log.e("API_ERROR", error.getMessage());
                        });

                requestQueue.add(artistRequest);

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return rootView;
    }

    private Artist processArtistResult(JSONObject apiResponse, ViewGroup container) {
        try {
            String idArtist = apiResponse.getJSONArray("artists").getJSONObject(0).getString("id");
            String nameArtist = apiResponse.getJSONArray("artists").getJSONObject(0).getString("name");

            Artist artist = new Artist(nameArtist, idArtist, new ArrayList<>());
            TextView artistName = container.findViewById(R.id.textView_artist_name);

            artistName.setText(nameArtist);

            Button favorites = container.findViewById(R.id.favorites_button);
            favorites.setOnClickListener(v -> {
                Database.getInstance().add(artist.getName());
                Snackbar snackbar = Snackbar
                        .make(container.findViewById(R.id.constraint_layout), "Artist added", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", view -> {})
                        .setBackgroundTint(Color.GREEN)
                        .setTextColor(Color.BLACK)
                        .setActionTextColor(Color.BLACK);
                snackbar.show();
            });

            favorites.setVisibility(View.VISIBLE);

            return artist;

        } catch (JSONException e) {
            Toast.makeText(getContext(), "Could not parse API response!", Toast.LENGTH_LONG).show();
            Log.e("PARSE_ERROR", e.getMessage());
        }
        return null;
    }

    private ArrayList<Album> processAlbumResult(JSONObject apiResponse, ViewGroup container) {
        try {
            JSONArray artistAlbums = apiResponse.getJSONArray("release-groups");
            ArrayList<Album> albums = new ArrayList<>();

            for(int i = 0; i < artistAlbums.length(); ++i) {
                JSONObject albumObj = artistAlbums.getJSONObject(i);
                Album album = new Album(albumObj.getString("title"), albumObj.getString("first-release-date"), albumObj.getString("primary-type"));
                albums.add(album);
            }

            albumListAdapter.clear();
            albumListAdapter.notifyDataSetChanged();

            albumListAdapter.addAll(albums);

            return albums;
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Could not parse API response!", Toast.LENGTH_LONG).show();
            Log.e("PARSE_ERROR", e.getMessage());
        }

        return null;

    }

}




