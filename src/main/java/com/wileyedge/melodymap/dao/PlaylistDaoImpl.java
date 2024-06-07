package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PlaylistDaoImpl implements PlaylistDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Playlist> getAllPlaylists() {
        String sql = "SELECT * FROM playlist";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Playlist.class));
    }

    @Override
    public Playlist getPlaylistById(int id) {
        String sql = "SELECT * FROM playlist WHERE id = ?";
        List<Playlist> playlists = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Playlist.class), id);
        return playlists.isEmpty() ? null : playlists.get(0);
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) {
        String sql = "INSERT INTO playlist (name, description, user_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, playlist.getName(), playlist.getDescription(), playlist.getUserId());
        int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        playlist.setId(id);
        return playlist;
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
        String sql = "UPDATE playlist SET name = ?, description = ?, user_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, playlist.getName(), playlist.getDescription(), playlist.getUserId(), playlist.getId());
    }

    @Override
    public void deletePlaylist(int id) {
        String sql = "DELETE FROM playlist WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Playlist> findByUser(User user) {
        String sql = "SELECT * FROM playlist WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{user.getId()}, (rs, rowNum) -> {
            Playlist playlist = new Playlist();
            playlist.setId(rs.getInt("id"));
            playlist.setName(rs.getString("name"));
            playlist.setDescription(rs.getString("description"));
            // Add more fields as necessary
            return playlist;
        });
    }
}
