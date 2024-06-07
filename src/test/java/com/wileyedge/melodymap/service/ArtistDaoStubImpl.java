package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.dao.ArtistDao;
import com.wileyedge.melodymap.model.Artist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtistDaoStubImpl implements ArtistDao {

    private Map<String, Artist> artists;
    private int nextId = 1;

    public ArtistDaoStubImpl() {
        this.artists = new HashMap<>();
        // Add a default artist for testing purposes
        Artist defaultArtist = new Artist();
        defaultArtist.setId(Integer.toString(nextId++));
        defaultArtist.setName("Default Artist");
        artists.put(defaultArtist.getId(), defaultArtist);
    }

    @Override
    public List<Artist> getAllArtists() {
        return new ArrayList<>(artists.values());
    }

    @Override
    public Artist getArtistById(String id) {
        return artists.get(id);
    }

    @Override
    public Artist addArtist(Artist artist) {
        artist.setId(Integer.toString(nextId++));
        artists.put(artist.getId(), artist);
        return artist;
    }

    @Override
    public void updateArtist(Artist artist) {
        if (artists.containsKey(artist.getId())) {
            artists.put(artist.getId(), artist);
        }
    }

    @Override
    public void deleteArtist(String id) {
        artists.remove(id);
    }
}
