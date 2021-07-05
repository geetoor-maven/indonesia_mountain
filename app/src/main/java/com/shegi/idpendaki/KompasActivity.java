package com.shegi.idpendaki;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class KompasActivity extends AppCompatActivity implements SensorEventListener {
    ImageView img_kompas;
    private float[] mGravity = new float[3];
    private float[] mGeometric = new float[3];
    private float azimut = 0f;
    private float currecrazimuth = 0f;
    private SensorManager sensorManager;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kompas);

        img_kompas = findViewById(R.id.img_kompas);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        adView = findViewById(R.id.adViewkompas);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.97f;
        synchronized (this){
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                mGravity[0] = alpha * mGravity[0]+(1-alpha)*event.values[0];
                mGravity[1] = alpha * mGravity[1]+(1-alpha)*event.values[1];
                mGravity[2] = alpha * mGravity[2]+(1-alpha)*event.values[2];
            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                mGeometric[0] = alpha * mGeometric[0]+(1-alpha)*event.values[0];
                mGeometric[1] = alpha * mGeometric[1]+(1-alpha)*event.values[1];
                mGeometric[2] = alpha * mGeometric[2]+(1-alpha)*event.values[2];
            }

            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R,I,mGravity,mGeometric);
            if (success){
                float orientation[] = new float[3];
                SensorManager.getOrientation(R,orientation);
                azimut = (float)Math.toDegrees(orientation[0]);
                azimut = (azimut+360)%360;

                Animation anim = new RotateAnimation(-currecrazimuth,-azimut,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                currecrazimuth = azimut;
                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);

                img_kompas.startAnimation(anim);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}