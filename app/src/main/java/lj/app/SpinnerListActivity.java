package lj.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SpinnerListActivity extends AppCompatActivity {
    Spinner spinner;
  //  String[] cityArray ={"Ahemdabad","surat","vadodara","vapi","Bhavnagar","Rajkot","Ahemdabad","surat","vadodara","vapi","Bhavnagar","Rajkot"};
    GridView gridView;
    AutoCompleteTextView autoCompleteTextView;
    MultiAutoCompleteTextView multiAutoCompleteTextView;
    ArrayList<String>cityArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_list);
        spinner=findViewById(R.id.spinner);
        cityArray=new ArrayList<>();
        cityArray.add("Ahemdabad");
        cityArray.add("Surat");
        cityArray.add("Bhavnagar");
        cityArray.add("Vadodara");
        cityArray.add("Rajkot");
        cityArray.add("Mahesana");
        cityArray.add("Vapi");
        cityArray.add("snand");
        cityArray.add("Test");

        cityArray.remove(8);
        cityArray.set(7,"Anand");
        ArrayAdapter adapter=new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new CommonMethod(SpinnerListActivity.this,cityArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        gridView=findViewById(R.id.grid_view);
        ArrayAdapter Listadapter=new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,cityArray);
        gridView.setAdapter(Listadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new CommonMethod(SpinnerListActivity.this,cityArray.get(i));
            }
        });
        autoCompleteTextView=findViewById(R.id.spinner_autocompletetext);
        ArrayAdapter autoadapter=new ArrayAdapter(SpinnerListActivity.this,android.R.layout.simple_list_item_1,cityArray);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(autoadapter);

        multiAutoCompleteTextView=findViewById(R.id.spinner_multiautocompletetext);
        ArrayAdapter multiadapter=new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,cityArray);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setAdapter(multiadapter);


    }
}