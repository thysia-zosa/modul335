package org.rafisa.bmicalc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;

public class Calculator extends Service {
    private final DecimalFormat df = new DecimalFormat("###.#");
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        Calculator getService() {
            return Calculator.this;
        }
    }

    public Calculator() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public String[] calculate(String weight, String height) {
        String[] result = new String[2];
        double bmi = calculateBMI(weight, height);
        result[0] = df.format(bmi);
        result[1] = getClassification(bmi);
        return result;
    }

    private double calculateBMI(String weight, String height) {
        double weightValue = Double.parseDouble(weight);
        double heightValue = Double.parseDouble(height);
        return weightValue / (heightValue * heightValue);
    }

    private String getClassification(double bmi) {
        if (bmi < 18.5) {
            return "Untergewicht";
        } else if (bmi < 25.0) {
            return "Normalgewicht";
        } else if (bmi < 30.0) {
            return "Präadipositas";
        } else if (bmi < 35.0) {
            return "Adipositas I. Klasse";
        } else if (bmi < 40.0) {
            return "Adipositas II. Klasse";
        } else {
            return "Adipositas III. Klasse";
        }
    }
}