package lj.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class CustomListViewActivity extends AppCompatActivity {
    ListView listView;
    String [] TechnologyArray={"JAVA","ANDROID","IOS","FLUTTER"};
    int[] ImageArray={R.drawable.background2,R.drawable.calender1,R.drawable.clock,R.drawable.background2,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view);
        listView =findViewById(R.id.custom_listview);
        CustomListAdapter adapter=new CustomListAdapter(CustomListViewActivity.this,TechnologyArray,ImageArray);
        listView.setAdapter(adapter);
    }
}