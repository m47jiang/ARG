package com.example.michel.arg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.renderscript.Byte3;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private MediaPlayer mp;

    public Controller(Context context) {
        this.mainActivity = (MainActivity) context;
        this.model = mainActivity.getModel();
        this.homeView = mainActivity.getHomeView();
        //createBitmaps();
    }

    public void media (){
        mp = MediaPlayer.create(mainActivity, R.raw.bg);
        mp.start();
    }

    public void takePicture() {
        camera = MainActivity.getCameraInstance();

        Log.e("cameraexception", "took a picture");

        mainActivity.isTakingPicture = true;
        //camera.startPreview();
        camera.takePicture(null, null, null, new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //BitmapFactory.Options options=new BitmapFactory.Options();
                //options.inSampleSize = 5;
                //image=BitmapFactory.decodeByteArray(data,0,data.length,options);
                try {
                    sendImage(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mainActivity.isTakingPicture = false;
                camera.startPreview();
            }
        });
        Log.e("cameraexception", "took a picture");
    }

    public void initStage (LayoutInflater controlInflater, android.content.Context resource){
        controlInflater = LayoutInflater.from(resource);

        View viewControl = controlInflater.inflate(R.layout.subtitles, null);
        View viewControl2 = controlInflater.inflate(R.layout.tunnelvision, null);
        ViewGroup.LayoutParams layoutParamsControl
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);

        mainActivity.addContentView(viewControl, layoutParamsControl);
        mainActivity.addContentView(viewControl2, layoutParamsControl);
    }

    public void changeStage (TextView sub, ImageView image){
        int i = 0;
        for (; i < model.subtitles.length; i++) {
            if (sub.getText().equals(model.subtitles[i]))
                break;
        }

        if (i != model.subtitles.length-1){
            sub.setText(model.subtitles[i+1]);
            image.setImageResource(model.stages[i+1]);
        }
        else {
            mainActivity.setContentView(R.layout.activity_main);
            mp.stop();
        }
    }

    public void sendImage(final byte[] data) throws IOException {

        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap scale = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 5, bmp.getHeight() / 5, true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        scale.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        final byte[] newdata = outputStream.toByteArray();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    OkHttpClient client = new OkHttpClient();

                    Log.d("POST RUN", "post call");
                    MediaType mediaType = MediaType.parse("image/jpeg");
                    RequestBody filebody = RequestBody.create(mediaType, newdata);
                    MultipartBody body = new MultipartBody.Builder().addFormDataPart("file", "whatever.jpg", filebody).build();
                    Request request = new Request.Builder()
                            .url("https://api.havenondemand.com/1/api/sync/ocrdocument/v1?apikey=b14a0613-91b1-4485-9b9b-41225643cb94&mode=scene_photo")
                            .post(body)
                            .addHeader("cache-control", "no-cache")
                            .build();

                    Log.d("POST RUN", "executing post call");
                    Response response = client.newCall(request).execute();
                    Log.d("POST RUN", "post call finished");
                    String responseBody = response.body().string();
                    String mob = response.message();
                    Log.d("POST RUN", "response body " + responseBody);
                    Log.d("POST RUN", "mob " + mob);
                    /*OkHttpClient client = new OkHttpClient();
                    String url = "https://api.havenondemand.com/1/api/sync/ocrdoocument/v1";

                    RequestBody body = RequestBody.;
                    //MultipartBody body = new MultipartBody.Builder().addFormDataPart("name", "whatever.jpg", RequestBody.create(MediaType.parse("image/jpeg"), data)).build();
                    //Request request = new Request.Buildeir().url(url).post(body).build();

                    Request request = new Request.Builder()
                            .url(url)
                            .addHeader("url", "http://www.brainyquote.com/photos/a/abrahamlincoln137180.jpg")
                            .addHeader("apikey", "b14a0613-91b1-4485-9b9b-41225643cb94")
                            .build();//                            .post()
                    Response response = client.newCall(request).execute();
                    String mob = response.message();
                    //return response.body().string();*/

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };
        thread.start();
    }
}
