package com.wileyedge.melodymap.dao.mappers;

import com.wileyedge.melodymap.model.Playlist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistMapper implements RowMapper<Playlist> {
    @Override
    public Playlist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Playlist playlist = new Playlist();
        playlist.setId(rs.getInt("id"));
        playlist.setName(rs.getString("name"));
        playlist.setDescription(rs.getString("description"));
        playlist.setUserId(rs.getInt("user_id"));
        return playlist;
    }
}