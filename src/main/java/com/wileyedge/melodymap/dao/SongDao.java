package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.model.Song;
import java.util.List;

public interface SongDao {
    List<Song> getAllSongs();
    Song getSongById(String id);
    Song addSong(Song song);
    void updateSong(Song song);
    void deleteSong(String id);
}
