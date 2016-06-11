package com.example.michel.arg;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by michel on 11/06/16.
 */
public class Controller {

    private MainActivity mainActivity;
    private Model model;
    //this can be changed
    private RelativeLayout homeView;


    public Controller(Context context) {
        this.mainActivity = (MainActivity)context;
        this.model = mainActivity.getModel();
        this.homeView = mainActivity.getHomeView();
        //createBitmaps();
    }
}
