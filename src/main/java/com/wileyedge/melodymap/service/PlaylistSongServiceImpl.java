package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.dao.PlaylistSongDao;
import com.wileyedge.melodymap.dao.PlaylistDao;
import com.wileyedge.melodymap.dao.SongDao;
import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.service.exceptions.PersistenceException;
import com.wileyedge.melodymap.service.exceptions.PlaylistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.SongNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistSongServiceImpl implements PlaylistSongServiceInterface {

    @Autowired
    private PlaylistSongDao playlistSongDao;

    @Autowired
    private PlaylistDao playlistDao;

    @Autowired
    private SongDao songDao;

    //Constructor used for testing the service layer
    public PlaylistSongServiceImpl(PlaylistSongDao playlistSongDao, PlaylistDao playlistDao, SongDao songDao) {
        this.playlistSongDao = playlistSongDao;
        this.playlistDao = playlistDao;
        this.songDao = songDao;
    }

    @Override
    public void addSongToPlaylist(int playlistId, String songId) throws SongNotFoundException, PlaylistNotFoundException, PersistenceException {
        Playlist playlist = playlistDao.getPlaylistById(playlistId);
        if (playlist == null) {
            throw new PlaylistNotFoundException("Playlist does not exist");
        }
        Song song = songDao.getSongById(songId);
        if (song == null) {
            throw new SongNotFoundException("Song does not exist");
        }
        try {
            playlistSongDao.addSongToPlaylist(playlistId, songId);
        } catch (Exception e) {
            throw new PersistenceException("Failed to add song to playlist", e);
        }
    }

    @Override
    public void removeSongFromPlaylist(int playlistId, String songId) throws PlaylistNotFoundException, SongNotFoundException, PersistenceException {
        Playlist playlist = playlistDao.getPlaylistById(playlistId);
        if (playlist == null) {
            throw new PlaylistNotFoundException("Playlist does not exist");
        }
        Song song = songDao.getSongById(songId);
        if (song == null) {
            throw new SongNotFoundException("Song does not exist");
        }
        try {
            playlistSongDao.removeSongFromPlaylist(playlistId, songId);
        } catch (Exception e) {
            throw new PersistenceException("Failed to remove song from playlist", e);
        }
    }

    @Override
    public List<Song> getSongsInPlaylist(int playlistId) throws PlaylistNotFoundException, PersistenceException {
        Playlist playlist = playlistDao.getPlaylistById(playlistId);
        if (playlist == null) {
            throw new PlaylistNotFoundException("Playlist does not exist");
        }
        try {
            return playlistSongDao.getSongsInPlaylist(playlistId);
        } catch (Exception e) {
            throw new PersistenceException("Failed to retrieve songs in playlist", e);
        }
    }

    @Override
    public List<Playlist> getPlaylistsContainingSong(String songId) throws SongNotFoundException, PersistenceException {
        Song song = songDao.getSongById(songId);
        if (song == null) {
            throw new SongNotFoundException("Song does not exist");
        }
        try {
            return playlistSongDao.getPlaylistsContainingSong(songId);
        } catch (Exception e) {
            throw new PersistenceException("Failed to retrieve playlists containing song", e);
        }
    }
}