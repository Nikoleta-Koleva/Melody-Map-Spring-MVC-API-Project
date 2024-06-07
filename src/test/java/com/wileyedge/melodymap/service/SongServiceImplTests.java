package com.wileyedge.melodymap.service;

import com.wileyedge.melodymap.model.Artist;
import com.wileyedge.melodymap.model.Song;
import com.wileyedge.melodymap.service.exceptions.DataValidationException;
import com.wileyedge.melodymap.service.exceptions.SongNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SongServiceImplTests {
    private SongServiceInterface songService;
    private SongDaoStubImpl songDaoStub;
    private ArtistDaoStubImpl artistDaoStub;

    @BeforeEach
    public void setUp() {
        songDaoStub = new SongDaoStubImpl();
        artistDaoStub = new ArtistDaoStubImpl();
        songService = new SongServiceImpl(songDaoStub, artistDaoStub);
    }

    // Clean up songs and artists
    @AfterEach
    public void cleanUp() {
        cleanUpSongs();
        cleanUpArtists();
    }

    private void cleanUpSongs() {
        List<Song> songs = songDaoStub.getAllSongs();
        for (Song song : songs) {
            songDaoStub.deleteSong(song.getId());
        }
    }

    private void cleanUpArtists() {
        List<Artist> artists = artistDaoStub.getAllArtists();
        for (Artist artist : artists) {
            artistDaoStub.deleteArtist(artist.getId());
        }
    }

    @Test
    @DisplayName("Find Song Service Test")
    public void findSongServiceTest() throws SongNotFoundException {
        Song returnSong = songService.getSongById("song1");
        assertNotNull(returnSong);
        assertEquals("Default Song 1", returnSong.getTitle());
    }

    @Test
    @DisplayName("Song Not Found Service Test")
    public void songNotFoundServiceTest() {
        assertThrows(SongNotFoundException.class, () -> songService.getSongById("99"));
    }

    @Test
    @DisplayName("Add Song Service Test")
    public void addSongServiceTest() {
        Song newSong = new Song();
        Artist artist = new Artist();
        artist.setId("artist1");
        artist.setName("Default Artist 1");
        newSong.setId("song3");
        newSong.setTitle("New Song");
        newSong.setDuration("03:00");
        newSong.setArtist(artist);
        assertDoesNotThrow(() -> songService.addSong(newSong));
        List<Song> songs = songService.getAllSongs();
        assertEquals(3, songs.size());
        assertTrue(songs.stream().anyMatch(song -> song.getTitle().equals("New Song")));
    }

    @Test
    @DisplayName("Add Song Service Test With Empty Title")
    public void addSongServiceTestWithEmptyTitle() {
        Song newSong = new Song();
        Artist artist = new Artist();
        artist.setId("artist1");
        artist.setName("Default Artist 1");
        newSong.setId("song3");
        newSong.setTitle("");
        newSong.setDuration("03:00");
        newSong.setArtist(artist);
        assertThrows(IllegalArgumentException.class, () -> songService.addSong(newSong));
    }

    @Test
    @DisplayName("Add Song Service Test With Invalid Duration")
    public void addSongServiceTestWithInvalidDuration() {
        Song newSong = new Song();
        Artist artist = new Artist();
        artist.setId("artist1");
        artist.setName("Default Artist 1");
        newSong.setId("song3");
        newSong.setTitle("Invalid Duration Song");
        newSong.setDuration("1234");
        newSong.setArtist(artist);
        assertThrows(IllegalArgumentException.class, () -> songService.addSong(newSong));
    }

    @Test
    @DisplayName("Add Song Service Test With Nonexistent Artist")
    public void addSongServiceTestWithNonexistentArtist() {
        // Create a new song with a nonexistent artist
        Song newSong = new Song();
        Artist artist = new Artist();
        artist.setId("nonexistentArtist");
        artist.setName("Nonexistent Artist");
        newSong.setId("song3");
        newSong.setTitle("Nonexistent Artist Song");
        newSong.setDuration("03:00");
        newSong.setArtist(artist);

        // Add the song, expecting no exception
        assertDoesNotThrow(() -> songService.addSong(newSong));

        // Retrieve all songs and verify the new song is added
        List<Song> songs = songService.getAllSongs();
        assertEquals(3, songs.size());
        Song addedSong = songs.stream().filter(song -> song.getTitle().equals("Nonexistent Artist Song")).findFirst().orElse(null);
        assertNotNull(addedSong);

        // Retrieve the new artist to verify it was created
        Artist createdArtist = addedSong.getArtist();
        assertNotNull(createdArtist);
        assertEquals("Nonexistent Artist", createdArtist.getName());
        assertNotEquals("nonexistentArtist", createdArtist.getId()); // ID should be different
    }

    @Test
    @DisplayName("Update Song Service Test")
    public void updateSongServiceTest() throws SongNotFoundException {
        Song song = new Song();
        song.setId("song1");
        song.setTitle("Updated Song");
        song.setDuration("03:00");
        Artist artist = new Artist();
        artist.setId("artist1");
        artist.setName("Default Artist 1");
        song.setArtist(artist);
        assertDoesNotThrow(() -> songService.updateSong(song));
        Song updatedSong = songService.getSongById("song1");
        assertEquals("Updated Song", updatedSong.getTitle());
    }

    @Test
    @DisplayName("Update Song Service Test With Empty Title")
    public void updateSongServiceTestWithEmptyTitle() {
        Song song = new Song();
        song.setId("song1");
        song.setTitle("");
        song.setDuration("03:00");
        Artist artist = new Artist();
        artist.setId("artist1");
        artist.setName("Default Artist 1");
        song.setArtist(artist);
        assertThrows(DataValidationException.class, () -> songService.updateSong(song));
    }

    @Test
    @DisplayName("Update Song Service Test With Nonexistent Song")
    public void updateSongServiceTestWithNonexistentSong() {
        Song song = new Song();
        song.setId("nonexistentSong");
        song.setTitle("Nonexistent Song");
        song.setDuration("03:00");
        Artist artist = new Artist();
        artist.setId("artist1");
        artist.setName("Default Artist 1");
        song.setArtist(artist);
        assertThrows(SongNotFoundException.class, () -> songService.updateSong(song));
    }

    @Test
    @DisplayName("Delete Song Service Test")
    public void deleteSongServiceTest() throws SongNotFoundException {
        assertDoesNotThrow(() -> songService.deleteSong("song1"));
        assertThrows(SongNotFoundException.class, () -> songService.getSongById("song1"));
    }

    @Test
    @DisplayName("Delete Song Service Test With Nonexistent Song")
    public void deleteSongServiceTestWithNonexistentSong() {
        assertThrows(SongNotFoundException.class, () -> songService.deleteSong("99"));
    }
}
