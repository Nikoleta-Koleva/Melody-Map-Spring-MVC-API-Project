package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.TestApplicationConfiguration;
import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.User;
import com.wileyedge.melodymap.service.PlaylistDaoStubImpl;
import com.wileyedge.melodymap.service.UserDaoStubImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class PlaylistDaoImplTests {

    private PlaylistDao playlistDao;
    private UserDao userDao;
    private User testUser;

    @BeforeEach
    public void setUp() {
        // Use stub implementations for testing
        playlistDao = new PlaylistDaoStubImpl();
        userDao = new UserDaoStubImpl();

        cleanUpPlaylists();

        //Clean up users
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            userDao.deleteUser(user.getId());
        }

        //Add test user
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        userDao.save(testUser);
        testUser = userDao.findByUsername("testuser").orElseThrow(()
                -> new RuntimeException("User not found after saving"));
    }

    //Clean up users and playlists after each test
    @AfterEach
    public void cleanUp() {
        cleanUpPlaylists();
        if (testUser != null) {
            userDao.deleteUser(testUser.getId());
        }
    }

    private void cleanUpPlaylists() {
        List<Playlist> playlists = playlistDao.getAllPlaylists();
        for (Playlist playlist : playlists) {
            playlistDao.deletePlaylist(playlist.getId());
        }
    }

    @Test
    public void testAddGetPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setName("Test Playlist");
        playlist.setDescription("Test Description");
        playlist.setUserId(testUser.getId());
        playlistDao.addPlaylist(playlist);

        Playlist fromDao = playlistDao.getPlaylistById(playlist.getId());

        assertNotNull(fromDao);
        assertEquals(playlist.getName(), fromDao.getName());
        assertEquals(playlist.getDescription(), fromDao.getDescription());
        assertEquals(playlist.getUserId(), fromDao.getUserId());
    }

    @Test
    public void testGetAllPlaylists() {
        Playlist playlist1 = new Playlist();
        playlist1.setName("Test Playlist 1");
        playlist1.setDescription("Description 1");
        playlist1.setUserId(testUser.getId());
        playlistDao.addPlaylist(playlist1);

        Playlist playlist2 = new Playlist();
        playlist2.setName("Test Playlist 2");
        playlist2.setDescription("Description 2");
        playlist2.setUserId(testUser.getId());
        playlistDao.addPlaylist(playlist2);

        List<Playlist> playlists = playlistDao.getAllPlaylists();

        assertEquals(2, playlists.size());
        assertTrue(playlists.contains(playlist1));
        assertTrue(playlists.contains(playlist2));
    }

    @Test
    public void testUpdatePlaylist() {
        Playlist playlist = new Playlist();
        playlist.setName("Test Playlist");
        playlist.setDescription("Original Description");
        playlist.setUserId(testUser.getId());
        playlistDao.addPlaylist(playlist);

        playlist.setDescription("Updated Description");
        playlistDao.updatePlaylist(playlist);

        Playlist fromDao = playlistDao.getPlaylistById(playlist.getId());

        assertNotNull(fromDao);
        assertEquals(playlist.getName(), fromDao.getName());
        assertEquals(playlist.getDescription(), fromDao.getDescription());
        assertEquals(playlist.getUserId(), fromDao.getUserId());
    }

    @Test
    public void testDeletePlaylist() {
        Playlist playlist = new Playlist();
        playlist.setName("Test Playlist");
        playlist.setDescription("Test Description");
        playlist.setUserId(testUser.getId());
        playlistDao.addPlaylist(playlist);

        playlistDao.deletePlaylist(playlist.getId());

        Playlist fromDao = playlistDao.getPlaylistById(playlist.getId());
        assertNull(fromDao);
    }

    @Test
    public void testFindByUser() {
        Playlist playlist1 = new Playlist();
        playlist1.setName("Playlist 1");
        playlist1.setDescription("Description 1");
        playlist1.setUserId(testUser.getId());
        playlistDao.addPlaylist(playlist1);

        Playlist playlist2 = new Playlist();
        playlist2.setName("Playlist 2");
        playlist2.setDescription("Description 2");
        playlist2.setUserId(testUser.getId());
        playlistDao.addPlaylist(playlist2);

        List<Playlist> playlists = playlistDao.findByUser(testUser);

        assertNotNull(playlists);
        assertEquals(2, playlists.size());
        assertTrue(playlists.contains(playlist1));
        assertTrue(playlists.contains(playlist2));
    }
}
