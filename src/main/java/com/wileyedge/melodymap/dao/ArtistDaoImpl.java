package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.model.Artist;
import com.wileyedge.melodymap.dao.mappers.ArtistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtistDaoImpl implements ArtistDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Artist> getAllArtists() {
        String sql = "SELECT * FROM artist";
        return jdbcTemplate.query(sql, new ArtistMapper());
    }

    @Override
    public Artist getArtistById(String id) {
        final String sql = "SELECT * FROM artist WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new ArtistMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null; // Artist not found
        }
    }

    @Override
    public Artist addArtist(Artist artist) {
        String sql = "INSERT INTO artist (id, name) VALUES (?, ?)";
        jdbcTemplate.update(sql, artist.getId(), artist.getName());
        return artist;
    }

    @Override
    public void updateArtist(Artist artist) {
        String sql = "UPDATE artist SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, artist.getName(), artist.getId());
    }

    @Override
    public void deleteArtist(String id) {
        String sql = "DELETE FROM artist WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}