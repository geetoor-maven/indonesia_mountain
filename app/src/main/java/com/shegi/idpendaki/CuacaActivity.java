package com.shegi.idpendaki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CuacaActivity extends AppCompatActivity {

    private static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";
    private static final String OPEN_WATHER_MAP_API = "78978df25ddedd49c38f5c465e845419";
    ProgressDialog progressDialog;
    TextView cityfield,detailfieds,currentTempraturfield,humidityfield,pressurefield,weatherIcon,updatedField;
    Typeface weatherFont;
    static String latitude;
    static String longtitude;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuaca);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        progressDialog = new ProgressDialog(CuacaActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Toast.makeText(this, "jika loading terlalu lama mohon keluar dan kembali lagi (If loading takes too long Exit and reopen)", Toast.LENGTH_SHORT).show();
        requestPermission();


        FusedLocationProviderClient fusedLocationProviderClient;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(CuacaActivity.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(CuacaActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!= null){

                    latitude = String.valueOf(location.getLatitude());
                    longtitude = String.valueOf(location.getLongitude());

                    weatherFont = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/weathericons-regular-webfont.ttf");

                    cityfield = findViewById(R.id.txt_city);
                    currentTempraturfield = findViewById(R.id.txt_current_field);
                    updatedField = findViewById(R.id.txt_updated);
                    detailfieds = findViewById(R.id.txt_details);
                    humidityfield = findViewById(R.id.txt_humadity);
                    pressurefield = findViewById(R.id.txt_pressure);
                    weatherIcon = findViewById(R.id.txt_icon);
                    weatherIcon.setTypeface(weatherFont);

                    final String[] jsonData = getJsonResponse();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            cityfield.setText(jsonData[0]);
                            detailfieds.setText(jsonData[1]);
                            currentTempraturfield.setText(jsonData[2]);
                            humidityfield.setText("Humadity : "+jsonData[3]);
                            pressurefield.setText("Pressure : "+jsonData[4]);
                            updatedField.setText(jsonData[5]);
                            weatherIcon.setText(Html.fromHtml(jsonData[6]));
                        }
                    },500);


                }
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        adView = findViewById(R.id.adViewcuaca);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private String[] getJsonResponse() {
        String[] jsonData = new String[7];
        JSONObject jsonWeather = null;
        try {
            jsonWeather = getWeatherJson(latitude,longtitude);
        }catch (Exception e){
            Log.d("Error","Cannot prosess Json",e);
        }

        try {
            if (jsonWeather != null){
                JSONObject details = jsonWeather.getJSONArray("weather").getJSONObject(0);
                JSONObject main = jsonWeather.getJSONObject("main");
                DateFormat df = DateFormat.getDateInstance();

                String city = jsonWeather.getString("name")+", "+jsonWeather.getJSONObject("sys").getString("country");
                String desc = details.getString("description").toLowerCase(Locale.US);
                String tempratur = String.format("%.0f",main.getDouble("temp"))+"*";
                String humadity = main.getString("humidity") + "%";
                String pressure = main.getString("pressure") + "hpa";
                String updateOn = df.format(new Date(jsonWeather.getLong("dt")*1000));
                String iconText = setWeatherIcon(details.getInt("id"),jsonWeather.getJSONObject("sys").getLong("sunrise") * 1000,
                        jsonWeather.getJSONObject("sys").getLong("sunset") * 1000);

                jsonData[0] = city;
                jsonData[1] = desc;
                jsonData[2] = tempratur;
                jsonData[3] = humadity;
                jsonData[4] = pressure;
                jsonData[5] = updateOn;
                jsonData[6] = iconText;
            }
        }catch (Exception e){

        }
        return jsonData;
    }

    public static String setWeatherIcon(int actualy, long sunrise, long sunsite){
        int id = actualy / 100;
        String icon = "";
        if (actualy == 800){
            long currentime = new Date().getTime();
            if (currentime >= sunrise && currentime < sunsite){
                icon = "&#xf00d;";
            }else {
                icon = "&#xf02e;";
            }
        }else {
            switch (id){
                case 2:
                    icon = "&#xf01e;";
                    break;
                case 3:
                    icon = "&#xf01c;";
                    break;
                case 7:
                    icon = "&#xf014;";
                    break;
                case 8:
                    icon = "&#xf013;";
                    break;
                case 6:
                    icon = "&#xf01b;";
                    break;
                case 5:
                    icon = "&#xf019;";
                    break;
            }
        }
        return icon;
    }

    public static JSONObject getWeatherJson(String lat, String lon) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL,lat,lon));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.addRequestProperty("x-api-key",OPEN_WATHER_MAP_API);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tap = "";
            while ((tap = reader.readLine()) != null){
                json.append(tap).append("\n");
            }
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if (data.getInt("cod") != 200){
                return null;
            }
            return data;
        }catch (Exception e){
            return null;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }

}