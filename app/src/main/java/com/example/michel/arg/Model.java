package com.example.michel.arg;

import android.content.Context;

/**
 * Created by michel on 11/06/16.
 */
public class Model {
    private Context context;
    public static final String subtitle = "Sub";
    public int state;

    public Model(Context context) {
        this.context = context;
        state = 1;
        //initializeFields();
        //setScreenSize();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public final String[] subtitles = new String [] {
        "Find the name of this hackathon.",
        "Call your friends",
        "Get Outside",
        "Subtitle4",
        "Subtitle5"
    };

    public final int [] stages = new int [] {
        R.drawable.stage_01,
        R.drawable.stage_02,
        R.drawable.stage_03,
        R.drawable.stage_04,
        R.drawable.stage_05
    };
}
