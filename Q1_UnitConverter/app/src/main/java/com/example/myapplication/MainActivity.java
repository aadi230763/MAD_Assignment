package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputValue;
    Spinner fromUnit, toUnit;
    TextView resultText;
    Button convertButton;

    String[] units = {"Feet", "Inches", "Centimeters", "Meters", "Yards"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        resultText = findViewById(R.id.resultText);
        convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });
    }

    private void convertUnits() {
        String input = inputValue.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(input);
        String from = fromUnit.getSelectedItem().toString();
        String to = toUnit.getSelectedItem().toString();

        double baseValue = convertToBaseUnit(value, from);
        double convertedValue = convertFromBaseUnit(baseValue, to);

        resultText.setText("Converted Value: " + convertedValue + " " + to);
    }

    private double convertToBaseUnit(double value, String unit) {
        switch (unit) {
            case "Feet":
                return value * 30.48;
            case "Inches":
                return value * 2.54;
            case "Centimeters":
                return value;
            case "Meters":
                return value * 100;
            case "Yards":
                return value * 91.44;
            default:
                return 0;
        }
    }

    private double convertFromBaseUnit(double baseValue, String unit) {
        switch (unit) {
            case "Feet":
                return baseValue / 30.48;
            case "Inches":
                return baseValue / 2.54;
            case "Centimeters":
                return baseValue;
            case "Meters":
                return baseValue / 100;
            case "Yards":
                return baseValue / 91.44;
            default:
                return 0;
        }
    }
}