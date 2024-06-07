package com.wileyedge.melodymap.dao.mappers;

import com.wileyedge.melodymap.model.Artist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistMapper implements RowMapper<Artist> {
    @Override
    public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Artist artist = new Artist();
        artist.setId(rs.getString("id"));
        artist.setName(rs.getString("name"));
        return artist;
    }
}