package lj.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AddListActivity extends AppCompatActivity {
    Button button;
    EditText editText;
    ListView listView;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        button=findViewById(R.id.addlist_send);
        editText=findViewById(R.id.addlist_edittext);
        listView=findViewById(R.id.addlist_listview);

        arrayList=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter(AddListActivity.this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().trim().equalsIgnoreCase(""))
                {
                    editText.setError("enter the the name");
                }
                else
                {
                    arrayList.add(editText.getText().toString());
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        new CommonMethod(AddListActivity.this,arrayList.get(i));
                    }
                });
            }
        });


    }
}