package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.model.Artist;
import com.wileyedge.melodymap.service.exceptions.ArtistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;

import java.util.List;

public interface ArtistServiceInterface {
    List<Artist> getAllArtists();
    Artist getArtistById(String id) throws ArtistNotFoundException;
    Artist addArtist(Artist artist) throws DataValidationException;
    void updateArtist(Artist artist) throws DataValidationException, ArtistNotFoundException;
    void deleteArtist(String id) throws ArtistNotFoundException;
}