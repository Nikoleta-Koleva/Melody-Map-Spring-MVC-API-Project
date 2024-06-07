package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.model.Artist;
import com.wileyedge.melodymap.service.exceptions.ArtistNotFoundException;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArtistServiceImplTests {
    private ArtistServiceInterface artistService;
    private ArtistDaoStubImpl artistDaoStub;

    // Ensures that each test method in the test class starts with a fresh instance of ArtistServiceImpl
    // configured with stub implementations of its dependencies (ArtistDao).
    // dependencies are injected into the ArtistServiceImpl instance
    @BeforeEach
    public void setUp() {
        artistDaoStub = new ArtistDaoStubImpl();
        artistService = new ArtistServiceImpl(artistDaoStub);
    }

    // Clean up artists
    @AfterEach
    public void cleanUp() {
        cleanUpArtists();
    }

    private void cleanUpArtists() {
        List<Artist> artists = artistDaoStub.getAllArtists();
        for (Artist artist : artists) {
            artistDaoStub.deleteArtist(artist.getId());
        }
    }

    @Test
    @DisplayName("Find Artist Service Test")
    public void findArtistServiceTest() throws ArtistNotFoundException {
        Artist returnArtist = artistService.getArtistById("1");
        assertNotNull(returnArtist);
        assertEquals("Default Artist", returnArtist.getName());
    }

    @Test
    @DisplayName("Artist Not Found Service Test")
    public void artistNotFoundServiceTest() {
        assertThrows(ArtistNotFoundException.class, () -> artistService.getArtistById("99"));
    }

    @Test
    @DisplayName("Add Artist Service Test")
    public void addArtistServiceTest() {
        Artist newArtist = new Artist();
        newArtist.setName("New Artist");
        assertDoesNotThrow(() -> artistService.addArtist(newArtist));
        List<Artist> artists = artistService.getAllArtists();
        assertEquals(2, artists.size());
        assertTrue(artists.stream().anyMatch(artist -> artist.getName().equals("New Artist")));
    }

    @Test
    @DisplayName("Add Artist Service Test With Empty Name")
    public void addArtistServiceTestWithEmptyName() {
        Artist newArtist = new Artist();
        assertThrows(DataValidationException.class, () -> artistService.addArtist(newArtist));
    }

    @Test
    @DisplayName("Update Artist Service Test")
    public void updateArtistServiceTest() throws ArtistNotFoundException {
        Artist artist = new Artist();
        artist.setId("1");
        artist.setName("Updated Artist");
        assertDoesNotThrow(() -> artistService.updateArtist(artist));
        Artist updatedArtist = artistService.getArtistById("1");
        assertEquals("Updated Artist", updatedArtist.getName());
    }

    @Test
    @DisplayName("Update Artist Service Test With Empty Name")
    public void updateArtistServiceTestWithEmptyName() {
        Artist artist = new Artist();
        artist.setId("1");
        assertThrows(DataValidationException.class, () -> artistService.updateArtist(artist));
    }

    @Test
    @DisplayName("Update Artist Service Test With Nonexistent Artist")
    public void updateArtistServiceTestWithNonexistentArtist() {
        Artist artist = new Artist();
        artist.setId("99");
        artist.setName("Updated Artist");
        assertThrows(ArtistNotFoundException.class, () -> artistService.updateArtist(artist));
    }

    @Test
    @DisplayName("Delete Artist Service Test")
    public void deleteArtistServiceTest() {
        assertDoesNotThrow(() -> artistService.deleteArtist("1"));
        assertThrows(ArtistNotFoundException.class, () -> artistService.getArtistById("1"));
    }

    @Test
    @DisplayName("Delete Artist Service Test With Nonexistent Artist")
    public void deleteArtistServiceTestWithNonexistentArtist() {
        assertThrows(ArtistNotFoundException.class, () -> artistService.deleteArtist("99"));
    }
}
