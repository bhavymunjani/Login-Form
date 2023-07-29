package lj.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Date_TimeActivity extends AppCompatActivity {
    EditText dateEdit,timeEdit;
    Calendar calendar;
    int iHour,iMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);
        dateEdit=findViewById(R.id.date_select);
        timeEdit=findViewById(R.id.time_select);
        calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateClick=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            calendar.set(Calendar.YEAR,i);
            calendar.set(Calendar.MONTH,i1);
            calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dateEdit.setText(dateFormat.format(calendar.getTime()));
            }
        };

        TimePickerDialog.OnTimeSetListener timeClick=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                iHour = i;
                iMinute=i1;
                String sAMPM;
                if(iHour>12)
                {
                    iHour-=12;
                    sAMPM="PM";
                }
                else if(iHour==12)
                {
                    sAMPM="PM";
                }
                else if(iHour==0)
                {
                    iHour=12;
                    sAMPM="am";
                }
                else
                {
                    sAMPM="AM";
                }
                String sMinute;
                if(iMinute<10)
                {
                    sMinute="0"+iMinute;
                }
                else
                {
                    sMinute= String.valueOf(iMinute);
                }
                timeEdit.setText(iHour+":"+sMinute+" "+sAMPM);
            }
        };

        timeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(Date_TimeActivity.this,timeClick,iHour,iMinute,false).show();
            }
        });


        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dateDialog=new DatePickerDialog(Date_TimeActivity.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dateDialog.show();
            }
        });
    }
}