package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.Song;

import java.util.List;

public interface PlaylistSongDao {
    void addSongToPlaylist(int playlistId, String songId);
    void removeSongFromPlaylist(int playlistId, String songId);
    List<Song> getSongsInPlaylist(int playlistId);
    List<Playlist> getPlaylistsContainingSong(String songId);
}
