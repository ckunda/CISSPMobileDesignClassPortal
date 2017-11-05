package com.cisp362.cisspmobiledesignclassportal;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "com.cisp362.cisspmobiledesignclassportal";
    public static final String ENGLISH_L = "English";
    public static final String SPANISH_L = "Spanish";
    public static final String AUTHOR = "CKUNDA";

    private TextView welcomeTextView, scaTextView, dateTimeTextView,
                    nameTextView, emailTextView;
    private EditText dateEditText, timeEditText, nameEditText, emailEditText;
    private Button dateButton, timeButton;
    private RadioButton englishRadio, spanishRadio;
    private Spinner scaSpinner;
    private String languageChoice, message;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Get Intent message (User ID)
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Get Language choice
        setLanguage();

        // Set radio button listeners
        englishRadio.setOnClickListener(this);
        spanishRadio.setOnClickListener(this);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);

        dateEditText = (EditText) findViewById(R.id.dateEditText);
        timeEditText = (EditText) findViewById(R.id.timeEditText);
        nextButton = (Button) findViewById(R.id.btnNext);

        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);
        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        // Protect date / time fields
        dateEditText.setEnabled(false);
        dateEditText.setClickable(false);
        dateEditText.setFocusable(false);
        timeEditText.setEnabled(false);
        timeEditText.setClickable(false);
        timeEditText.setFocusable(false);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.radioEnglish :
            case R.id.radioSpanish :
                setLanguage();
                break;
            case R.id.dateButton :
                setDate();
                break;
            case R.id.timeButton :
                setTime();
                break;
            case R.id.btnNext :
                if (validate()) {
                    saveToFile();
                    String message = scaSpinner.getSelectedItem().toString() + "::" +
                            languageChoice + "::" +
                            nameEditText.getText().toString() + "::" +
                            emailEditText.getText().toString() + "::" +
                            dateEditText.getText().toString() + "::" +
                            timeEditText.getText().toString();
                    Intent intent = new Intent(this, Main3Activity.class);
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                }
                break;
        }
    }

    public boolean validate() {
        if (nameEditText.getText().toString().isEmpty() ||
            emailEditText.getText().toString().isEmpty() ||
            dateEditText.getText().toString().isEmpty() ||
            timeEditText.getText().toString().isEmpty()) {
            AlertDialog.Builder dltAlert = new AlertDialog.Builder(this);
            dltAlert.setTitle("CISP Mobile Design Class App");
            dltAlert.setMessage("Please enter values in name, email, date, and time fields.");
            dltAlert.setPositiveButton("OK", null);
            dltAlert.setCancelable(true);
            dltAlert.create().show();
            return false;
        }
        return true;
    }

    public void saveToFile() {

        String data = scaSpinner.getSelectedItem().toString() + " " +
                        languageChoice + " " +
                        nameEditText.getText().toString() + " " +
                        emailEditText.getText().toString() + " " +
                        dateEditText.getText().toString() + " " +
                        timeEditText.getText().toString() + " " +
                        AUTHOR + "\n";
        writeToFile(data, this);
        String temp = readFromFile(this);
        System.out.println("Reading from File");
        System.out.println(temp);
    }

    public void setDate() {

        // Get Current Date
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Display date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePicker,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateEditText.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void setTime() {

        // Get Current Time
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Display time picker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DatePicker,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        timeEditText.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);

        timePickerDialog.show();
    }

    public void setLanguage() {

        englishRadio = (RadioButton) findViewById(R.id.radioEnglish);
        spanishRadio = (RadioButton) findViewById(R.id.radioSpanish);
        if (englishRadio.isChecked()) languageChoice = ENGLISH_L;
        if (spanishRadio.isChecked()) languageChoice = SPANISH_L;

        welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        scaTextView = (TextView) findViewById(R.id.scaTextView);
        scaSpinner = (Spinner) findViewById(R.id.spnStudent);
        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);

        switch (languageChoice) {

            case ENGLISH_L:

                welcomeTextView.setText(getString(R.string.strWelcome) +
                    ", " + message + getString(R.string.strLanguage));
                scaTextView.setText(getString(R.string.strSacCity));
                ArrayAdapter<CharSequence> adapterEng = ArrayAdapter.createFromResource(
                        this, R.array.student_array, android.R.layout.simple_spinner_item);
                adapterEng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                scaSpinner.setAdapter(adapterEng);
                dateButton.setText(getString(R.string.strDate));
                timeButton.setText(getString(R.string.strTime));
                nameTextView.setText(getString(R.string.strName));
                emailTextView.setText(getString(R.string.strEmail));
                break;

            case SPANISH_L:

                welcomeTextView.setText(getString(R.string.spn_strWelcome) +
                        ", " + message + getString(R.string.spn_strLanguage));
                scaTextView.setText(getString(R.string.spn_strSacCity));
                ArrayAdapter<CharSequence> adapterSpn = ArrayAdapter.createFromResource(
                        this, R.array.spn_student_array, android.R.layout.simple_spinner_item);
                adapterSpn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                scaSpinner.setAdapter(adapterSpn);
                dateButton.setText(getString(R.string.spn_strDate));
                timeButton.setText(getString(R.string.spn_strTime));
                nameTextView.setText(getString(R.string.spn_strName));
                emailTextView.setText(getString(R.string.spn_strEmail));
                break;
        }
    }

    private void writeToFile(String data, Context context) {

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            System.out.println("Wrote successfully");
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            System.out.println("File Failed to open");
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if (inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
