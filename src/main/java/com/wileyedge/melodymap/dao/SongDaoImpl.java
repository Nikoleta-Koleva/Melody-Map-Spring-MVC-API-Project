package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.dao.mappers.SongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongDaoImpl implements SongDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Song> getAllSongs() {
        String sql = "SELECT s.id, s.title, s.duration, a.id AS artist_id, a.name AS artist_name " +
                "FROM song s JOIN artist a ON s.artist_id = a.id";
        return jdbcTemplate.query(sql, new SongMapper());
    }

    @Override
    public Song getSongById(String id) {
        String sql = "SELECT s.id, s.title, s.duration, a.id AS artist_id, a.name AS artist_name " +
                "FROM song s JOIN artist a ON s.artist_id = a.id WHERE s.id = ?";
        return jdbcTemplate.queryForObject(sql, new SongMapper(), id);
    }

    @Override
    public Song addSong(Song song) {
        final String sql = "INSERT INTO song (id, title, duration, artist_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, song.getId(), song.getTitle(), song.getDuration(), song.getArtist().getId());
        return song;
    }

    @Override
    public void updateSong(Song song) {
        String sql = "UPDATE song SET title = ?, duration = ?, artist_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, song.getTitle(), song.getDuration(), song.getArtist().getId(), song.getId());
    }

    @Override
    public void deleteSong(String id) {
        String sql = "DELETE FROM song WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
