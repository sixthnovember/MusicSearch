package at.ac.univie.hci.musicsearchapp;

public class Album {
    private String titel;
    private String release;
    private String albumType;

    public Album(String titel, String release, String albumType) {
        this.titel = titel;
        this.release = release;
        this.albumType = albumType;
    }

    public String getTitel(){
        return this.titel;
    }

    public String getRelease() {
        return this.release;
    }

    public String getAlbumType() {
        return this.albumType;
    }
}

