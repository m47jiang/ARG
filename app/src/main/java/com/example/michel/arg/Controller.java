package com.example.michel.arg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private MediaPlayer voiceMp;

    public Controller(Context context) {
        this.mainActivity = (MainActivity) context;
        this.model = mainActivity.getModel();
        this.homeView = mainActivity.getHomeView();
        //createBitmaps();
    }

    public void doNextState(String input) {
        Log.d("DO NEXT STATE", "Performing the state: " + model.getState());
        if(model.getState() == 1) {
            changeStage(mainActivity.getSubTitle(), mainActivity.getOverlay());
            voiceMp = MediaPlayer.create(mainActivity, R.raw.stevepart1);
            voiceMp.start();
            voiceMp.setVolume(0.9f, 0.9f);
            model.setState(2);
        } else if(model.getState() == 2) {
            if(input.contains("angel") || input.contains("hack")) {
                changeStage(mainActivity.getSubTitle(), mainActivity.getOverlay());
                voiceMp.stop();
                voiceMp.release();
                voiceMp = MediaPlayer.create(mainActivity, R.raw.stevepart2);
                voiceMp.start();
                voiceMp.setVolume(0.9f, 0.9f);
                model.setState(3);
            }
        } else if(model.getState() == 3) {
            if(input.toLowerCase().contains("mar") || input.toLowerCase().contains("ars")) {
                changeStage(mainActivity.getSubTitle(), mainActivity.getOverlay());
                voiceMp.stop();
                voiceMp.release();
                voiceMp = MediaPlayer.create(mainActivity, R.raw.stevepart3);
                voiceMp.start();
                voiceMp.setVolume(0.9f, 0.9f);
                model.setState(4);
            }
        } else if(model.getState() == 4) {
            if(input.toLowerCase().contains("awareness")) {
                changeStage(mainActivity.getSubTitle(), mainActivity.getOverlay());
                voiceMp.stop();
                voiceMp.release();
                //No audio file
                model.setState(5);
            }
        } else if(model.getState() == 5) {
            changeStage(mainActivity.getSubTitle(), mainActivity.getOverlay());
            model.setState(1);
        }
    }

    public void media (){
        mp = MediaPlayer.create(mainActivity, R.raw.itd);
        mp.start();
        mp.setVolume(0.3f, 0.3f);
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
        Log.d("CURRENT STATE", "current state is " + model.getState());
        if (model.getState() == 5){
            mainActivity.setContentView(R.layout.activity_main);
            mp.stop();
        }
        else {
            sub.setText(model.subtitles[model.getState() - 1]);
            if(model.getState() != 1) {
                image.setImageResource(model.stages[model.getState() - 1]);
                Animation myFadeInAnimation = AnimationUtils.loadAnimation(mainActivity, R.anim.fadein);
                image.startAnimation(myFadeInAnimation);

                //image.startAnimation(myFadeInAnimation);
                //image.setAlpha(0);
                //final Drawable drawable = image.getResources().getDrawable(model.stages[model.getState() - 1]);
                //GradientDrawable gd = (GradientDrawable) drawable.;
                /*for(int i = 255; i> 0; i=i-10) {
                    drawable.mutate();
                    drawable.setAlpha(i);
                    drawable.invalidateSelf();
                }*/

                /*Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                            //GradientDrawable gd = (GradientDrawable) drawable;
                            for(int i = 255; i> 0; i--) {
                                drawable.mutate();
                                drawable.setAlpha(i);
                                drawable.invalidateSelf();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();*/
            }
        }
    }

    public void sendImage(final byte[] data) throws IOException {

        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap scale = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 5, bmp.getHeight() / 5, true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        scale.compress(Bitmap.CompressFormat.JPEG, 60, outputStream);
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

                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray jsonArray = jsonObject.getJSONArray("text_block");
                        JSONObject text = jsonArray.getJSONObject(0);

                        final String recognizedText = text.getString("text");

                        mainActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                doNextState(recognizedText);
                            }
                        });
                        Log.d("recognized text", "recognized text is: " + recognizedText);
                    } catch (Exception e) {
                        Log.e("JSON EXCEPTION", "CRITICAL ERROR PARSING JSON " + e.getMessage());
                    }


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
