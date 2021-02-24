package ch.informatikundautismus.counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String prefCountingKey = "counterValue";
    private int counting = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCounter();

        final TextView counter = findViewById(R.id.counter);
        counter.setText(String.valueOf(counting));

        final Button plusOne = findViewById(R.id.plusOne);
        final Button minusOne = findViewById(R.id.minusOne);
        final Button reset = findViewById(R.id.reset);

        plusOne.setOnClickListener(v -> {
            counting++;
            counter.setText(String.valueOf(counting));
        });

        minusOne.setOnClickListener(v -> {
            counting--;
            counter.setText(String.valueOf(counting));
        });

        reset.setOnClickListener(v -> {
            counting = 0;
            counter.setText(String.valueOf(counting));
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        saveCounter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveCounter();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getCounter();
    }

    private void saveCounter() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(prefCountingKey, counting);
        editor.apply();
    }

    private void getCounter() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        counting = sharedPref.getInt(prefCountingKey, 0);
    }
}