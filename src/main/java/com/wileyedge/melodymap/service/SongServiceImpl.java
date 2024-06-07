package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.dao.ArtistDao;
import com.wileyedge.melodymap.dao.SongDao;
import com.wileyedge.melodymap.model.Artist;
import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.service.exceptions.ArtistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import com.wileyedge.melodymap.service.exceptions.SongNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongServiceInterface {

    @Autowired
    private SongDao songDao;

    @Autowired
    private ArtistDao artistDao;

    //Constructor used for testing the service layer
    public SongServiceImpl(SongDao songDao, ArtistDao artistDao) {
        this.songDao = songDao;
        this.artistDao = artistDao;
    }

    @Override
    public List<Song> getAllSongs() {
        return songDao.getAllSongs();
    }

    @Override
    public Song getSongById(String id) throws SongNotFoundException {
        Song song = songDao.getSongById(id);
        if (song == null) {
            throw new SongNotFoundException("Song does not exist");
        }
        return song;
    }

    @Override
    public Song addSong(Song song) throws ArtistNotFoundException {
        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Song title cannot be empty");
        }
        if (!song.getDuration().matches("\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Duration must be in the format MM:SS");
        }

        Artist artist = song.getArtist();
        if (artist == null || artist.getName() == null || artist.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Artist name cannot be null or empty");
        }

        // Check if the artist exists in the database by name
        Artist existingArtist = artistDao.getArtistById(artist.getId());
        if (existingArtist == null) {
            // If the artist does not exist, add it to the database
            existingArtist = artistDao.addArtist(artist);
        }

        // Set the artist in the song to ensure it has the correct ID
        song.setArtist(existingArtist);

        // Add the song to the database
        return songDao.addSong(song);
    }

    @Override
    public void updateSong(Song song) throws DataValidationException, SongNotFoundException {
        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) {
            throw new DataValidationException("Song title cannot be empty");
        }
        if (!song.getDuration().matches("\\d{2}:\\d{2}")) {
            throw new DataValidationException("Duration must be in the format MM:SS");
        }
        if (song.getArtist() == null) {
            throw new DataValidationException("Artist cannot be null");
        }
        if (song.getArtist().getId() == null || song.getArtist().getId().trim().isEmpty()) {
            throw new DataValidationException("Artist ID cannot be empty");
        }
        if (song.getArtist().getName() == null || song.getArtist().getName().trim().isEmpty()) {
            throw new DataValidationException("Artist name cannot be empty");
        }

        // Check if the song exists
        Song existingSong = songDao.getSongById(song.getId());
        if (existingSong == null) {
            throw new SongNotFoundException("Song does not exist");
        }

        // Check if the artist exists, if not add the artist to the database
        Artist existingArtist = artistDao.getArtistById(song.getArtist().getId());
        if (existingArtist == null) {
            artistDao.addArtist(song.getArtist());
        }

        // Update the song
        songDao.updateSong(song);
    }

    @Override
    public void deleteSong(String id) throws SongNotFoundException {
        Song existingSong = songDao.getSongById(id);
        if (existingSong == null) {
            throw new SongNotFoundException("Song does not exist");
        }
        songDao.deleteSong(id);
    }
}