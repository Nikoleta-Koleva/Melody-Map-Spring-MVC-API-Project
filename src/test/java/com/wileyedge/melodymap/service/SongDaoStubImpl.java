package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.dao.SongDao;
import com.wileyedge.melodymap.model.Artist;
import com.wileyedge.melodymap.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongDaoStubImpl implements SongDao {

    private Map<String, Song> songs = new HashMap<>();

    public SongDaoStubImpl() {
        // Initialize with default songs
        Artist artist1 = new Artist();
        artist1.setId("artist1");
        artist1.setName("Default Artist 1");

        Song song1 = new Song();
        song1.setId("song1");
        song1.setTitle("Default Song 1");
        song1.setDuration(String.valueOf(300));
        song1.setArtist(artist1);
        songs.put(song1.getId(), song1);

        Song song2 = new Song();
        song2.setId("song2");
        song2.setTitle("Default Song 2");
        song2.setDuration(String.valueOf(200));
        song2.setArtist(artist1);
        songs.put(song2.getId(), song2);
    }

    @Override
    public List<Song> getAllSongs() {
        return new ArrayList<>(songs.values());
    }

    @Override
    public Song getSongById(String id) {
        return songs.get(id);
    }

    @Override
    public Song addSong(Song song) {
        songs.put(song.getId(), song);
        return song;
    }

    @Override
    public void updateSong(Song song) {
        songs.put(song.getId(), song);
    }

    @Override
    public void deleteSong(String id) {
        songs.remove(id);
    }

    // Additional method to clear data for testing purposes
    public void clear() {
        songs.clear();
    }
}
