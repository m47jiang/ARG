package com.example.michel.arg;

import android.content.Context;

/**
 * Created by michel on 11/06/16.
 */
public class Model {
    private Context context;
    public static final String subtitle = "Sub";

    public Model(Context context) {
        this.context = context;
        //initializeFields();
        //setScreenSize();
    }

    public final String[] subtitles = new String [] {
        "Subtitle1",
        "Subtitle2",
        "Subtitle3",
        "Subtitle4",
        "Subtitle5"
    };
}
