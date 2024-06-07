package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.dao.PlaylistDao;
import com.wileyedge.melodymap.dao.UserDao;
import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.User;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import com.wileyedge.melodymap.service.exceptions.PlaylistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistServiceImpl implements PlaylistServiceInterface {

    @Autowired
    private PlaylistDao playlistDao;

    @Autowired
    private UserDao userDao;

    //Constructor used for testing the service layer
    public PlaylistServiceImpl(PlaylistDao playlistDao, UserDao userDao) {
        this.playlistDao = playlistDao;
        this.userDao = userDao;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistDao.getAllPlaylists();
    }

    @Override
    public Playlist getPlaylistById(int id) throws PlaylistNotFoundException {
        Playlist playlist = playlistDao.getPlaylistById(id);
        if (playlist == null) {
            throw new PlaylistNotFoundException("Playlist does not exist");
        }
        return playlist;
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) throws DataValidationException, UserNotFoundException {
        if (playlist.getName() == null || playlist.getName().trim().isEmpty()) {
            throw new DataValidationException("Playlist name cannot be empty");
        }

        if (playlist.getUserId() > 0) {
            Optional<User> user = userDao.findById(playlist.getUserId());
            if (!user.isPresent()) {
                throw new UserNotFoundException("User does not exist");
            }
        }
        return playlistDao.addPlaylist(playlist);
    }

    @Override
    public List<Playlist> getUserPlaylists(int userId) {
        Optional<User> userOpt = userDao.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return playlistDao.findByUser(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist) throws DataValidationException, UserNotFoundException, PlaylistNotFoundException {
        if (playlist.getName() == null || playlist.getName().trim().isEmpty()) {
            throw new DataValidationException("Playlist name cannot be empty");
        }

        if (playlist.getUserId() > 0) {
            Optional<User> user = userDao.findById(playlist.getUserId());
            if (!user.isPresent()) {
                throw new UserNotFoundException("User does not exist");
            }
        }

        Playlist existingPlaylist = playlistDao.getPlaylistById(playlist.getId());
        if (existingPlaylist == null) {
            throw new PlaylistNotFoundException("Playlist does not exist");
        }
        playlistDao.updatePlaylist(playlist);
        return playlist;
    }

    @Override
    public void deletePlaylist(int id) throws PlaylistNotFoundException {
        Playlist playlist = playlistDao.getPlaylistById(id);
        if (playlist == null) {
            throw new PlaylistNotFoundException("Playlist does not exist");
        }
        playlistDao.deletePlaylist(id);
    }
}
