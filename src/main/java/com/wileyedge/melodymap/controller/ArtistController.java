package com.wileyedge.melodymap.controller;

import com.wileyedge.melodymap.model.Artist;
import com.wileyedge.melodymap.service.ArtistServiceImpl;
import com.wileyedge.melodymap.service.exceptions.ArtistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
@CrossOrigin
public class ArtistController {

    @Autowired
    private ArtistServiceImpl artistService;

    @GetMapping("/artists")
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    public Artist getArtistById(@PathVariable String id) throws ArtistNotFoundException {
        return artistService.getArtistById(id);
    }

    @PostMapping("/add")
    public Artist addArtist(@RequestBody Artist artist) throws DataValidationException {
        return artistService.addArtist(artist);
    }

    @PutMapping("/{id}")
    public void updateArtist(@PathVariable String id, @RequestBody Artist artist) throws DataValidationException, ArtistNotFoundException {
        artist.setId(id);
        artistService.updateArtist(artist);
    }

    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable String id) throws ArtistNotFoundException {
        artistService.deleteArtist(id);
    }
}