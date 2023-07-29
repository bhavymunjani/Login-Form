package lj.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SqliteDatabaseActivity extends AppCompatActivity {
EditText name,email,contact;
Button insert,show,CustomShow;
SQLiteDatabase sb;
String sType;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sb=openOrCreateDatabase("DataBase",MODE_PRIVATE,null);
        String creattable="create table if not exists Record(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACTNO INT(10))";
        sb.execSQL(creattable);
        setContentView(R.layout.activity_sqlite_database);
        name=findViewById(R.id.sqlite_name);
        email=findViewById(R.id.sqlite_email);
        contact=findViewById(R.id.sqlite_contact);
        insert=findViewById(R.id.sqlite_insert);
        show=findViewById(R.id.sqlite_show);
        CustomShow=findViewById(R.id.sqlite_Custom_show);
        Bundle bundle=getIntent().getExtras();
        sType=bundle.getString("type");
        if(sType.equalsIgnoreCase("Add"))
        {
            getSupportActionBar().setTitle("ADD");
            insert.setText("Insert");
            contact.setEnabled(true);
        }
        else

        {
            getSupportActionBar().setTitle("UPDATE");
            insert.setText("Update");
            name.setText(bundle.getString("NAME"));
            email.setText(bundle.getString("EMAIL"));
            contact.setText(bundle.getString("CONTACT"));

            contact.setEnabled(false);
        }
        CustomShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(SqliteDatabaseActivity.this,SqliteCustomListActivity.class);
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(SqliteDatabaseActivity.this,SqliteListviewActivity.class);
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().equalsIgnoreCase(""))
                {
                    name.setError("Name Required");
                }
                else if(email.getText().toString().trim().equalsIgnoreCase(""))
                {
                    email.setError("Email Required");
                }
                else if(!email.getText().toString().matches(emailPattern))
                {
                    email.setError("Valid Email is Required");
                }
                else if(contact.getText().toString().equalsIgnoreCase(""))
                {
                    contact.setError("Contact Number Required");
                }
                else if(contact.getText().toString().length()<10)
                {
                    contact.setError("Valid Contact nUmber is Required");
                }
                else
                {
                    if(sType.equalsIgnoreCase("Add")) {
                        String selectQuery = "select * from record WHERE EMAIL='" + email.getText().toString() + "'OR CONTACTNO='" + contact.getText().toString() + "' ";
                        Cursor cursor = sb.rawQuery(selectQuery, null);
                        if (cursor.getCount() > 0) {
                            new CommonMethod(SqliteDatabaseActivity.this, "EmailID/ContactNo is Already Exists");
                        } else {
                            String insertRecord = "insert into Record VALUES('" + name.getText().toString() + "','" + email.getText().toString() + "','" + contact.getText().toString() + "')";
                            sb.execSQL(insertRecord);
                            new CommonMethod(SqliteDatabaseActivity.this, "Insert SuccessFully");
                            clearData();
                        }
                    }
                    else
                    {
                        String UpdateRecord = "Update Record SET NAME='"+name.getText().toString()+"',EMAIL='"+email.getText().toString()+"' WHERE CONTACTNO='"+contact.getText().toString()+"'";
                        sb.execSQL(UpdateRecord);
                        new CommonMethod(SqliteDatabaseActivity.this, "Insert SuccessFully");
                        clearData();
                    }
                }
            }
        });
    }

    public void clearData() {
        name.setText("");
        email.setText("");
        contact.setText("");
        name.requestFocus();
        onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

