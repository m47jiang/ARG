package com.example.michel.arg;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Model model;
    private Controller controller;
    private HomeView homeView;
    private CameraView camView;
    private Controller c = new Controller(this);
    private static Camera camera = null;
    //original code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Button button = (Button) v;
        modelViewController();
        setContentView(camView);
        c.media();
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
