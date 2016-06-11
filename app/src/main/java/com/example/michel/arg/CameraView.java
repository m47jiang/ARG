package com.example.michel.arg;

import android.hardware.Camera;
import android.widget.RelativeLayout;

/**
 * Created by michel on 11/06/16.
 */
public class CameraView extends RelativeLayout {

    private MainActivity mainActivity;
    private Model model;

    public CameraView(MainActivity mainActivity) {
        super(mainActivity);
        this.mainActivity = mainActivity;
        this.model = mainActivity.getModel();
        startCamera();
        //initAppView();
        //updateUILoop();
    }

    private void startCamera() {
        Camera object = null;
        object = Camera.open();
    }
}
