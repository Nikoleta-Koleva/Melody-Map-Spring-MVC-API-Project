package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.dao.mappers.PlaylistMapper;
import com.wileyedge.melodymap.dao.mappers.SongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaylistSongDaoImpl implements PlaylistSongDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addSongToPlaylist(int playlistId, String songId) {
        String sql = "INSERT INTO playlist_song (playlist_id, song_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, playlistId, songId);
    }

    @Override
    public void removeSongFromPlaylist(int playlistId, String songId) {
        String sql = "DELETE FROM playlist_song WHERE playlist_id = ? AND song_id = ?";
        jdbcTemplate.update(sql, playlistId, songId);
    }

    public List<Song> getSongsInPlaylist(int playlistId) {
        String sql = "SELECT s.id, s.title, s.duration, a.id AS artist_id, a.name AS artist_name " +
                "FROM song s " +
                "JOIN playlist_song ps ON s.id = ps.song_id " +
                "JOIN artist a ON s.artist_id = a.id " +
                "WHERE ps.playlist_id = ?";
        return jdbcTemplate.query(sql, new SongMapper(), playlistId);
    }

    @Override
    public List<Playlist> getPlaylistsContainingSong(String songId) {
        String sql = "SELECT p.* FROM playlist p JOIN playlist_song ps ON p.id = ps.playlist_id WHERE ps.song_id = ?";
        return jdbcTemplate.query(sql, new PlaylistMapper(), songId);
    }
}