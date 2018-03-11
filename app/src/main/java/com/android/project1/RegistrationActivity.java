package com.android.project1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText edname, edemail, edpassword, edphone, edaddress;

    private int PICK_IMAGE_REQUEST = 1;

    private Button createAccount,uploadImage;

    private ImageView imageView;

    private Bitmap bitmap;


    private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edname = (EditText) findViewById(R.id.editName);
        edphone = (EditText) findViewById(R.id.editPhone);
        edpassword = (EditText) findViewById(R.id.editPassword);
        edemail = (EditText) findViewById(R.id.editEmail);
        edaddress = (EditText) findViewById(R.id.editAddress);

        createAccount = (Button) findViewById(R.id.create_account);




        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
            }
        });
    }





    private void addAccount() {
        final String name = edname.getText().toString().trim();
        final String address = edaddress.getText().toString().trim();
        final String phone = edphone.getText().toString().trim();
        final String email = edemail.getText().toString().trim();
        final String pass = edpassword.getText().toString().trim();

        if (!isValidName(name)) {
            edname.setError("Name is Required");
        }
        if (!isValidPass(pass)) {
            edpassword.setError("Minimum 6 characters");
        }
        if (!isValidEmail(email)) {
            edemail.setError("Invalid Email");
        }
        if (!isValidPhone(phone)) {
            edphone.setError("Phone is Required");
        }

        if (!isValidAddress(address)) {
            edaddress.setError("Address is Required");
        }

        if (isValidPass(pass) && isValidAddress(address) && isValidEmail(email) && isValidName(name) && isValidPhone(phone))
        {

            class AddAccount extends AsyncTask<Bitmap, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(RegistrationActivity.this, "Adding...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    Toast.makeText(RegistrationActivity.this,s, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));



                }

                @Override
                protected String doInBackground(Bitmap... p) {
                    Bitmap bitmap = p[0];

                    HashMap<String, String> params = new HashMap<>();
                    params.put(Config.KEY_ACC_NAME, name);
                    params.put(Config.KEY_ACC_PASS, pass);
                    params.put(Config.KEY_ACC_EMAIL, email);
                    params.put(Config.KEY_ACC_PHONE, phone);
                    params.put(Config.KEY_ACC_ADDRESS, address);

                    RequestHandler req = new RequestHandler();
                    String s = req.sendPostRequest(Config.URL_ADD, params);
                    return s;
                }
            }

            AddAccount add = new AddAccount();
            add.execute(bitmap);
        }


}

    private boolean isValidName(String name) {
        if (name.length()>0) {
            return true;
        }
        return false;
    }

    private boolean isValidPass(String pass) {
        if (pass.length() != 0 && pass.length() >= 6) {
            return true;
        }
        return false;
    }

    private boolean isValidPhone(String phone) {
        if (phone.length() != 0 && phone.length() <= 10) {
            return true;
        }
        return false;
    }

    private boolean isValidAddress(String address) {
        if (address.length() != 0) {
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
