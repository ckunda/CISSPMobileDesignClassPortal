package com.cisp362.cisspmobiledesignclassportal;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
implements TextView.OnEditorActionListener, View.OnClickListener {

    public static final String EXTRA_MESSAGE = "com.cisp362.cisspmobiledesignclassportal";
    private Button loginButton;
    private EditText userIdTextView, passwordTextView;
    private TextView errorLogin;
    int tries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.loginButton);
        userIdTextView = (EditText) findViewById(R.id.userIdEditText);
        passwordTextView = (EditText) findViewById(R.id.passwordEditText);
        errorLogin = (TextView) findViewById(R.id.errorLoginTextView);

        userIdTextView.setText("");
        passwordTextView.setText("");
        errorLogin.setText("");
        tries = 0;

        passwordTextView.setOnEditorActionListener(this);
        loginButton.setOnClickListener(this);
    }

    public boolean checkPassword() {

        boolean status = false;

        // Increment number of attempts counter
        tries++;

        // Get valid user id and password from strings xml file
        String validUserId = getString(R.string.validUserName);
        String validPassword = getString(R.string.validPassword);

        // Validate if the user id and password are correct
        if (userIdTextView.getText().toString().compareToIgnoreCase(validUserId) == 0)
            if (passwordTextView.getText().toString().equals(validPassword)) {
                errorLogin.setText("");
                errorLogin.setTextColor(Color.MAGENTA);
                status = true;
                tries = 0;
            }
            // Invalid credentials, display error
            else {
                errorLogin.setText(getString(R.string.strError) + " " + tries + ")");
                errorLogin.setTextColor(Color.RED);
                status = false;
            }
        return status;
    }

    // Validate credentials and go to next screen if correct
    public void validate() {

        // Go to next screen if valid user id and password
        if (checkPassword()) {
            String message = userIdTextView.getText().toString();
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }

    // User pressed an action key on the password field, validate credentials
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        validate();
        return false;
    }

    // User clicked on the Login button, validate credentials
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.loginButton) {
            validate();
        }
    }
}
