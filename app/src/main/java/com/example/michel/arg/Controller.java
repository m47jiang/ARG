package com.example.michel.arg;

import android.content.Context;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.widget.RelativeLayout;

/**
 * Created by michel on 11/06/16.
 */
public class Controller {

    private MainActivity mainActivity;
    private Model model;
    private Camera camera;
    private Camera.ShutterCallback shutterCallback;
    //this can be changed
    private RelativeLayout homeView;


    public Controller(Context context) {
        this.mainActivity = (MainActivity)context;
        this.model = mainActivity.getModel();
        this.homeView = mainActivity.getHomeView();
        //createBitmaps();
    }

    public void media (){
        MediaPlayer mp = MediaPlayer.create(mainActivity, R.raw.drake);
        mp.start();
        takePicture();
    }

    private void takePicture() {
        camera = mainActivity.getCameraInstance();
        camera.takePicture(null,null,null, new Camera.PictureCallback(){

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                
                camera.startPreview();
            }
        });
    }

}
