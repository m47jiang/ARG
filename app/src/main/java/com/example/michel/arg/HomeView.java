package com.example.michel.arg;

import android.widget.RelativeLayout;

/**
 * Created by michel on 11/06/16.
 */
public class HomeView extends RelativeLayout {

    private MainActivity mainActivity;
    private Model model;

    public HomeView(MainActivity mainActivity) {
        super(mainActivity);
        this.mainActivity = mainActivity;
        this.model = mainActivity.getModel();
        //initAppView();
        //updateUILoop();
    }
}
