package lj.app;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SqliteListviewActivity extends AppCompatActivity {
ListView listView;
SQLiteDatabase sb;
ArrayList<String> arrayList;
ArrayList<String> contactArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_listview);
        sb=openOrCreateDatabase("DataBase",MODE_PRIVATE,null);
        String creattable="create table if not exists record(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACTNO INT(10))";
        sb.execSQL(creattable);
        listView=findViewById(R.id.sqlite_listview);
        String selectquery="select * from record";
        Cursor cursor =sb.rawQuery(selectquery,null);
        if(cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                arrayList=new ArrayList<>();
                contactArrayList = new ArrayList<>();
                while (cursor.moveToNext())
                {
                    String sStudentname="Student Name: "+cursor.getString(0);
                    String sStudentemail="Student Email: "+cursor.getString(1);
                    String sStudentcontactNo="Student Number: "+cursor.getString(2);
                    arrayList.add(sStudentname+"\n"+sStudentemail+"\n"+sStudentcontactNo);
                    contactArrayList.add(cursor.getString(2));

                }
                ArrayAdapter adapter=new ArrayAdapter(SqliteListviewActivity.this, android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String deleteQurey="delete from record WHERE CONTACTNO='"+contactArrayList.get(i)+"'";
                        sb.execSQL(deleteQurey);
                        new CommonMethod(SqliteListviewActivity.this,"Student Record"+ contactArrayList.get(i)+"Deleted Succesfully");
                        arrayList.remove(i);
                        contactArrayList.remove(i);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });

            }
            else
            {
                new CommonMethod(SqliteListviewActivity.this,"Data is not found");
            }

        }




    }
}
