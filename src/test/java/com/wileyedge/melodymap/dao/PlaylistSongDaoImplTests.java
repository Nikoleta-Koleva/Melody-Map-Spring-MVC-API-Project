package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.TestApplicationConfiguration;
import com.wileyedge.melodymap.model.Playlist;
import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.service.PlaylistSongDaoStubImpl;
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
public class PlaylistSongDaoImplTests {

    private PlaylistSongDao playlistSongDao;
    private PlaylistSongDaoStubImpl stubImpl;

    @BeforeEach
    public void setUp() {
        // Use stub implementation for testing
        stubImpl = new PlaylistSongDaoStubImpl();
        playlistSongDao = stubImpl;

        // Clean up all playlist-song associations
        cleanUpPlaylistSongs();

        // Add test data
        Song song1 = new Song();
        song1.setId("song1");
        song1.setTitle("Test Song 1");
        song1.setDuration(String.valueOf(300));
        stubImpl.addSong(song1);

        Song song2 = new Song();
        song2.setId("song2");
        song2.setTitle("Test Song 2");
        song2.setDuration(String.valueOf(200));
        stubImpl.addSong(song2);

        Playlist playlist1 = new Playlist();
        playlist1.setId(1);
        playlist1.setName("Test Playlist 1");
        playlist1.setDescription("Description 1");
        stubImpl.addPlaylist(playlist1);

        Playlist playlist2 = new Playlist();
        playlist2.setId(2);
        playlist2.setName("Test Playlist 2");
        playlist2.setDescription("Description 2");
        stubImpl.addPlaylist(playlist2);
    }

    // Clean up all playlist-song associations
    @AfterEach
    public void cleanUp() {
        cleanUpPlaylistSongs();
    }

    private void cleanUpPlaylistSongs() {
        stubImpl.clear();
    }

    @Test
    public void testAddSongToPlaylist() {
        int playlistId = 1;
        String songId = "song1";

        playlistSongDao.addSongToPlaylist(playlistId, songId);

        List<Song> songsInPlaylist = playlistSongDao.getSongsInPlaylist(playlistId);
        assertNotNull(songsInPlaylist);
        assertEquals(1, songsInPlaylist.size());
        assertEquals(songId, songsInPlaylist.get(0).getId());
    }

    @Test
    public void testRemoveSongFromPlaylist() {
        int playlistId = 1;
        String songId = "song1";

        playlistSongDao.addSongToPlaylist(playlistId, songId);
        playlistSongDao.removeSongFromPlaylist(playlistId, songId);

        List<Song> songsInPlaylist = playlistSongDao.getSongsInPlaylist(playlistId);
        assertNotNull(songsInPlaylist);
        assertTrue(songsInPlaylist.isEmpty());
    }

    @Test
    public void testGetSongsInPlaylist() {
        int playlistId = 1;
        String songId1 = "song1";
        String songId2 = "song2";

        playlistSongDao.addSongToPlaylist(playlistId, songId1);
        playlistSongDao.addSongToPlaylist(playlistId, songId2);

        List<Song> songsInPlaylist = playlistSongDao.getSongsInPlaylist(playlistId);
        assertNotNull(songsInPlaylist);
        assertEquals(2, songsInPlaylist.size());
        assertTrue(songsInPlaylist.stream().anyMatch(song -> song.getId().equals(songId1)));
        assertTrue(songsInPlaylist.stream().anyMatch(song -> song.getId().equals(songId2)));
    }

    @Test
    public void testGetPlaylistsContainingSong() {
        int playlistId1 = 1;
        int playlistId2 = 2;
        String songId = "song1";

        playlistSongDao.addSongToPlaylist(playlistId1, songId);
        playlistSongDao.addSongToPlaylist(playlistId2, songId);

        List<Playlist> playlistsContainingSong = playlistSongDao.getPlaylistsContainingSong(songId);
        assertNotNull(playlistsContainingSong);
        assertEquals(2, playlistsContainingSong.size());
        assertTrue(playlistsContainingSong.stream().anyMatch(playlist -> playlist.getId() == playlistId1));
        assertTrue(playlistsContainingSong.stream().anyMatch(playlist -> playlist.getId() == playlistId2));
    }
}