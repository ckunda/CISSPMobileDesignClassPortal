package com.cisp362.cisspmobiledesignclassportal;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Main3Activity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String ENGLISH_L = "English";
    public static final String SPANISH_L = "Spanish";
    private String studentType, languageChoice, name, email, date, time;
    private TextView txtHi, txtCategory, txtDesc, txtRating;
    private Button submit;
    private RatingBar rBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Get Intent message (language choice)
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String separated[] = message.split("::");
        studentType = separated[0];
        languageChoice = separated[1];
        name = separated[2];
        email = separated[3];
        date = separated[4];
        time = separated[5];

        txtHi = (TextView) findViewById(R.id.txtHi);
        txtCategory = (TextView) findViewById(R.id.txtCategory);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        txtRating = (TextView) findViewById(R.id.txtRating);
        submit = (Button) findViewById(R.id.btnSubmit);
        submit.setOnClickListener(this);

        // Set Language choice
        setLanguage();
    }

    @Override
    public void onClick(View v) {

        String appLink = "https://play.google.com/store/apps/details?id=com.ckunda.myweather";
        rBar1 = (RatingBar) findViewById(R.id.rBar1);
        String subject = "MyWeather App (android)";
        String emailText = "Hello " + name + ",\n\nMy name is Chakra Kunda. " +
                        "I am a student at Sacramento City Community College. " +
                        "Thank you for taking the time to review and rate my app today.\n" +
                        "\nYour rating of my app: " + rBar1.getRating() + " stars.\n" +
                        "\nYour information:\n" +
                        "\nName: " + name +
                        "\nSC Affiliation: " + studentType +
                        "\nAppointment Date/Time: " + date + " / " + time +
                        "\n\nLink to my app on Google Store: " + appLink +
                        "\n\nSincerely,\n\nChakra Kunda";

        if (languageChoice.equals(SPANISH_L)) {
            emailText = "Hola " + name + ",\n\nMi nombre es Chakra Kunda. " +
                    "Soy un estudiante en Sacramento City Community College. " +
                    "Gracias por tomarse el tiempo para revisar y calificar mi aplicación hoy.\n" +
                    "\nTu calificación de mi aplicación: " + rBar1.getRating() + " estrellas.\n" +
                    "\nTu información:\n" +
                    "\nNombre: " + name +
                    "\nAfiliación SC: " + studentType +
                    "\nFecha / Hora de la cita: " + date + " / " + time +
                    "\n\nLink to my app on Google Store: " + appLink +
                    "\n\nSinceramente,\n\nChakra Kunda";
        }

        // eMail
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + email));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailText);
        startActivity(Intent.createChooser(emailIntent, "Send feedback"));
        Log.d("Emailing Visitor", subject);


        // Write to DB
        MySQLiteHelper db = new MySQLiteHelper(this);
//        db.deleteAllAppVisitors();
        // add
        db.addAppVisitor(new AppVisitor(name, email, languageChoice,
                studentType, date, time, String.valueOf(rBar1.getNumStars())));

        // get all visitors
        List<AppVisitor> list = db.getAllAppVisitors();

        // email app visitors to me
        Intent email2Intent = new Intent(Intent.ACTION_SENDTO);
        email2Intent.setData(Uri.parse("mailto:ckunda@gmail.com"));
        email2Intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        email2Intent.putExtra(Intent.EXTRA_TEXT, list.toString());
        startActivity(Intent.createChooser(email2Intent, "App Visitors"));
        Log.d("Emailing Data", subject);
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
                return true;

            case R.id.mnuMyApp:
                Toast.makeText(this, "My Weather App", Toast.LENGTH_SHORT).show();

                // Launch MyWeather app
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.ckunda.myweather");
                if (launchIntent != null) { //null pointer check in case package name was not found
                    startActivity(launchIntent);
                }
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
