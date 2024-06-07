package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.dao.PlaylistSongDao;
import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistSongDaoStubImpl implements PlaylistSongDao {

    private final Map<Integer, List<String>> playlistSongs = new HashMap<>();
    private final Map<String, List<Integer>> songPlaylists = new HashMap<>();
    private final Map<String, Song> songs = new HashMap<>();
    private final Map<Integer, Playlist> playlists = new HashMap<>();
    private int nextPlaylistId = 1;

    public PlaylistSongDaoStubImpl() {
        // Initialize with default data
        Song song1 = new Song();
        song1.setId("song1");
        song1.setTitle("Default Song 1");
        song1.setDuration(String.valueOf(300));
        songs.put(song1.getId(), song1);

        Song song2 = new Song();
        song2.setId("song2");
        song2.setTitle("Default Song 2");
        song2.setDuration(String.valueOf(200));
        songs.put(song2.getId(), song2);

        Playlist playlist1 = new Playlist();
        playlist1.setId(nextPlaylistId++);
        playlist1.setName("Default Playlist 1");
        playlist1.setDescription("Default Description 1");
        playlists.put(playlist1.getId(), playlist1);

        Playlist playlist2 = new Playlist();
        playlist2.setId(nextPlaylistId++);
        playlist2.setName("Default Playlist 2");
        playlist2.setDescription("Default Description 2");
        playlists.put(playlist2.getId(), playlist2);

        addSongToPlaylist(playlist1.getId(), song1.getId());
        addSongToPlaylist(playlist2.getId(), song2.getId());
    }

    @Override
    public void addSongToPlaylist(int playlistId, String songId) {
        playlistSongs.computeIfAbsent(playlistId, k -> new ArrayList<>()).add(songId);
        songPlaylists.computeIfAbsent(songId, k -> new ArrayList<>()).add(playlistId);
    }

    @Override
    public void removeSongFromPlaylist(int playlistId, String songId) {
        List<String> songsInPlaylist = playlistSongs.get(playlistId);
        if (songsInPlaylist != null) {
            songsInPlaylist.remove(songId);
            if (songsInPlaylist.isEmpty()) {
                playlistSongs.remove(playlistId);
            }
        }

        List<Integer> playlistsForSong = songPlaylists.get(songId);
        if (playlistsForSong != null) {
            playlistsForSong.remove(Integer.valueOf(playlistId));
            if (playlistsForSong.isEmpty()) {
                songPlaylists.remove(songId);
            }
        }
    }

    @Override
    public List<Song> getSongsInPlaylist(int playlistId) {
        List<String> songIds = playlistSongs.get(playlistId);
        List<Song> songsInPlaylist = new ArrayList<>();
        if (songIds != null) {
            for (String songId : songIds) {
                songsInPlaylist.add(songs.get(songId));
            }
        }
        return songsInPlaylist;
    }

    @Override
    public List<Playlist> getPlaylistsContainingSong(String songId) {
        List<Integer> playlistIds = songPlaylists.get(songId);
        List<Playlist> containingPlaylists = new ArrayList<>();
        if (playlistIds != null) {
            for (Integer playlistId : playlistIds) {
                containingPlaylists.add(playlists.get(playlistId));
            }
        }
        return containingPlaylists;
    }

    // Additional methods to manage test data
    public void addSong(Song song) {
        songs.put(song.getId(), song);
    }

    public void addPlaylist(Playlist playlist) {
        playlist.setId(nextPlaylistId++);
        playlists.put(playlist.getId(), playlist);
    }

    public void clear() {
        playlistSongs.clear();
        songPlaylists.clear();
        songs.clear();
        playlists.clear();
        nextPlaylistId = 1;
    }
}

