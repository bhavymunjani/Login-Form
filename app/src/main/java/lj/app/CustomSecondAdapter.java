package lj.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomSecondAdapter extends BaseAdapter {
    Context context;
    ArrayList<CustomList> arrayList;
    public CustomSecondAdapter(CustomListViewSecondActivity customListViewSecondActivity, ArrayList<CustomList> arrayList) {
        this.context=customListViewSecondActivity;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_list,null);

        ImageView imageView=view.findViewById(R.id.custom_list_image);
        TextView textView=view.findViewById(R.id.custom_list_name);
        TextView priceView=view.findViewById(R.id.custom_list_price);
        textView.setText(arrayList.get(i).getName());
        priceView.setText(context.getResources().getString(R.string.Price_Symbol) +arrayList.get(i).getPrice());
        imageView.setImageResource(arrayList.get(i).getImage());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(context,arrayList.get(i).getName()+"image click");
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("NAME",arrayList.get(i).name);
                bundle.putString("PRICE",arrayList.get(i).price);
                bundle.putString("DISCRIPTION",arrayList.get(i).discription);
                bundle.putInt("IMAGE",arrayList.get(i).image);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


        return view;
    }
}
