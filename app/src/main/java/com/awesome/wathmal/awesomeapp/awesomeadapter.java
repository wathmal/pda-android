package com.awesome.wathmal.awesomeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by wathmal on 3/4/15.
 */
public class awesomeadapter extends RecyclerView.Adapter<awesomeadapter.myviewholder> {

    List<information> data= Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public awesomeadapter(Context context, List<information> data){

        // init data list
        this.data= data;
        this.context= context;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public myviewholder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view= inflater.inflate(R.layout.custom_row, viewGroup, false);
        myviewholder holder= new myviewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myviewholder viewHolder, int i) {

        Log.d("test","on bind viwe holder called");
        information current= data.get(i);
        viewHolder.title.setText(current.title);
        viewHolder.icon.setImageResource(current.iconid);
        viewHolder.notify.setText(current.notify);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;
        TextView notify;

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "item clicked at "+ getPosition(),Toast.LENGTH_SHORT).show();
        }



        public myviewholder(View itemView) {

            super(itemView);

            title= (TextView)itemView.findViewById(R.id.listtext);
            icon= (ImageView)itemView.findViewById(R.id.listicon);
            notify= (TextView)itemView.findViewById(R.id.itemnotify);

            itemView.setOnClickListener(this);

        }
    }
}
