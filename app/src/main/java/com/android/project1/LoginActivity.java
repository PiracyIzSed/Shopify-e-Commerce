package com.android.project1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;



import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.payu.magicretry.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity  {


    private EditText email,pass;
    private TextView register;
    private Button login;
    private static final String TAG = "LoginActivity";
    public boolean loginStatus = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {


        checkLogin();

            super.onCreate(savedInstanceState);
        if (!loginStatus) {
            setContentView(R.layout.activity_login);

            email = (EditText) findViewById(R.id.email);
            pass = (EditText) findViewById(R.id.password);

            register = (TextView) findViewById(R.id.register);


            login = (Button) findViewById(R.id.email_sign_in_button);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDetails();


                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                }
            });
        }
        else
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
    }

    public void checkLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
        String Name = sharedPreferences.getString(Config.KEY_ACC_NAME,null);
        if(Name!=null)
            loginStatus = true;
        else
            loginStatus=false;

    }






    private void getDetails() {

        final String password = pass.getText().toString().trim();
        if (!isValidPass(password)) {
            pass.setError("Password Required");
        }

        final String emailid = email.getText().toString().trim();

        if (!isValidEmail(emailid)) {
            email.setError("Email Id Required");
        } else {

            class GetAccount extends AsyncTask<Void, Void, String> {

                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(LoginActivity.this, "Loggin In ...", "Please Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    String json = s;
                    try {
                        JSONObject obj = new JSONObject(json);
                        JSONArray result = obj.getJSONArray(Config.TAG_JSON_ARRAY);
                        JSONObject c = result.getJSONObject(0);
                        final String id = c.getString(Config.TAG_ID);
                        final String name = c.getString(Config.TAG_NAME);
                        final String email = c.getString(Config.TAG_EMAIL);
                        final String phone = c.getString(Config.TAG_PHONE);
                        final String address = c.getString(Config.TAG_ADDRESS);
                        final String profilePicture=c.getString(Config.TAG_IMAGE);

                        if(name!="null")
                        {
                            Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                                    SharedPreferences mySharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = mySharedPreferences.edit();

                                    editor.putString(Config.KEY_ACC_ID,id);
                                    editor.putString(Config.KEY_ACC_NAME,name);
                                    editor.putString(Config.KEY_ACC_EMAIL,email);
                                    editor.putString(Config.KEY_ACC_PHONE,phone);
                                    editor.putString(Config.KEY_ACC_ADDRESS,address);
                                    editor.putString(Config.KEY_ACC_IMAGE,profilePicture);
                                    editor.commit();
                                    startActivity(i);
                                    finish();
                                }
                            }, 2000);

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Username or Password Invalid",Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected String doInBackground(Void... v) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put(Config.KEY_ACC_PASS, password);
                    params.put(Config.KEY_ACC_EMAIL, emailid);

                    RequestHandler rh = new RequestHandler();
                    String s = rh.sendPostRequest(Config.URL_CHECK, params);
                    return s;
                }
            }
            GetAccount ge = new GetAccount();
            ge.execute();
        }
    }

    private boolean isValidPass(String pass) {
        if (pass.length() != 0 ) {
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email){
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}

