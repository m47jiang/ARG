package com.example.michel.arg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by michel on 11/06/16.
 */
public class Controller {

    private MainActivity mainActivity;
    private Model model;
    private Camera camera;
    private Camera.ShutterCallback shutterCallback;
    private Bitmap image;
    //this can be changed
    private RelativeLayout homeView;


    public Controller(Context context) {
        this.mainActivity = (MainActivity)context;
        this.model = mainActivity.getModel();
        this.homeView = mainActivity.getHomeView();
        //createBitmaps();
    }

    public void media (){
        MediaPlayer mp = MediaPlayer.create(mainActivity, R.raw.bg);
        mp.start();
    }

    public void takePicture() {
        camera = mainActivity.getCameraInstance();

        //camera.takePicture(null,null,null,null);
        Log.e("cameraexception", "took a picture");

        //camera.startPreview();
        camera.takePicture(null,null,null, new Camera.PictureCallback(){

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //BitmapFactory.Options options=new BitmapFactory.Options();
                //options.inSampleSize = 5;
                //image=BitmapFactory.decodeByteArray(data,0,data.length,options);
                camera.startPreview();
            }
        });
        Log.e("cameraexception", "took a picture");
    }

}
