package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.User;

import java.util.List;

public interface PlaylistDao {
    List<Playlist> getAllPlaylists();
    Playlist getPlaylistById(int id);
    Playlist addPlaylist(Playlist playlist);
    void updatePlaylist(Playlist playlist);
    void deletePlaylist(int id);
    List<Playlist> findByUser(User user);
}
