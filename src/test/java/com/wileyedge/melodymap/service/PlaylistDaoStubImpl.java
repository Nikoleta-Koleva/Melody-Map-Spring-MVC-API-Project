package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.dao.PlaylistDao;
import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistDaoStubImpl implements PlaylistDao {

    private Map<Integer, Playlist> playlists = new HashMap<>();
    private int nextId = 1;

    public PlaylistDaoStubImpl() {
        // Initialize with default playlists
        Playlist playlist1 = new Playlist();
        playlist1.setId(nextId++);
        playlist1.setName("Default Playlist 1");
        playlist1.setDescription("Default Description 1");
        playlist1.setUserId(1);
        playlists.put(playlist1.getId(), playlist1);

        Playlist playlist2 = new Playlist();
        playlist2.setId(nextId++);
        playlist2.setName("Default Playlist 2");
        playlist2.setDescription("Default Description 2");
        playlist2.setUserId(2);
        playlists.put(playlist2.getId(), playlist2);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return new ArrayList<>(playlists.values());
    }

    @Override
    public Playlist getPlaylistById(int id) {
        return playlists.get(id);
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) {
        playlist.setId(nextId++);
        playlists.put(playlist.getId(), playlist);
        return playlist;
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
        playlists.put(playlist.getId(), playlist);
    }

    @Override
    public void deletePlaylist(int id) {
        playlists.remove(id);
    }

    @Override
    public List<Playlist> findByUser(User user) {
        List<Playlist> userPlaylists = new ArrayList<>();
        for (Playlist playlist : playlists.values()) {
            if (playlist.getUserId() == user.getId()) {
                userPlaylists.add(playlist);
            }
        }
        return userPlaylists;
    }
}
