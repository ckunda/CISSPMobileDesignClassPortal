package com.cisp362.cisspmobiledesignclassportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    public static final String ENGLISH_L = "English";
    public static final String SPANISH_L = "Spanish";
    private String languageChoice;
    private TextView txtHi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Get Intent message
        Intent intent = getIntent();
        languageChoice = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        txtHi = (TextView) findViewById(R.id.txtHi);
        // Get Language choice
        setLanguage();

    }

    public void setLanguage() {

        switch (languageChoice) {

            case ENGLISH_L:
                txtHi.setText(getString(R.string.strHi));
                break;

            case SPANISH_L:
                txtHi.setText(getString(R.string.spn_strHi));
                break;
        }
    }
}
