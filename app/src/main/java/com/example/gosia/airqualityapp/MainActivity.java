package com.example.gosia.airqualityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.gosia.airqualityapp.R.id.editTextCity;

public class MainActivity extends AppCompatActivity {

    private Button buttonOK;
    private EditText editTextCity;
    private static String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        buttonOK = (Button) findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PopUpPollution.class));

                editTextCity = (EditText)findViewById(R.id.editTextCity);
                cityName = editTextCity.getText().toString();
            }
        });
    }

    public static String getCityName() {
        return cityName;
    }
}
