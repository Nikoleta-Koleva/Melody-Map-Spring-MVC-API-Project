package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.service.exceptions.PersistenceException;
import com.wileyedge.melodymap.service.exceptions.PlaylistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.SongNotFoundException;

import java.util.List;

public interface PlaylistSongServiceInterface {
    void addSongToPlaylist(int playlistId, String songId) throws SongNotFoundException, PlaylistNotFoundException, PersistenceException;
    void removeSongFromPlaylist(int playlistId, String songId) throws PlaylistNotFoundException, SongNotFoundException, PersistenceException;
    List<Song> getSongsInPlaylist(int playlistId) throws PlaylistNotFoundException, PersistenceException;
    List<Playlist> getPlaylistsContainingSong(String songId) throws SongNotFoundException, PersistenceException;
}