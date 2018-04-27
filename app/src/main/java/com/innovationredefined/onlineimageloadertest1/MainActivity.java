package com.innovationredefined.onlineimageloadertest1;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.support.annotation.Nullable;
import android.support.v4.net.TrafficStatsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewPicasso, imageViewGlide;
    TextView textView_time_picasso, textView_data_picasso, textView_time_glide, textView_data_glide;
    long startTime, endTime;
    Button buttonPicasso, buttonGlide, buttonBoth;
    Context context;
    String url;
    int uid;
    long startingData, endingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPicasso = findViewById(R.id.button_picasso);
        buttonGlide = findViewById(R.id.button_glide);
        buttonBoth = findViewById(R.id.button_both);
        imageViewPicasso = findViewById(R.id.imageview_picasso);
        imageViewGlide = findViewById(R.id.imageview_glide);
        textView_time_picasso = findViewById(R.id.textview_time_picasso);
        textView_data_picasso = findViewById(R.id.textview_data_picasso);
        textView_time_glide = findViewById(R.id.textview_time_glide);
        textView_data_glide = findViewById(R.id.textview_data_glide);
        context = this;
        try {
            uid = getPackageManager().getApplicationInfo(getPackageName(), 0).uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        url = "https://i.imgur.com/ubnjlpo.jpg";

        buttonPicasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageView1.setVisibility(View.GONE);
                startingData = TrafficStats.getUidTxBytes(uid);
                startTime = System.currentTimeMillis();
                Picasso.get().load(url).into(imageViewPicasso, new Callback() {
                    @Override
                    public void onSuccess() {
                        //imageView1.setVisibility(View.VISIBLE);
                        endingData = TrafficStats.getUidTxBytes(uid);
                        endTime = System.currentTimeMillis();
                        Toast.makeText(getApplicationContext(), "Loaded", Toast.LENGTH_SHORT).show();
                        String dataText = "Data: " + String.valueOf(endingData - startingData) + " bytes";
                        String timeText = "Time: " + String.valueOf(endTime - startTime) + "ms";
                        textView_data_picasso.setText(dataText);
                        textView_time_picasso.setText(timeText);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });

        buttonGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageView1.setVisibility(View.GONE);
                startingData = TrafficStats.getUidTxBytes(uid);
                startTime = System.currentTimeMillis();
                Glide.with(context).load(url).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //imageView1.setVisibility(View.VISIBLE);
                        endingData = TrafficStats.getUidTxBytes(uid);
                        endTime = System.currentTimeMillis();
                        Toast.makeText(getApplicationContext(), "Loaded", Toast.LENGTH_SHORT).show();
                        String dataText = "Data: " + String.valueOf(endingData - startingData) + " bytes";
                        String timeText = "Time: " + String.valueOf(endTime - startTime) + "ms";
                        textView_data_glide.setText(dataText);
                        textView_time_glide.setText(timeText);
                        return false;
                    }
                }).into(imageViewGlide);
            }
        });

        buttonBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Picasso
                startingData = TrafficStats.getUidTxBytes(uid);
                startTime = System.currentTimeMillis();
                Picasso.get().load(url).into(imageViewPicasso, new Callback() {
                    @Override
                    public void onSuccess() {
                        //imageView1.setVisibility(View.VISIBLE);
                        long endingDataPic = TrafficStats.getUidTxBytes(uid);
                        long endTimePic = System.currentTimeMillis();
                        Toast.makeText(getApplicationContext(), "Loaded", Toast.LENGTH_SHORT).show();
                        String dataText = "Data: " + String.valueOf(endingDataPic - startingData) + " bytes";
                        String timeText = "Time: " + String.valueOf(endTimePic - startTime) + "ms";
                        textView_data_picasso.setText(dataText);
                        textView_time_picasso.setText(timeText);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

                Glide.with(context).load(url).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //imageView1.setVisibility(View.VISIBLE);
                        long endingDataGlide = TrafficStats.getUidTxBytes(uid);
                        long endTimeGlide = System.currentTimeMillis();
                        Toast.makeText(getApplicationContext(), "Loaded", Toast.LENGTH_SHORT).show();
                        String dataText = "Data: " + String.valueOf(endingDataGlide - startingData) + " bytes";
                        String timeText = "Time: " + String.valueOf(endTimeGlide - startTime) + "ms";
                        textView_data_glide.setText(dataText);
                        textView_time_glide.setText(timeText);
                        return false;
                    }
                }).into(imageViewGlide);
            }
        });
    }
}
