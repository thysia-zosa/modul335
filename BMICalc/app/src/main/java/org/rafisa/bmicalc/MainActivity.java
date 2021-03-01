package org.rafisa.bmicalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button calculate;
    private EditText weight;
    private EditText height;
    private Intent intent;
    protected static final String WEIGHT = "weight";
    protected static final String HEIGHT = "height";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculate = findViewById(R.id.button);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        calculate.setOnClickListener(v -> {
            intent = new Intent(this, ResultActivity.class);
            intent.putExtra(WEIGHT, weight.getText().toString());
            intent.putExtra(HEIGHT, height.getText().toString());
            startActivity(intent);
//            System.out.println("Gewicht: " + weight.getText().toString());
//            System.out.println("Höhe: " + height.getText().toString());
        });
    }

}