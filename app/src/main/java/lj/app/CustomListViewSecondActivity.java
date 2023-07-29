package lj.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class CustomListViewSecondActivity extends AppCompatActivity {

    ListView listView;
    String [] TechnologyArray={"JAVA","ANDROID","IOS","FLUTTER"};
    String [] PriceArray={"60000","70000","80000","10000"};
    String [] Discription=
            {
                    "Java is a widely used object-oriented programming language and software platform that runs on billions of devices, including notebook computers, mobile devices, gaming consoles, medical devices and many others. The rules and syntax of Java are based on the C and C++ languages",
                    "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets.",
                    "Apple (AAPL) iOS is the operating system for iPhone, iPad, and other Apple mobile devices. Based on Mac OS, the operating system which runs Apple's line of Mac desktop and laptop computers, Apple iOS is designed for easy, seamless networking between a range of Apple products.",
                    "Flutter is a cross-platform UI toolkit that is designed to allow code reuse across operating systems such as iOS and Android, while also allowing applications to interface directly with underlying platform services."
            };
    int[] ImageArray={R.drawable.java,R.drawable.android,R.drawable.ios,R.drawable.flutter};
    ArrayList<CustomList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view_second);
        listView=findViewById(R.id.Custom_listview_second);

        arrayList=new ArrayList<>();
        for(int i=0;i< TechnologyArray.length;i++) {
            CustomList list = new CustomList(TechnologyArray[i], ImageArray[i],PriceArray[i],Discription[i]);
            arrayList.add(list);
            /*CustomList list=new CustomList();
            list.setName(TechnologyArray[i]);
            list.setImage(ImageArray[i]);
            arrayList.add(list);*/


        }
        CustomSecondAdapter adapter=new CustomSecondAdapter(CustomListViewSecondActivity.this,arrayList);
        listView.setAdapter(adapter);

    }
}