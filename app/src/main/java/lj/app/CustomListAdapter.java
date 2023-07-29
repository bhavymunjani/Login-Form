package lj.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
    Context context;
    String[] TechnologyArray;
    int[] ImageArray;

    public CustomListAdapter(CustomListViewActivity customListViewActivityActivity, String[] technologyArray, int[] imageArray)
    {
        context=customListViewActivityActivity;
        this.TechnologyArray=technologyArray;
        this.ImageArray=imageArray;
    }

    @Override
    public int getCount() {
        return TechnologyArray.length;
    }

    @Override
    public Object getItem(int i) {
        return TechnologyArray[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_list,null);

        ImageView imageView=view.findViewById(R.id.custom_list_image);
        TextView textView=view.findViewById(R.id.custom_list_name);
        textView.setText(TechnologyArray[i]);
        imageView.setImageResource(ImageArray[i]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(context,TechnologyArray[i]+"image click");
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(context,TechnologyArray[i]);
            }
        });

        return view;
    }
}
