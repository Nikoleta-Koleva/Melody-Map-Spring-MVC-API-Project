package com.wileyedge.melodymap.dao.mappers;

import com.wileyedge.melodymap.model.Artist;
import com.wileyedge.melodymap.model.Song;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongMapper implements RowMapper<Song> {
    @Override
    public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
        Artist artist = new Artist();
        artist.setId(rs.getString("artist_id"));
        artist.setName(rs.getString("artist_name"));

        Song song = new Song();
        song.setId(rs.getString("id"));
        song.setTitle(rs.getString("title"));
        song.setDuration(rs.getString("duration"));
        song.setArtist(artist);

        return song;
    }
}