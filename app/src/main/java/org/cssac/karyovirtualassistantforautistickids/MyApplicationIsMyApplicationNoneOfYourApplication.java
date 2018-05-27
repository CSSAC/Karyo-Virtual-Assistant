package org.cssac.karyovirtualassistantforautistickids;

import android.app.Application;

/**
 * Created by prrateekk on 25/5/18.
 * Global Data - App Parameters
 */

public class MyApplicationIsMyApplicationNoneOfYourApplication extends Application {

    private boolean isMusicOn = true;

    public boolean getMusic() {
        return isMusicOn;
    }

    public void setMusic(Boolean isMusicOn) {
        this.isMusicOn = isMusicOn;
    }
}