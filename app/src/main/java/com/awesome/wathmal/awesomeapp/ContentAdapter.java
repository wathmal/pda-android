package com.awesome.wathmal.awesomeapp;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by wathmal on 3/18/15.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

//    protected String caption[];
//    protected int imageid[];
    protected List<Event> events= Collections.emptyList();
    //private LayoutInflater inflater;

    public ContentAdapter(List<Event> events){

        this.events= events;
//        this.caption= caption;
//        this.imageid= imageid;
    //    inflater= LayoutInflater.from(context);
    }

    @Override
    public ContentAdapter.ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row_view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_row, parent, false);
        ContentAdapter.ContentViewHolder holder= new ContentViewHolder(row_view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {


        Event current= events.get(position);
        //get the first character of the title
        char c= current.get_title().charAt(0);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));


        TextDrawable drawable2 = TextDrawable.builder()
                .buildRound(String.valueOf(c).toUpperCase(), color);
        holder.image.setImageDrawable(drawable2);

        holder.caption.setText(current.get_title());
        holder.description.setText(current.get_description());
    }



    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView caption;
        TextView description;

        public ContentViewHolder(View itemView) {
            super(itemView);

            caption= (TextView) itemView.findViewById(R.id.contentCaption);
            image= (ImageView)itemView.findViewById(R.id.contentIcon);
            description= (TextView)itemView.findViewById(R.id.contentDescription);
        }
    }
}
