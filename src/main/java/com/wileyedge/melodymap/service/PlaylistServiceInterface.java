package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import com.wileyedge.melodymap.service.exceptions.PlaylistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.UserNotFoundException;

import java.util.List;

public interface PlaylistServiceInterface {
    List<Playlist> getAllPlaylists();
    Playlist getPlaylistById(int id) throws PlaylistNotFoundException;
    List<Playlist> getUserPlaylists(int userId);
    Playlist addPlaylist(Playlist playlist) throws DataValidationException, UserNotFoundException;
    Playlist updatePlaylist(Playlist playlist) throws DataValidationException, UserNotFoundException, PlaylistNotFoundException;
    void deletePlaylist(int id) throws PlaylistNotFoundException;
}