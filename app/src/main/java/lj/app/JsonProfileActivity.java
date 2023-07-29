package lj.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import lj.app.GetData.GetSignupData;
import lj.app.utils.ApiClient;
import lj.app.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JsonProfileActivity extends AppCompatActivity {
    EditText name,email,password,dob,contact;
    RadioGroup gender;
    RadioButton male,female,transgender;
    Button edit,logout,submit;
    Spinner spinner;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ArrayList<String> cityArray;
    Calendar calendar;
    String Scity,Sgender;
    SharedPreferences sp;
    ApiInterface apiInterface;
    ProgressDialog pd;
    CircleImageView circleImageView;
    ImageView camara;
    int REQUEST_CODE_CHOOSE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_profile);
        name=findViewById(R.id.json_profile_name);
        email=findViewById(R.id.json_profile_email);
        password=findViewById(R.id.json_profile_password);
        contact=findViewById(R.id.json_profile_contact);
        dob=findViewById(R.id.json_profile_dob);
        edit=findViewById(R.id.json_profile_editprofile);
        spinner=findViewById(R.id.json_profile_city);
        gender=findViewById(R.id.json_profile_gender);
        logout=findViewById(R.id.json_profile_logout);
        submit=findViewById(R.id.json_profile_submit);
        male=findViewById(R.id.json_profile_male);
        female=findViewById(R.id.json_profile_female);
        circleImageView=findViewById(R.id.json_profile_image);
        camara=findViewById(R.id.json_profile_camara);
        transgender=findViewById(R.id.json_profile_transgender);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        sp=getSharedPreferences(ConstantURl.PREF,MODE_PRIVATE);

        name.setText(sp.getString(ConstantURl.NAME,""));
        email.setText(sp.getString(ConstantURl.EMAIL,""));
        contact.setText(sp.getString(ConstantURl.CONTACT,""));
        dob.setText(sp.getString(ConstantURl.DOB,""));
        setEnableData(false);
        edit.setVisibility(View.VISIBLE);
        submit.setVisibility(View.GONE);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(JsonProfileActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(5)
                        .gridExpectedSize(200)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .showPreview(false) // Default is `true`
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEnableData(true);
                edit.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();
                new CommonMethod(JsonProfileActivity.this,JsonLoginActivity.class);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().equalsIgnoreCase(""))
                {
                    name.setError("name is required");
                }
                else if(email.getText().toString().trim().equalsIgnoreCase(""))
                {
                    email.setError("email is required");
                }
                else if(!email.getText().toString().matches(emailPattern))
                {
                    email.setError("valid emailid required");
                }
                else if(contact.getText().toString().trim().equalsIgnoreCase(""))
                {
                    contact.setError("contact is required");
                }
                else if(contact.getText().toString().length()<10)
                {
                    contact.setError("valid contact number required");
                }
                else if(password.getText().toString().trim().equalsIgnoreCase(""))
                {
                    password.setError("password is required");
                }
                else if(dob.getText().toString().trim().equalsIgnoreCase(""))
                {
                    dob.setError("dob is required");
                }
                else if(gender.getCheckedRadioButtonId()==-1)
                {
                    new CommonMethod(JsonProfileActivity.this,"Please select gender");
                }
                else
                {
                    /*setEnableData(false);
                    edit.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);*/
                    //ASYNC TASK
                    /*if(new ConnectionDetector(JsonProfileActivity.this).isConnectingToInternet())
                    {
                        new UpdateData().execute();
                    }
                    else
                    {
                        new ConnectionDetector(JsonProfileActivity.this).connectiondetect();
                    }*/
                    if(new ConnectionDetector(JsonProfileActivity.this).isConnectingToInternet())
                    {
                        pd=new ProgressDialog(JsonProfileActivity.this);
                        pd.setMessage("Please Wait.....");
                        pd.setCancelable(false);
                        pd.show();
                        retrofitupdatedata();
                    }
                    else
                    {
                        new ConnectionDetector(JsonProfileActivity.this).connectiondetect();
                    }
                }

            }
        });
        Sgender=sp.getString(ConstantURl.GENDER,"");
        if(Sgender.equalsIgnoreCase("male"))
        {
            male.setChecked(true);
        }
        else if(Sgender.equalsIgnoreCase("male"))
        {
            female.setChecked(true);
        }
        else if (Sgender.equalsIgnoreCase("transgender"))
        {
            transgender.setChecked(true);
        }
        else
        {

        }
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=findViewById(i);
                Sgender=radioButton.getText().toString();
                new CommonMethod(JsonProfileActivity.this,Sgender);
            }
        });

        cityArray=new ArrayList<>();
        cityArray.add("Ahemdabad");
        cityArray.add("Surat");
        cityArray.add("Bhavnagar");
        cityArray.add("Vadodara");
        cityArray.add("Rajkot");
        cityArray.add("Mahesana");
        cityArray.add("Vapi");
        cityArray.add("anand");
        ArrayAdapter adapter=new ArrayAdapter(JsonProfileActivity.this, android.R.layout.simple_list_item_1,cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinner.setAdapter(adapter);
        Scity=sp.getString(ConstantURl.CITY,"");
        int position=0;
        for(int i=0;i<cityArray.size();i++)
        {
            if(cityArray.get(i).equalsIgnoreCase(Scity))
            {
                position=i;
                break;
            }
        }
        spinner.setSelection(position);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Scity=cityArray.get(i);
                new CommonMethod(JsonProfileActivity.this,Scity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateClick=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dob.setText(dateFormat.format(calendar.getTime()));
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dateDialog=new DatePickerDialog(JsonProfileActivity.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dateDialog.show();
            }
        });

    }

    private void retrofitupdatedata() {
        Call<GetSignupData> call=apiInterface.getupdatedata(
                sp.getString(ConstantURl.ID,""),
                name.getText().toString(),
                email.getText().toString(),
                contact.getText().toString(),
                Sgender,
                Scity,
                password.getText().toString(),
                dob.getText().toString()
                );
        call.enqueue(new Callback<GetSignupData>() {
            @Override
            public void onResponse(Call<GetSignupData> call, Response<GetSignupData> response) {
                pd.dismiss();
                if(response.code()==200)
                {
                    if(response.body().status==true)
                    {
                        new CommonMethod(JsonProfileActivity.this,response.body().message);

                        setEnableData(false);
                        edit.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.GONE);

                        sp.edit().putString(ConstantURl.NAME,name.getText().toString()).commit();
                        sp.edit().putString(ConstantURl.EMAIL,email.getText().toString()).commit();
                        sp.edit().putString(ConstantURl.CONTACT,contact.getText().toString()).commit();
                        sp.edit().putString(ConstantURl.DOB,dob.getText().toString()).commit();
                        sp.edit().putString(ConstantURl.GENDER,Sgender).commit();
                        sp.edit().putString(ConstantURl.CITY,Scity).commit();
                    }
                    else
                    {
                        new CommonMethod(JsonProfileActivity.this,response.body().message);
                    }
                }
                else
                {
                    new CommonMethod(JsonProfileActivity.this,"Error code"+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetSignupData> call, Throwable t) {
                pd.dismiss();
                Log.d("Response Error",t.getMessage());
            }
        });
    }

    private void setEnableData(boolean b) {
        contact.setEnabled(b);
        email.setEnabled(b);
        dob.setEnabled(b);
        name.setEnabled(b);

        male.setEnabled(b);
        female.setEnabled(b);
        transgender.setEnabled(b);

        spinner.setEnabled(b);
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private class UpdateData extends AsyncTask<String,String,String> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd=new ProgressDialog(JsonProfileActivity.this);
            pd.setMessage("Please Wait.....");
            pd.setCancelable(false);
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap <String,String>hashMap=new HashMap<>();
            hashMap.put("userid",sp.getString(ConstantURl.ID,""));
            hashMap.put("name",name.getText().toString());
            hashMap.put("email",email.getText().toString());
            hashMap.put("contact",contact.getText().toString());
            hashMap.put("gender",Sgender);
            hashMap.put("city",Scity);
            hashMap.put("password",password.getText().toString());
            hashMap.put("dob",dob.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantURl.UpdateProfile_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object=new JSONObject(s);
                if(object.getBoolean("Status")==true)
                {
                    new CommonMethod(JsonProfileActivity.this,object.getString("Message"));
                    setEnableData(false);
                    edit.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);
                    sp.edit().putString(ConstantURl.NAME,name.getText().toString()).commit();
                    sp.edit().putString(ConstantURl.EMAIL,email.getText().toString()).commit();
                    sp.edit().putString(ConstantURl.CONTACT,contact.getText().toString()).commit();
                    sp.edit().putString(ConstantURl.DOB,dob.getText().toString()).commit();
                    sp.edit().putString(ConstantURl.GENDER,Sgender).commit();
                    sp.edit().putString(ConstantURl.CITY,Scity).commit();
                }
                else
                {
                    new CommonMethod(JsonProfileActivity.this,object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
