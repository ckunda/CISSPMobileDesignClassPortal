package com.cisp362.cisspmobiledesignclassportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity
implements View.OnClickListener {

    public static final String ENGLISH_L = "English";
    public static final String SPANISH_L = "Spanish";
    private TextView welcomeTextView, scaTextView;
    private RadioButton englishRadio, spanishRadio;
    private Spinner scaSpinner;
    private String languageChoice, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Get Intent message
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Get Language choice
        englishRadio = (RadioButton) findViewById(R.id.radioEnglish);
        spanishRadio = (RadioButton) findViewById(R.id.radioSpanish);
        if (englishRadio.isChecked()) languageChoice = ENGLISH_L;
        if (spanishRadio.isChecked()) languageChoice = SPANISH_L;
        setLanguage(languageChoice);

        // Set radio button listeners
        englishRadio.setOnClickListener(this);
        spanishRadio.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.radioEnglish :
                if (englishRadio.isChecked()) languageChoice = ENGLISH_L;
                break;

            case R.id.radioSpanish :
                if (spanishRadio.isChecked()) languageChoice = SPANISH_L;
                break;
        }

        setLanguage(languageChoice);

    }

    public void setLanguage(String languageC) {

        welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        scaTextView = (TextView) findViewById(R.id.scaTextView);
        scaSpinner = (Spinner) findViewById(R.id.spnStudent);

        switch (languageC) {

            case ENGLISH_L:

                welcomeTextView.setText(getString(R.string.strWelcome) +
                    ", " + message + getString(R.string.strLanguage));
                scaTextView.setText(getString(R.string.strSacCity));
                ArrayAdapter<CharSequence> adapterEng = ArrayAdapter.createFromResource(
                        this, R.array.student_array, android.R.layout.simple_spinner_item);
                adapterEng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                scaSpinner.setAdapter(adapterEng);
                break;

            case SPANISH_L:

                welcomeTextView.setText(getString(R.string.spn_strWelcome) +
                        ", " + message + getString(R.string.spn_strLanguage));
                scaTextView.setText(getString(R.string.spn_strSacCity));
                ArrayAdapter<CharSequence> adapterSpn = ArrayAdapter.createFromResource(
                        this, R.array.spn_student_array, android.R.layout.simple_spinner_item);
                adapterSpn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                scaSpinner.setAdapter(adapterSpn);
                break;
        }

    }
}
