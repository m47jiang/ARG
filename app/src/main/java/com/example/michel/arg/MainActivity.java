package com.example.michel.arg;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Model model;
    private Controller controller;
    private HomeView homeView;
    private CameraView camView;
    private static Camera camera = null;
    LayoutInflater controlInflater = null;
    public boolean isTakingPicture = false;

    //original code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelViewController();
        setContentView(R.layout.activity_main);
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        modelViewController();
        setContentView(camView);

        c.media();

    }*/

    public void buttonOnClick (View v) {
        controller.media();
        setContentView(camView);

        controller.initStage(controlInflater, getBaseContext()); //Adds subtitle view onto camera view

        final TextView sub = (TextView) findViewById(R.id.subtitle);
        sub.setText(model.subtitles[0]);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.changeStage(sub, ((ImageView) findViewById(R.id.tVision)));
            }
        });
    }

    private void modelViewController() {
        model = new Model(this);
        homeView = new HomeView(this);
        controller = new Controller(this);

        // DEBUG
        boolean camAvailable = checkCameraHardware(this);

        if(!camAvailable) {
            Log.d("cameraAvailability", "There is no camera available :(");
        }

        camView = new CameraView(this, getCameraInstance());
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance() {
        if (camera != null) {
            return camera;
        }
        try {
            camera = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return camera; // returns null if camera is unavailable

    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public HomeView getHomeView() {
        return homeView;
    }

    public void setHomeView(HomeView homeView) {
        this.homeView = homeView;
    }
}
