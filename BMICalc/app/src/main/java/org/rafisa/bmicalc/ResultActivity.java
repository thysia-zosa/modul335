package org.rafisa.bmicalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {

    Calculator calculator;
    boolean mBound = false;

    private TextView bmiText;
    private TextView classification;
    private Bundle extras;
    private String height;
    private String weight;
    private double bmiValue;
    private String[] result;
//    private final DecimalFormat df = new DecimalFormat("###.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        bmiText = findViewById(R.id.bmi);
        classification = findViewById(R.id.classification);
        extras = getIntent().getExtras();
        weight = extras.getString(MainActivity.WEIGHT);
        height = extras.getString(MainActivity.HEIGHT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, Calculator.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Calculator.LocalBinder binder = (Calculator.LocalBinder) service;
            calculator = binder.getService();
            mBound = true;
            result = calculator.calculate(weight, height);
            bmiText.setText(result[0]);
            classification.setText(result[1]);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    //    private double calculateBMI(String weight, String height) {
//        double weightValue = Double.parseDouble(weight);
//        double heightValue = Double.parseDouble(height);
//        return weightValue / (heightValue * heightValue);
//    }
//
//    private String getClassification(double bmi) {
//        if (bmi < 18.5) {
//            return "Untergewicht";
//        } else if (bmi < 25.0) {
//            return "Normalgewicht";
//        } else if (bmi < 30.0) {
//            return "Präadipositas";
//        } else if (bmi < 35.0) {
//            return "Adipositas I. Klasse";
//        } else if (bmi < 40.0) {
//            return "Adipositas II. Klasse";
//        } else {
//            return "Adipositas III. Klasse";
//        }
//    }
}