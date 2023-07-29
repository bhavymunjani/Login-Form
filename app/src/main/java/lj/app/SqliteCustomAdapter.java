package lj.app;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SqliteCustomAdapter extends RecyclerView.Adapter<SqliteCustomAdapter.MyHolder> {

    Context context;
    ArrayList<SqliteCustomList> arrayList;
    SQLiteDatabase sb;
    public SqliteCustomAdapter(Context context, ArrayList<SqliteCustomList> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
        sb=context.openOrCreateDatabase("DataBase",MODE_PRIVATE,null);
        String creattable="create table if not exists Record(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACTNO INT(10))";
        sb.execSQL(creattable);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview,parent,false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name,contact,email;
        ImageView delete,edit;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.custom_recyclerview_name);
            email=itemView.findViewById(R.id.custom_recyclerview_Email);
            contact=itemView.findViewById(R.id.custom_recyclerview_contact);
            delete=itemView.findViewById(R.id.recyclerview_delete);
            edit=itemView.findViewById(R.id.recyclerview_edit);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
    holder.name.setText(arrayList.get(position).getName());
    holder.email.setText(arrayList.get(position).getEmail());
    holder.contact.setText(arrayList.get(position).getContact());

   holder.delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String deleteQurey="delete from record WHERE CONTACTNO='"+arrayList.get(position)+"'";
            sb.execSQL(deleteQurey);
            new CommonMethod(context,"Student Record"+ arrayList.get(position)+"Deleted Succesfully");
            arrayList.remove(position);
            notifyDataSetChanged();

        }
    });
    holder.edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context,SqliteDatabaseActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("type","edit");
            bundle.putString("NAME",arrayList.get(position).getName());
            bundle.putString("EMAIL",arrayList.get(position).getEmail());
            bundle.putString("CONTACT",arrayList.get(position).getContact());
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
