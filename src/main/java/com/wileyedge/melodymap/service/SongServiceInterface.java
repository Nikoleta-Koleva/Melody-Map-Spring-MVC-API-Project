package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.service.exceptions.ArtistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import com.wileyedge.melodymap.service.exceptions.SongNotFoundException;

import java.util.List;

public interface SongServiceInterface {
    List<Song> getAllSongs();
    Song getSongById(String id) throws SongNotFoundException;
    Song addSong(Song song) throws ArtistNotFoundException;
    void updateSong(Song song) throws DataValidationException, SongNotFoundException;
    void deleteSong(String id) throws SongNotFoundException;
}