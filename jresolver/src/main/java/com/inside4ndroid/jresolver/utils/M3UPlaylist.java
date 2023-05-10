package com.inside4ndroid.jresolver.utils;

import java.util.ArrayList;

public class M3UPlaylist {

    private String playlistName;

    private String playlistParams;

    private ArrayList<M3UItem> playlistItems;

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistParams() {
        return playlistParams;
    }

    public void setPlaylistParams(String playlistParams) {
        this.playlistParams = playlistParams;
    }

    public ArrayList<M3UItem> getPlaylistItems() {
        return playlistItems;
    }

    public void setPlaylistItems(ArrayList<M3UItem> playlistItems) {
        this.playlistItems = playlistItems;
    }

    public String getSingleParameter(String paramName) {
        if(this.playlistParams == null)
            return "";

        String[] paramsArray = this.playlistParams.split(" ");
        for (String parameter : paramsArray) {
            if (parameter.contains(paramName)) {
                return parameter.substring(parameter.indexOf(paramName) + paramName.length()).replace("=", "");
            }
        }
        return "";
    }
}