package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.service.exceptions.PlaylistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.SongNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistSongServiceImplTests {
    private PlaylistSongServiceInterface playlistSongService;
    private PlaylistSongDaoStubImpl playlistSongDaoStub;
    private PlaylistDaoStubImpl playlistDaoStub;
    private SongDaoStubImpl songDaoStub;

    @BeforeEach
    public void setUp() {
        playlistSongDaoStub = new PlaylistSongDaoStubImpl();
        playlistDaoStub = new PlaylistDaoStubImpl();
        songDaoStub = new SongDaoStubImpl();
        playlistSongService = new PlaylistSongServiceImpl(playlistSongDaoStub, playlistDaoStub, songDaoStub);
    }

    // Clean up playlists and songs
    @AfterEach
    public void cleanUp() {
        cleanUpPlaylists();
        cleanUpSongs();
    }

    private void cleanUpPlaylists() {
        List<Playlist> playlists = playlistDaoStub.getAllPlaylists();
        for (Playlist playlist : playlists) {
            playlistDaoStub.deletePlaylist(playlist.getId());
        }
    }

    private void cleanUpSongs() {
        List<Song> songs = songDaoStub.getAllSongs();
        for (Song song : songs) {
            songDaoStub.deleteSong(song.getId());
        }
    }

    @Test
    @DisplayName("Add Song to Playlist Service Test")
    public void addSongToPlaylistServiceTest() {
        assertDoesNotThrow(() -> playlistSongService.addSongToPlaylist(1, "song2"));
        List<Song> songsInPlaylist = assertDoesNotThrow(() -> playlistSongService.getSongsInPlaylist(1));
        assertEquals(2, songsInPlaylist.size());
        assertTrue(songsInPlaylist.stream().anyMatch(song -> song.getId().equals("song2")));
    }

    @Test
    @DisplayName("Add Song to Nonexistent Playlist Service Test")
    public void addSongToNonexistentPlaylistServiceTest() {
        assertThrows(PlaylistNotFoundException.class, () -> playlistSongService.addSongToPlaylist(99, "song1"));
    }

    @Test
    @DisplayName("Add Nonexistent Song to Playlist Service Test")
    public void addNonexistentSongToPlaylistServiceTest() {
        assertThrows(SongNotFoundException.class, () -> playlistSongService.addSongToPlaylist(1, "nonexistentSong"));
    }

    @Test
    @DisplayName("Remove Song from Playlist Service Test")
    public void removeSongFromPlaylistServiceTest() {
        assertDoesNotThrow(() -> playlistSongService.removeSongFromPlaylist(1, "song1"));
        List<Song> songsInPlaylist = assertDoesNotThrow(() -> playlistSongService.getSongsInPlaylist(1));
        assertEquals(0, songsInPlaylist.size());
    }

    @Test
    @DisplayName("Remove Song from Nonexistent Playlist Service Test")
    public void removeSongFromNonexistentPlaylistServiceTest() {
        assertThrows(PlaylistNotFoundException.class, () -> playlistSongService.removeSongFromPlaylist(99, "song1"));
    }

    @Test
    @DisplayName("Remove Nonexistent Song from Playlist Service Test")
    public void removeNonexistentSongFromPlaylistServiceTest() {
        assertThrows(SongNotFoundException.class, () -> playlistSongService.removeSongFromPlaylist(1, "nonexistentSong"));
    }

    @Test
    @DisplayName("Get Songs in Playlist Service Test")
    public void getSongsInPlaylistServiceTest() {
        List<Song> songsInPlaylist = assertDoesNotThrow(() -> playlistSongService.getSongsInPlaylist(1));
        assertEquals(1, songsInPlaylist.size());
        assertEquals("song1", songsInPlaylist.get(0).getId());
    }

    @Test
    @DisplayName("Get Songs in Nonexistent Playlist Service Test")
    public void getSongsInNonexistentPlaylistServiceTest() {
        assertThrows(PlaylistNotFoundException.class, () -> playlistSongService.getSongsInPlaylist(99));
    }

    @Test
    @DisplayName("Get Playlists Containing Song Service Test")
    public void getPlaylistsContainingSongServiceTest() {
        List<Playlist> playlistsContainingSong = assertDoesNotThrow(() -> playlistSongService.getPlaylistsContainingSong("song1"));
        assertEquals(1, playlistsContainingSong.size());
        assertEquals(1, playlistsContainingSong.get(0).getId());
    }

    @Test
    @DisplayName("Get Playlists Containing Nonexistent Song Service Test")
    public void getPlaylistsContainingNonexistentSongServiceTest() {
        assertThrows(SongNotFoundException.class, () -> playlistSongService.getPlaylistsContainingSong("nonexistentSong"));
    }
}