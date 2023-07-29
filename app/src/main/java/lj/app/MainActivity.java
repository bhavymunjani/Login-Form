package lj.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText email,password;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.main_login);
        email = findViewById(R.id.main_email);
        password = findViewById(R.id.main_password);
        getSupportActionBar().hide();

        login.setBackgroundColor(getResources().getColor(R.color.light_gray));

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().equals("") || password.getText().toString().trim().equals("")){
                    login.setClickable(false);
                    login.setBackgroundColor(getResources().getColor(R.color.light_gray));
                }
                else{
                    login.setClickable(true);
                    login.setBackground(getResources().getDrawable(R.drawable.custom_button));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().equals("") || email.getText().toString().trim().equals("")){
                        login.setClickable(false);
                        login.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    }
                    else{
                        login.setClickable(true);
                        login.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().trim().equals("")) {
                    email.setError("Email Id Required");
                } else if (!email.getText().toString().matches(emailPattern)) {
                    email.setError("Valid Email Id Required");
                } else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                } else
                {
                    //if (email.getText().toString().equals(emailPattern)) {

                        System.out.println("Login Successfully");
                        Log.d("RESPONSE", "Login Successfully");
                        Log.e("RESPONSE", "Login Successfully");
                        //Toast.makeText(MainActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                        new CommonMethod(MainActivity.this, "Login Successfully");
                        //Snackbar.make(view,"Login Successfully", Snackbar.LENGTH_SHORT).show();
                        new CommonMethod(view, "Login Successfully");
                        Intent intent=new Intent(MainActivity.this,homeActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("EMAIL",email.getText().toString());
                        bundle.putString("PASSWORD",password.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);

                      //  new CommonMethod(MainActivity.this, homeActivity.class);
                    //}
                  // }

                }
            }
        });

    }
}