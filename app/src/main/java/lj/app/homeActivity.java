package lj.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class homeActivity extends AppCompatActivity {
    TextView email,password;
    RadioGroup gender;
    CheckBox androidcheck,ios,java,flutter;
    Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       getSupportActionBar().setTitle("home");
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       email=findViewById(R.id.home_email);
       password=findViewById(R.id.home_password);

       Bundle bundle=getIntent().getExtras();
       email.setText(bundle.getString("EMAIL"));
       password.setText(bundle.getString("PASSWORD"));
        gender=findViewById(R.id.home_gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=findViewById(i);
                new CommonMethod(homeActivity.this,radioButton.getText().toString());
            }
        });
        androidcheck=findViewById(R.id.home_android);
        ios=findViewById(R.id.home_ios);
        java=findViewById(R.id.home_java);
        flutter=findViewById(R.id.home_flutter);
        show=findViewById(R.id.home_show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb=new StringBuilder();
                if(androidcheck.isChecked())
                {
                    sb.append(androidcheck.getText().toString()+"\n");
                }
                if(ios.isChecked())
                {
                    sb.append(ios.getText().toString()+"\n");
                }
                if(java.isChecked())
                {
                    sb.append(java.getText().toString()+"\n");
                }
                if(flutter.isChecked())
                {
                    sb.append(flutter.getText().toString()+"\n");
                }
                new CommonMethod(homeActivity.this,sb.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        alertMethod();

    }
    private void alertMethod()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(homeActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Exit Alert");
        builder.setMessage("Are You Sure Want To Exit");


        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });

        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNeutralButton("rate us", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new CommonMethod(homeActivity.this,"rate us");
                dialogInterface.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }
}



