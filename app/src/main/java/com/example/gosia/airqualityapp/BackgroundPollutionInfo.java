package com.example.gosia.airqualityapp;

import android.os.AsyncTask;

import com.example.gosia.airqualityapp.xml.PollutionAppWidgetConfigureActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.R.attr.format;
import static com.example.gosia.airqualityapp.MainActivity.isCityFromClassMainActivity;

public class BackgroundPollutionInfo extends AsyncTask<Void, Void, Void> {

    private Document docPollution;
    private Document docWeather;
    private String textPollution;
    private String textWeather;
    private static String aqiIndex;
    private static String localMeasurementTime;
    private static String timeZoneDifference;
    private static String pollutionAnnotation;
    private static String temperature;
    private static String city = checkFromWhatClassCityIs();


    public static String getAqiIndex() {
        return aqiIndex;
    }

    public static String getPollutionAnnotation() {
        return pollutionAnnotation;
    }

    public static String getTemperature() {
        return temperature;
    }

    public static String getLocalMeasurementTime() {
        return localMeasurementTime;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            docPollution = Jsoup.connect("http://api.waqi.info/feed/" + city + "/?token=7f3db400a2fd73f8dbe7115ad3d4b322237a9d83").ignoreContentType(true).get();
            textPollution = docPollution.text();

            try {
                JSONObject data = (new JSONObject(textPollution)).getJSONObject("data");
                aqiIndex = data.getString("aqi");

                analyzePollutionData(aqiIndex);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject dataTime = (new JSONObject(textPollution)).getJSONObject("data").getJSONObject("time");
                localMeasurementTime = dataTime.getString("s");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONObject dataTime = (new JSONObject(textPollution)).getJSONObject("data").getJSONObject("time");
                timeZoneDifference = dataTime.getString("tz");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            docWeather = Jsoup.connect("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=c197531d48f9342edd4c5329d4f3d0a9").ignoreContentType(true).get();
            textWeather = docWeather.text();

            try {
                JSONObject tempData = (new JSONObject(textWeather)).getJSONObject("main");
                String kelwinTemperature = tempData.getString("temp");
                temperature = convertKelvinToCelsius(kelwinTemperature);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public void analyzePollutionData(String pPollutionInd){

        int pPollutionIndex = Integer.parseInt(pPollutionInd);

        if(pPollutionIndex >= 0 && pPollutionIndex <= 50){
            pollutionAnnotation = "Good";
        }
        else if(pPollutionIndex >= 51 && pPollutionIndex <= 100){
            pollutionAnnotation = "Moderate";
        }
        else if(pPollutionIndex >= 101 && pPollutionIndex <=150){
            pollutionAnnotation = "Unhealthy for sensitive groups";
        }
        else if(pPollutionIndex >= 151 && pPollutionIndex <= 200){
            pollutionAnnotation = "Unhealthy";
        }
        else if(pPollutionIndex >= 201 && pPollutionIndex <= 300){
            pollutionAnnotation = "Very Unhealthy";
        }
        else if(pPollutionIndex >= 301){
            pollutionAnnotation = "Hazardous";
        }
        else{
            pollutionAnnotation = "Can't analize pollution index";
        }
    }

    public static String convertKelvinToCelsius(String pTempInKelvin){
        String tempCelsius = "";
        int tempInC = (int)(Double.parseDouble(pTempInKelvin) - 273);
        tempCelsius = String.valueOf(tempInC);

        return tempCelsius;
    }

    public static String checkFromWhatClassCityIs(){
        if(isCityFromClassMainActivity()){
           city =  MainActivity.getCityName();
        }
        else if(PollutionAppWidgetConfigureActivity.iscityFromClassPollutionAppWidgetConfigureActivity()){
            city = PollutionAppWidgetConfigureActivity.getCityFromWidget();
        }
        return city;
    }

    public String setTheMeasuementTimeWithTimeZoneDifference(String pTime, String pTimeDifferent){

        Date dateTime = null;
        Date dateTimeDifferent = null;

        DateFormat formatTime = new SimpleDateFormat("YYY-mmm-ddd HH:mm:ss", Locale.ENGLISH);
        try {
             dateTime = formatTime.parse(pTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat formatTimeDifferent = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        try {
            dateTimeDifferent = formatTimeDifferent.parse(pTimeDifferent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String createWidgetInfo(){
        String widgetInfo = city  + "\n aqi index:" + aqiIndex +
                "\nTemp.: " + temperature + " \u2103" +
                "\n" + pollutionAnnotation;

        return widgetInfo;
    }
}

