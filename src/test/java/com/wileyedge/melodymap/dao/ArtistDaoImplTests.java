package com.wileyedge.melodymap.dao;

import com.wileyedge.melodymap.TestApplicationConfiguration;
import com.wileyedge.melodymap.model.Artist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class ArtistDaoImplTests {
    @Autowired
    private ArtistDao artistDao;

    // Clean up artists
    @BeforeEach
    public void setUp() {
        List<Artist> artists = artistDao.getAllArtists();
        for (Artist artist : artists) {
            artistDao.deleteArtist(artist.getId());
        }
    }

    // Clean up artists after each test
    @AfterEach
    public void cleanUp() {
        List<Artist> artists = artistDao.getAllArtists();
        for (Artist artist : artists) {
            artistDao.deleteArtist(artist.getId());
        }
    }

    @Test
    public void testAddGetArtist() {
        Artist artist = new Artist();
        artist.setId("1");
        artist.setName("Test Artist");

        artistDao.addArtist(artist);
        Artist fromDao = artistDao.getArtistById(artist.getId());

        assertEquals(artist, fromDao);
    }

    @Test
    void testGetAllArtists() {
        Artist artist1 = new Artist("1", "Artist 1");
        Artist artist2 = new Artist("2", "Artist 2");

        artistDao.addArtist(artist1);
        artistDao.addArtist(artist2);

        List<Artist> actualArtists = artistDao.getAllArtists();

        assertEquals(2, actualArtists.size());
        assertTrue(actualArtists.contains(artist1));
        assertTrue(actualArtists.contains(artist2));
    }

    @Test
    public void testUpdateArtist() {
        Artist artist = new Artist("1", "Artist 1");
        artistDao.addArtist(artist);

        artist.setName("Updated Artist");
        artistDao.updateArtist(artist);

        Artist updatedArtist = artistDao.getArtistById("1");
        assertEquals("Updated Artist", updatedArtist.getName());
    }

    @Test
    public void testDeleteArtist() {
        Artist artist = new Artist("1", "Artist 1");

        artistDao.addArtist(artist);
        artistDao.deleteArtist("1");

        Artist deletedArtist = artistDao.getArtistById("1");

        assertNull(deletedArtist);
    }
}