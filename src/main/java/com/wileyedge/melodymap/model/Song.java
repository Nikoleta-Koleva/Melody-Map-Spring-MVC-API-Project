package com.wileyedge.melodymap.model;

import java.util.Objects;

public class Song {
    private String id;
    private String title;
    private String duration;
    private Artist artist;

    public Song() {}

    public Song(String id, String title, String duration, Artist artist) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.title);
        hash = 89 * hash + Objects.hashCode(this.duration);
        hash = 89 * hash + Objects.hashCode(this.artist);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Song other = (Song) obj;
        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.title, other.title) &&
                Objects.equals(this.duration, other.duration) &&
                Objects.equals(this.artist, other.artist);
    }
}