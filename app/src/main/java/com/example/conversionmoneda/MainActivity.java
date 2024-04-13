package com.example.conversionmoneda;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText amountEditText;
    private Spinner fromCurrencySpinner;
    private Spinner toCurrencySpinner;
    private TextView resultTextView;

    private String[] currencies;
    private double[] conversionRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountEditText = findViewById(R.id.amountEditText);
        fromCurrencySpinner = findViewById(R.id.fromCurrencySpinner);
        toCurrencySpinner = findViewById(R.id.toCurrencySpinner);
        resultTextView = findViewById(R.id.resultTextView);

        currencies = getResources().getStringArray(R.array.currencies_array);
        conversionRates = getConversionRates();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromCurrencySpinner.setAdapter(adapter);
        toCurrencySpinner.setAdapter(adapter);
    }

    private double[] getConversionRates() {
        Resources res = getResources();
        String[] conversionRateStrings = res.getStringArray(R.array.conversion_rates_array);
        double[] conversionRates = new double[conversionRateStrings.length];
        for (int i = 0; i < conversionRateStrings.length; i++) {
            conversionRates[i] = Double.parseDouble(conversionRateStrings[i]);
        }
        return conversionRates;
    }

    public void convert(View view) {
        String amountString = amountEditText.getText().toString();
        if (amountString.isEmpty()) {
            resultTextView.setText("Please enter amount");
            return;
        }

        double amount = Double.parseDouble(amountString);
        int fromCurrencyIndex = fromCurrencySpinner.getSelectedItemPosition();
        int toCurrencyIndex = toCurrencySpinner.getSelectedItemPosition();

        double convertedAmount = amount * (conversionRates[toCurrencyIndex] / conversionRates[fromCurrencyIndex]);

        DecimalFormat df = new DecimalFormat("#.##");
        resultTextView.setText(df.format(convertedAmount));
    }
}