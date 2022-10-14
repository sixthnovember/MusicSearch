package at.ac.univie.hci.musicsearchapp;

import java.util.ArrayList;

public class Artist {

    private String name;
    private String id;
    private ArrayList<Album> albums;

    public Artist(String name, String id, ArrayList<Album> albums){
        this.name = name;
        this.id = id;
        this.albums = albums;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Album> getAlbums() {
        return this.albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

}
