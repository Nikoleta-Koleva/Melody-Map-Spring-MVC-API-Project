package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.model.Artist;

import java.util.List;

public interface ArtistDao {
    List<Artist> getAllArtists();
    Artist getArtistById(String id);
    Artist addArtist(Artist artist);
    void updateArtist(Artist artist);
    void deleteArtist(String id);
}