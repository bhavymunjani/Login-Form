package lj.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import lj.app.GetData.GetLoginData;
import lj.app.utils.ApiClient;
import lj.app.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JsonLoginActivity extends AppCompatActivity {
    EditText email,password;
    TextView create;
    Button submit;
    String Scity,Sgender;
    SharedPreferences sp;
    ProgressDialog pd;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_login);
        submit=findViewById(R.id.json_login1_submit);
        email=findViewById(R.id.json_login1_email);
        password=findViewById(R.id.json_login1_password);
        create=findViewById(R.id.json_login1_create);
        sp=getSharedPreferences(ConstantURl.PREF,MODE_PRIVATE);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(JsonLoginActivity.this,Json_Form_Activity.class);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equalsIgnoreCase(""))
                {
                    email.setError(" Email/Contect no. Required");
                }
                else if (password.getText().toString().equalsIgnoreCase(""))
                {
                    password.setError("PassWord Required");
                }
                else
                {
                    //ASYNC TASK
                    /*if(new ConnectionDetector(JsonLoginActivity.this).isConnectingToInternet())
                    {
                        new dosingup().execute();
                    }
                    else
                    {
                        new ConnectionDetector(JsonLoginActivity.this).connectiondetect();
                    }*/
                    if(new ConnectionDetector(JsonLoginActivity.this).isConnectingToInternet())
                    {
                        pd=new ProgressDialog(JsonLoginActivity.this);
                        pd.setMessage("please wait.....");
                        pd.setCancelable(false);
                        pd.show();
                        retrofitlogin();
                    }
                    else
                    {
                        new ConnectionDetector(JsonLoginActivity.this).connectiondetect();
                    }
                }

            }
        });
    }

    private void retrofitlogin() {
        Call<GetLoginData> call=apiInterface.getlogindata(email.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<GetLoginData>() {
            @Override
            public void onResponse(Call<GetLoginData> call, Response<GetLoginData> response) {
                pd.dismiss();
                if(response.code()==200)
                {
                    GetLoginData data=response.body();
                    if(response.body().status==true)
                    {
                        for(int i=0;i<data.userdata.size();i++)
                        {

                            String Sid=data.userdata.get(i).id;
                            String Sname=data.userdata.get(i).name;
                            String Semail=data.userdata.get(i).email;
                            String Scontact=data.userdata.get(i).contact;
                            String Sgender=data.userdata.get(i).gender;
                            String Scity=data.userdata.get(i).city;
                            String Sdob=data.userdata.get(i).dob;
                            String Stype=data.userdata.get(i).type;


                            sp.edit().putString(ConstantURl.ID,Sid).commit();
                            sp.edit().putString(ConstantURl.NAME,Sname).commit();
                            sp.edit().putString(ConstantURl.EMAIL,Semail).commit();
                            sp.edit().putString(ConstantURl.CONTACT,Scontact).commit();
                            sp.edit().putString(ConstantURl.GENDER,Sgender).commit();
                            sp.edit().putString(ConstantURl.CITY,Scity).commit();
                            sp.edit().putString(ConstantURl.DOB,Sdob).commit();
                            sp.edit().putString(ConstantURl.TYPE,Stype).commit();
                        }
                        new CommonMethod(JsonLoginActivity.this,data.message);
                        new CommonMethod(JsonLoginActivity.this,JsonProfileActivity.class);
                        
                    }
                    else
                    {
                        new CommonMethod(JsonLoginActivity.this,response.body().message);
                    }
                }
                else
                {
                    new CommonMethod(JsonLoginActivity.this,"Server Error code"+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetLoginData> call, Throwable t) {
                pd.dismiss();
                Log.d("Responce Error",t.getMessage());

            }
        });
    }

    private class dosingup extends AsyncTask<String,String,String> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {

            pd=new ProgressDialog(JsonLoginActivity.this);
            pd.setMessage("Please wait.......");
            pd.setCancelable(false);
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String>hashMap=new HashMap<>();
            hashMap.put("email",email.getText().toString());
            hashMap.put("password",password.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantURl.Login_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            try {
                JSONObject object=new JSONObject(s);
                if(object.getBoolean("Status")==true)
                {
                    JSONArray array=object.getJSONArray("Userdata");
                    for(int i=0;i< array.length();i++)
                    {
                        JSONObject jsonObject=array.getJSONObject(i);
                        String Sid=jsonObject.getString("id");
                        String Sname=jsonObject.getString("name");
                        String Semail=jsonObject.getString("email");
                        String Scontact=jsonObject.getString("contact");
                        String Sgender=jsonObject.getString("gender");
                        String Scity=jsonObject.getString("city");
                        String Sdob=jsonObject.getString("dob");
                        String Stype=jsonObject.getString("type");

                        sp.edit().putString(ConstantURl.ID,Sid).commit();
                        sp.edit().putString(ConstantURl.NAME,Sname).commit();
                        sp.edit().putString(ConstantURl.EMAIL,Semail).commit();
                        sp.edit().putString(ConstantURl.CONTACT,Scontact).commit();
                        sp.edit().putString(ConstantURl.GENDER,Sgender).commit();
                        sp.edit().putString(ConstantURl.CITY,Scity).commit();
                        sp.edit().putString(ConstantURl.DOB,Sdob).commit();
                        sp.edit().putString(ConstantURl.TYPE,Stype).commit();
                    }
                    new CommonMethod(JsonLoginActivity.this,object.getString("Message"));
                    new CommonMethod(JsonLoginActivity.this,JsonProfileActivity.class);
                }
                else
                {
                    new CommonMethod(JsonLoginActivity.this,object.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                new CommonMethod(JsonLoginActivity.this,e.getMessage());
            }
            super.onPostExecute(s);

        }
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}