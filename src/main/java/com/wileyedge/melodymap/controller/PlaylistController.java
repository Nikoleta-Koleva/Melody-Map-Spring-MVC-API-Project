package com.wileyedge.melodymap.controller;

import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.service.PlaylistServiceImpl;
import com.wileyedge.melodymap.service.exceptions.*;
import com.wileyedge.melodymap.service.PlaylistSongServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
@CrossOrigin
public class PlaylistController {

    @Autowired
    private PlaylistServiceImpl playlistService;

    @Autowired
    private PlaylistSongServiceImpl playlistSongService;

    @GetMapping("/playlists")
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{id}")
    public Playlist getPlaylistById(@PathVariable int id) throws PlaylistNotFoundException {
        return playlistService.getPlaylistById(id);
    }

    @PostMapping("/add")
    public Playlist addPlaylist(@RequestBody Playlist playlist) throws UserNotFoundException, DataValidationException {
        return playlistService.addPlaylist(playlist);
    }

    @PutMapping("/{id}")
    public Playlist updatePlaylist(@PathVariable int id, @RequestBody Playlist playlist) throws UserNotFoundException, DataValidationException, PlaylistNotFoundException {
        playlist.setId(id);
        return playlistService.updatePlaylist(playlist);
    }

    @DeleteMapping("/{id}")
    public void deletePlaylist(@PathVariable int id) throws PlaylistNotFoundException {
        playlistService.deletePlaylist(id);
    }

    @PostMapping("/{playlistId}/addSong/{songId}")
    public void addSongToPlaylist(@PathVariable int playlistId, @PathVariable String songId) throws PlaylistNotFoundException, PersistenceException, SongNotFoundException {
        playlistSongService.addSongToPlaylist(playlistId, songId);
    }

    @DeleteMapping("/{playlistId}/removeSong/{songId}")
    public void removeSongFromPlaylist(@PathVariable int playlistId, @PathVariable String songId) throws PlaylistNotFoundException, PersistenceException, SongNotFoundException {
        playlistSongService.removeSongFromPlaylist(playlistId, songId);
    }

    @GetMapping("/{playlistId}/songs")
    public List<Song> getSongsInPlaylist(@PathVariable int playlistId) throws PlaylistNotFoundException, PersistenceException {
        return playlistSongService.getSongsInPlaylist(playlistId);
    }

    @GetMapping("/songs/{songId}/playlists")
    public List<Playlist> getPlaylistsContainingSong(@PathVariable String songId) throws PersistenceException, SongNotFoundException {
        return playlistSongService.getPlaylistsContainingSong(songId);
    }

    @GetMapping("/user/{userId}/playlists")
    public List<Playlist> getUserPlaylists(@PathVariable int userId) {
        return playlistService.getUserPlaylists(userId);
}
}