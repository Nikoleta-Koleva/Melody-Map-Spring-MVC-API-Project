package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.dao.UserDao;
import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import com.wileyedge.melodymap.service.exceptions.PlaylistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistServiceImplTests {

    private PlaylistServiceInterface playlistService;
    private PlaylistDaoStubImpl playlistDaoStub;

    // Ensures that each test method in the test class starts with a fresh instance of PlaylistServiceImpl
    // configured with stub implementations of its dependencies (PlaylistDao).
    // dependencies are injected into the PlaylistServiceImpl instance
    @BeforeEach
    public void setUp() {
        playlistDaoStub = new PlaylistDaoStubImpl();
        UserDao userDao = new UserDaoStubImpl();
        playlistService = new PlaylistServiceImpl(playlistDaoStub, userDao);
    }

    // Clean up playlists
    @AfterEach
    public void cleanUp() {
        cleanUpPlaylists();
    }

    private void cleanUpPlaylists() {
        List<Playlist> playlists = playlistDaoStub.getAllPlaylists();
        for (Playlist playlist : playlists) {
            playlistDaoStub.deletePlaylist(playlist.getId());
        }
    }

    @Test
    @DisplayName("Find Playlist Service Test")
    public void findPlaylistServiceTest() throws PlaylistNotFoundException {
        Playlist returnPlaylist = playlistService.getPlaylistById(1);
        assertNotNull(returnPlaylist);
        assertEquals("Default Playlist 1", returnPlaylist.getName());
    }

    @Test
    @DisplayName("Playlist Not Found Service Test")
    public void playlistNotFoundServiceTest() {
        assertThrows(PlaylistNotFoundException.class, () -> playlistService.getPlaylistById(99));
    }

    @Test
    @DisplayName("Add Playlist Service Test")
    public void addPlaylistServiceTest() {
        Playlist newPlaylist = new Playlist();
        newPlaylist.setName("New Playlist");
        newPlaylist.setUserId(1);
        assertDoesNotThrow(() -> playlistService.addPlaylist(newPlaylist));

        List<Playlist> playlists = playlistService.getAllPlaylists();
        assertEquals(3, playlists.size());
        assertTrue(playlists.stream().anyMatch(playlist -> playlist.getName().equals("New Playlist")));
    }

    @Test
    @DisplayName("Add Playlist Service Test With Empty Name")
    public void addPlaylistServiceTestWithEmptyName() {
        Playlist newPlaylist = new Playlist();
        assertThrows(DataValidationException.class, () -> playlistService.addPlaylist(newPlaylist));
    }

    @Test
    @DisplayName("Add Playlist Service Test With Nonexistent User")
    public void addPlaylistServiceTestWithNonexistentUser() {
        Playlist newPlaylist = new Playlist();
        newPlaylist.setName("New Playlist");
        newPlaylist.setUserId(99);
        assertThrows(UserNotFoundException.class, () -> playlistService.addPlaylist(newPlaylist));
    }

    @Test
    @DisplayName("Update Playlist Service Test")
    public void updatePlaylistServiceTest() throws PlaylistNotFoundException {
        Playlist playlist = new Playlist();
        playlist.setId(1);
        playlist.setName("Updated Playlist");

        assertDoesNotThrow(() -> playlistService.updatePlaylist(playlist));
        Playlist updatedPlaylist = playlistService.getPlaylistById(1);
        assertEquals("Updated Playlist", updatedPlaylist.getName());
    }

    @Test
    @DisplayName("Update Playlist Service Test With Empty Name")
    public void updatePlaylistServiceTestWithEmptyName() {
        Playlist playlist = new Playlist();
        playlist.setId(1);
        assertThrows(DataValidationException.class, () -> playlistService.updatePlaylist(playlist));
    }

    @Test
    @DisplayName("Update Playlist Service Test With Nonexistent Playlist")
    public void updatePlaylistServiceTestWithNonexistentPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setId(99);
        playlist.setName("Updated Playlist");
        assertThrows(PlaylistNotFoundException.class, () -> playlistService.updatePlaylist(playlist));
    }

    @Test
    @DisplayName("Delete Playlist Service Test")
    public void deletePlaylistServiceTest() {
        assertDoesNotThrow(() -> playlistService.deletePlaylist(1));
        assertThrows(PlaylistNotFoundException.class, () -> playlistService.getPlaylistById(1));
    }

    @Test
    @DisplayName("Delete Playlist Service Test With Nonexistent Playlist")
    public void deletePlaylistServiceTestWithNonexistentPlaylist() {
        assertThrows(PlaylistNotFoundException.class, () -> playlistService.deletePlaylist(99));
    }
}
