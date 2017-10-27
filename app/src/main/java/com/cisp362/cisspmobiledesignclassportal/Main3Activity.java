package com.cisp362.cisspmobiledesignclassportal;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    public static final String ENGLISH_L = "English";
    public static final String SPANISH_L = "Spanish";
    private String languageChoice;
    private TextView txtHi, txtCategory, txtDesc, txtRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Get Intent message (language choice)
        Intent intent = getIntent();
        languageChoice = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        txtHi = (TextView) findViewById(R.id.txtHi);
        txtCategory = (TextView) findViewById(R.id.txtCategory);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        txtRating = (TextView) findViewById(R.id.txtRating);
        // Set Language choice
        setLanguage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.cisp_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mnuAbout:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.mnuMyApp:
                Toast.makeText(this, "My Weather App", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setLanguage() {

        switch (languageChoice) {

            case ENGLISH_L:
                txtHi.setText(getString(R.string.strHi));
                txtCategory.setText(getString(R.string.strCategory));
                txtDesc.setText(getString(R.string.strDesc));
                txtRating.setText(getString(R.string.strRating));
                break;

            case SPANISH_L:
                txtHi.setText(getString(R.string.spn_strHi));
                txtCategory.setText(getString(R.string.spn_strCategory));
                txtDesc.setText(getString(R.string.spn_strDesc));
                txtRating.setText(getString(R.string.spn_strRating));
                break;
        }
    }
}
