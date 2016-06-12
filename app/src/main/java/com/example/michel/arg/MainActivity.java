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
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class MainActivity extends AppCompatActivity {

    private Model model;
    private Controller controller;
    private HomeView homeView;
    private CameraView camView;
    private static Camera camera = null;
    LayoutInflater controlInflater = null;

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

        //Adds subtitle view onto camera view
        controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.subtitles, null);
        LayoutParams layoutParamsControl
                = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        this.addContentView(viewControl, layoutParamsControl);

        final TextView sub = (TextView) findViewById(R.id.subtitle);
        sub.setText(model.subtitles[0]);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.changeSubtitle(sub);
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
