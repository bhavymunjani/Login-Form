package lj.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SqliteCustomListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SQLiteDatabase sb;
    ArrayList<SqliteCustomList> arrayList;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_custom_list);
        getSupportActionBar().setTitle("DataBase List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sb=openOrCreateDatabase("DataBase",MODE_PRIVATE,null);
        String creattable="create table if not exists record(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACTNO INT(10))";
        sb.execSQL(creattable);
        recyclerView=findViewById(R.id.Recyclerview);
        add=findViewById(R.id.custom_list_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SqliteCustomListActivity.this,SqliteDatabaseActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("type","Add");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(SqliteCustomListActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        String selectquery="select * from record";
        Cursor cursor =sb.rawQuery(selectquery,null);
        arrayList=new ArrayList<>();

        if(cursor.getCount()>=0)
        {
            if(cursor.moveToFirst())
            {
                while (cursor.moveToNext())
                {
                    SqliteCustomList list=new SqliteCustomList();
                    list.setName(cursor.getString(0));
                    list.setEmail(cursor.getString(1));
                    list.setContact(cursor.getString(2));
                    arrayList.add(list);

                }
                SqliteCustomAdapter adapter=new SqliteCustomAdapter(SqliteCustomListActivity.this,arrayList);
                recyclerView.setAdapter(adapter);

            }
        }
        else
        {
            new CommonMethod(SqliteCustomListActivity.this,"Data is not found");
        }
    }
}