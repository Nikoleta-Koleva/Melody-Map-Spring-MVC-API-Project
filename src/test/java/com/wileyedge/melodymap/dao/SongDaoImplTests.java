package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.TestApplicationConfiguration;
import com.wileyedge.melodymap.model.Artist;
import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.service.SongDaoStubImpl;
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
public class SongDaoImplTests {

    private SongDao songDao;
    private SongDaoStubImpl stubImpl;

    @BeforeEach
    public void setUp() {
        // Use stub implementation for testing
        stubImpl = new SongDaoStubImpl();
        songDao = stubImpl;

        // Clear all songs
        stubImpl.clear();

        // Add test data
        Artist artist1 = new Artist();
        artist1.setId("artist1");
        artist1.setName("Test Artist 1");

        Song song1 = new Song();
        song1.setId("song1");
        song1.setTitle("Test Song 1");
        song1.setDuration(String.valueOf(300));
        song1.setArtist(artist1);
        stubImpl.addSong(song1);

        Song song2 = new Song();
        song2.setId("song2");
        song2.setTitle("Test Song 2");
        song2.setDuration(String.valueOf(200));
        song2.setArtist(artist1);
        stubImpl.addSong(song2);
    }

    // Clear all songs
    @AfterEach
    public void cleanUp() {
        stubImpl.clear();
    }

    @Test
    public void testAddSong() {
        Artist artist = new Artist();
        artist.setId("artist1");
        artist.setName("Test Artist 1");

        Song song = new Song();
        song.setId("song3");
        song.setTitle("New Test Song");
        song.setDuration(String.valueOf(250));
        song.setArtist(artist);

        songDao.addSong(song);

        Song fromDao = songDao.getSongById(song.getId());
        assertNotNull(fromDao);
        assertEquals(song.getId(), fromDao.getId());
        assertEquals(song.getTitle(), fromDao.getTitle());
        assertEquals(song.getDuration(), fromDao.getDuration());
        assertEquals(song.getArtist().getId(), fromDao.getArtist().getId());
    }

    @Test
    public void testGetAllSongs() {
        List<Song> songs = songDao.getAllSongs();
        assertNotNull(songs);
        assertEquals(2, songs.size());

        Song song1 = songDao.getSongById("song1");
        Song song2 = songDao.getSongById("song2");

        assertTrue(songs.contains(song1));
        assertTrue(songs.contains(song2));
    }

    @Test
    public void testGetSongById() {
        Song song = songDao.getSongById("song1");
        assertNotNull(song);
        assertEquals("song1", song.getId());
        assertEquals("Test Song 1", song.getTitle());
        assertEquals("300", song.getDuration());
        assertEquals("artist1", song.getArtist().getId());
    }

    @Test
    public void testUpdateSong() {
        Song song = songDao.getSongById("song1");
        assertNotNull(song);

        song.setTitle("Updated Test Song 1");
        song.setDuration(String.valueOf(350));
        songDao.updateSong(song);

        Song fromDao = songDao.getSongById("song1");
        assertNotNull(fromDao);
        assertEquals("Updated Test Song 1", fromDao.getTitle());
        assertEquals("350", fromDao.getDuration());
    }

    @Test
    public void testDeleteSong() {
        songDao.deleteSong("song1");

        Song fromDao = songDao.getSongById("song1");
        assertNull(fromDao);
    }
}
