package com.kapil.musicplayer.helpers;

import com.kapil.musicplayer.ui.PlaybackInfoListener;

public class SetPlaybackInfoListenerHelper {

    public static SetPlaybackInfoListenerHelper controller = new SetPlaybackInfoListenerHelper();
    private PlaybackInfoListener playbackInfoListener;

    public void setPlaybackInfoListener(PlaybackInfoListener playbackInfoListener) {
        this.playbackInfoListener = playbackInfoListener;
    }

    public PlaybackInfoListener getPlaybackInfoListener() {
        return playbackInfoListener;
    }
}
