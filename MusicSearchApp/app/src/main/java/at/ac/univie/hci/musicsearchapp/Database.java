package at.ac.univie.hci.musicsearchapp;

import java.util.ArrayList;

public class Database {

    private static final ArrayList<String> db = new ArrayList<String>();

    private Database() {}

    public static ArrayList<String> getInstance() {
        return db;
    }

}
