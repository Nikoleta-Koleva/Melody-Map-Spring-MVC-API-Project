DROP DATABASE IF EXISTS spotifydb;

CREATE DATABASE spotifydb;
USE spotifydb;

CREATE TABLE IF NOT EXISTS artist (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS song (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    duration VARCHAR(5) NOT NULL,
    artist_id VARCHAR(255),
    FOREIGN KEY (artist_id) REFERENCES artist(id)
);

CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS playlist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS playlist_song (
    playlist_id BIGINT,
    song_id VARCHAR(255),
    PRIMARY KEY (playlist_id, song_id),
    FOREIGN KEY (playlist_id) REFERENCES playlist(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES song(id)
);