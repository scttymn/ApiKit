package com.ovenbits.apikit;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.ovenbits.apikit.api.models.User;
import com.ovenbits.apikit.api.response.ErrorResponse;
import com.ovenbits.apikit.api.response.UserResponse;
import com.ovenbits.request.ErrorListener;
import com.ovenbits.request.Listener;

import java.util.Map;

public class MainActivity extends ActionBarActivity {
    EditText mEmail;
    EditText mPassword;
    TextView mResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mResponse = (TextView) findViewById(R.id.response);
        findViewById(R.id.button).setOnClickListener(buttonClicked);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();

            App.getApi().signIn(email, password,
                new Listener<UserResponse>(){
                    @Override
                    public void onResponse(UserResponse response, Map<String, String> headers) {
                        User u = response.getUser();
                        mResponse.setText(u.getFirstName());
                    }
                }, new ErrorListener<ErrorResponse>() {
                    @Override
                    public void onErrorResponse(ErrorResponse errorObject) {
                        String message = errorObject.getMessage();
                        mResponse.setText(message);
                    }
                });
        }
    };
}
