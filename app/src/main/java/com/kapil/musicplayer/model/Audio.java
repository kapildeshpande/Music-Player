package com.kapil.musicplayer.model;

public class Audio {

    private String data;
    private String title;
    private String album;
    private String artist;
    private String songDuration;
    private String albumArtUri;

    public Audio(String data, String title, String album, String artist,String songDuration,String albumArtUri) {
        this.data = data;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.songDuration = songDuration;
        this.albumArtUri = albumArtUri;
    }

    public Audio () {}


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setSongDuration(String songDuration) { this.songDuration = songDuration; }

    public String getSongDuration() { return songDuration; }

    public String getAlbumArtUri() {
        return albumArtUri;
    }

    public void setAlbumArtUri(String albumArtUri) {
        this.albumArtUri = albumArtUri;
    }
}