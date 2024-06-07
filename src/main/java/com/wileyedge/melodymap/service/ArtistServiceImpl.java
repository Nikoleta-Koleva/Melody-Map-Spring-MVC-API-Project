package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.dao.ArtistDao;
import com.wileyedge.melodymap.model.Artist;
import com.wileyedge.melodymap.service.exceptions.ArtistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistServiceInterface {

    @Autowired
    private ArtistDao artistDao;

    //Constructor used for testing the service layer
    public ArtistServiceImpl(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistDao.getAllArtists();
    }

    @Override
    public Artist getArtistById(String id) throws ArtistNotFoundException {
        Artist artist = artistDao.getArtistById(id);
        if (artist == null) {
            throw new ArtistNotFoundException("Artist does not exist");
        }
        return artist;
    }

    @Override
    public Artist addArtist(Artist artist) throws DataValidationException {
        if (artist.getName() == null || artist.getName().trim().isEmpty()) {
            throw new DataValidationException("Artist name cannot be empty");
        }
        return artistDao.addArtist(artist);
    }

    @Override
    public void updateArtist(Artist artist) throws DataValidationException, ArtistNotFoundException {
        if (artist.getName() == null || artist.getName().trim().isEmpty()) {
            throw new DataValidationException("Artist name cannot be empty");
        }

        Artist existingArtist = artistDao.getArtistById(artist.getId());
        if (existingArtist == null) {
            throw new ArtistNotFoundException("Artist does not exist");
        }
        artistDao.updateArtist(artist);
    }

    @Override
    public void deleteArtist(String id) throws ArtistNotFoundException {
        Artist existingArtist = artistDao.getArtistById(id);
        if (existingArtist == null) {
            throw new ArtistNotFoundException("Artist does not exist");
        }
        artistDao.deleteArtist(id);
    }
}