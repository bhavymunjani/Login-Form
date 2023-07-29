package lj.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import lj.app.GetData.GetSignupData;
import lj.app.utils.ApiClient;
import lj.app.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Json_Form_Activity extends AppCompatActivity {
    EditText name,email,password,dob,contact;
    RadioGroup gender;
    Button submit;
    Spinner spinner;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ArrayList<String> cityArray;
    Calendar calendar;
    String Scity,Sgender;
    ApiInterface apiInterface;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_form);
        name=findViewById(R.id.json_login_name);
        email=findViewById(R.id.json_login_email);
        password=findViewById(R.id.json_login_password);
        contact=findViewById(R.id.json_login_contact);
        dob=findViewById(R.id.json_login_dob);
        submit=findViewById(R.id.json_login_submit);
        spinner=findViewById(R.id.json_login_city);
        gender=findViewById(R.id.json_login_gender);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);

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
                    new CommonMethod(Json_Form_Activity.this,"Please select gender");
                }
                else
                {
                    //ASYNC TASK
                    /*if(new ConnectionDetector(Json_Form_Activity.this).isConnectingToInternet())
                    {
                        new dosingup().execute();
                        //new CommonMethod(Json_Form_Activity.this,"Internet Connect Successfull");
                    }
                    else
                    {
                        new ConnectionDetector(Json_Form_Activity.this).connectiondetect();
                    }*/
                    if(new ConnectionDetector(Json_Form_Activity.this).isConnectingToInternet())
                    {
                        //new dosingup().execute();
                        //new CommonMethod(Json_Form_Activity.this,"Internet Connect Successfull");
                        pd=new ProgressDialog(Json_Form_Activity.this);
                        pd.setMessage("Please wait.....");
                        pd.setCancelable(false);
                        pd.show();
                        retrofitsignup();
                    }
                    else
                    {
                        new ConnectionDetector(Json_Form_Activity.this).connectiondetect();
                    }

                }

            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=findViewById(i);
                Sgender=radioButton.getText().toString();
                new CommonMethod(Json_Form_Activity.this,Sgender);
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
        ArrayAdapter adapter=new ArrayAdapter(Json_Form_Activity.this, android.R.layout.simple_list_item_1,cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Scity=cityArray.get(i);
                new CommonMethod(Json_Form_Activity.this,Scity);
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
                DatePickerDialog dateDialog=new DatePickerDialog(Json_Form_Activity.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dateDialog.show();
            }
        });

    }

    private class dosingup extends AsyncTask<String,String,String> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(Json_Form_Activity.this);
            pd.setMessage("please wait.....");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("name",name.getText().toString());
            hashMap.put("email",email.getText().toString());
            hashMap.put("contact",contact.getText().toString());
            hashMap.put("gender",Sgender);
            hashMap.put("city",Scity);
            hashMap.put("password",password.getText().toString());
            hashMap.put("dob",dob.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantURl.Singup_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object=new JSONObject(s);
                if(object.getBoolean("Status")==true)
                {
                    new CommonMethod(Json_Form_Activity.this,object.getString("Message"));
                }
                else
                {
                    new CommonMethod(Json_Form_Activity.this,object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                new CommonMethod(Json_Form_Activity.this,e.getMessage());
            }
        }
    }

    private void retrofitsignup() {
        Call<GetSignupData> call=apiInterface.getsignupdata(
                name.getText().toString(),
                email.getText().toString(),
                contact.getText().toString(),
                Sgender,
                Scity,
                password.getText().toString(),
                dob.getText().toString());

        call.enqueue(new Callback<GetSignupData>() {
            @Override
            public void onResponse(Call<GetSignupData> call, Response<GetSignupData> response) {
                pd.dismiss();
                if(response.code()==200)
                {
                    if(response.body().status==true)
                    {
                        new CommonMethod(Json_Form_Activity.this,response.body().message);
                        new CommonMethod(Json_Form_Activity.this,JsonLoginActivity.class);
                    }
                    else
                    {
                        new CommonMethod(Json_Form_Activity.this,response.body().message);
                    }
                }
                else
                {
                    new CommonMethod(Json_Form_Activity.this,"Server Error Code:"+response.code());
                }

            }

            @Override
            public void onFailure(Call<GetSignupData> call, Throwable t) {
                pd.dismiss();
                Log.d("Responce Error",t.getMessage());
            }
        });
    }
}