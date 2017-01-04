package com.example.gosia.airqualityapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PopUpPollution extends Activity  {

    private BackgroundPollutionInfo mBackgroundPollutionInfo;
    private TextView textViewAirPollution;
    private TextView textViewIndexNumber;
    private TextView textViewPollutionAnnotation;
    private TextView textViewTemperature;
    private TextView textViewUpdatedOn;
    private Button buttonOKBack;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_pollution);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.3));

        loadData();
        init();
    }


    public void init(){
        textViewAirPollution = (TextView) findViewById(R.id.textViewAirPollution);
        textViewAirPollution.setText(textViewAirPollution.getText() + " " + MainActivity.getCityName());

        textViewIndexNumber = (TextView)findViewById(R.id.textViewIndexNumber);
        textViewIndexNumber.setText(BackgroundPollutionInfo.getAqiIndex());

        textViewPollutionAnnotation = (TextView) findViewById(R.id.textViewPollutionAnnotation);
        textViewPollutionAnnotation.setText(BackgroundPollutionInfo.getPollutionAnnotation());

        textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);
        textViewTemperature.setText("Temp.:" + BackgroundPollutionInfo.getTemperature()+ "'C");

        textViewUpdatedOn = (TextView) findViewById(R.id.textViewUpdated);
        textViewUpdatedOn.setText("Updated on:\n" + BackgroundPollutionInfo.getLocalMeasurementTime());

        buttonOKBack = (Button)findViewById(R.id.buttonOKBack);
        buttonOKBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopUpPollution.this, MainActivity.class));
            }
        });
    }

    public void loadData() {
        mBackgroundPollutionInfo = new BackgroundPollutionInfo();
        mBackgroundPollutionInfo.execute();
    }

}

