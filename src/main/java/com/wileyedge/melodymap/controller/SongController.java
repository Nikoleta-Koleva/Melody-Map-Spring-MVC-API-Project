package com.wileyedge.melodymap.controller;

import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.service.SongServiceImpl;
import com.wileyedge.melodymap.service.exceptions.ArtistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import com.wileyedge.melodymap.service.exceptions.SongNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
@CrossOrigin
public class SongController {

    @Autowired
    private SongServiceImpl songService;

    @GetMapping("/songs")
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/{id}")
    public Song getSongById(@PathVariable String id) throws SongNotFoundException {
        return songService.getSongById(id);
    }

    @PostMapping("/add")
    public Song addSong(@RequestBody Song song) throws DataValidationException, ArtistNotFoundException {
        return songService.addSong(song);
    }

    @PutMapping("/{id}")
    public void updateSong(@PathVariable String id, @RequestBody Song song) throws DataValidationException, SongNotFoundException {
        song.setId(id);
        songService.updateSong(song);
    }

    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable String id) throws SongNotFoundException {
        songService.deleteSong(id);
    }
}