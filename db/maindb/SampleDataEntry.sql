USE spotifydb;
-- Insert sample data into User table
INSERT INTO user (username, password) VALUES ('user1', 'password1');
INSERT INTO user (username, password) VALUES ('user2', 'password2');

-- Insert sample data into Artist table
INSERT INTO artist (id, name) VALUES (1, 'Artist 1');

-- Insert sample data into Song table with artist_id
INSERT INTO song (id, title, duration, artist_id) VALUES (1, 'Song 1', '03:30', 1);

-- Insert sample data into Playlist table
INSERT INTO playlist (name, description, user_id) VALUES ('My Playlist', 'A great playlist', 1);

-- Insert relationships into Playlist_Song table
INSERT INTO playlist_song (playlist_id, song_id) VALUES (1, 1);

-- Select all data to verify inserts
SELECT * FROM user;
SELECT * FROM playlist;
SELECT * FROM song;
SELECT * FROM artist;
SELECT * FROM playlist_song;
