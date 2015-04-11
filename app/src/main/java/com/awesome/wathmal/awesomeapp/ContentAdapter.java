package com.awesome.wathmal.awesomeapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by wathmal on 3/18/15.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

//    protected String caption[];
//    protected int imageid[];

    //private LayoutInflater inflater;
    protected List<?> events= Collections.emptyList();
    String eventType;
    Context context;
    public ContentAdapter(Context context, List<?> events, String eventType){
        this.context= context;
        this.events= events;
        this.eventType= eventType;
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

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        if(this.eventType.equals(DatabaseHandler.TABLE_EVENT)) {
            Event current = (Event) events.get(position);
            //get the first character of the title
            char c;
            try{
                c= current.get_title().charAt(0);
            }
            catch (StringIndexOutOfBoundsException e){
                c= '-';
                Log.e("error", e.getMessage());
            }


            TextDrawable drawable2 = TextDrawable.builder()
                    .buildRound(String.valueOf(c).toUpperCase(), color);
            holder.image.setImageDrawable(drawable2);

            holder.caption.setText(current.get_title().toUpperCase());
            holder.description.setText(current.get_description());

            DateFormat df = new SimpleDateFormat("yy-MM-dd hh:mm", Locale.ENGLISH);
            holder.type.setText(df.format(current.get_date()));

        }
        else if(this.eventType.equals(DatabaseHandler.TABLE_BOOK)){
            Book current = (Book)events.get(position);
            char c = current.getTitle().charAt(0);




            TextDrawable drawable2 = TextDrawable.builder()
                    .buildRound(String.valueOf(c).toUpperCase(), color);
            holder.image.setImageDrawable(drawable2);
            holder.caption.setText(current.getTitle().toUpperCase());
            holder.description.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressBar.setMax(current.getPages());
            holder.progressBar.setProgress(current.getCurrentPage());
            holder.type.setText(String.format("%03d", current.getPages())+" pages");

        }

        else if(this.eventType.equals(DatabaseHandler.TABLE_MEDICINE)){
            Medicine current = (Medicine)events.get(position);
            char c= current.getName().charAt(0);
            TextDrawable drawable2 = TextDrawable.builder()
                    .buildRound(String.valueOf(c).toUpperCase(), color);

            holder.image.setImageDrawable(drawable2);
            holder.caption.setText(current.getName().toUpperCase());
            holder.description.setText(current.getDosage().toLowerCase());
            holder.type.setVisibility(View.GONE);
        }

        else if(this.eventType.equals(DatabaseHandler.TABLE_MOVIE)){
            Movie current= (Movie)events.get(position);
            char c = current.getTitle().charAt(0);
            TextDrawable drawable2 = TextDrawable.builder()
                    .buildRound(String.valueOf(c).toUpperCase(), color);

            holder.image.setImageDrawable(drawable2);
            holder.caption.setText(current.getTitle().toUpperCase());
            holder.description.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressBar.setMax(current.getDuration());
            holder.progressBar.setProgress(current.getCurrentTime());
            holder.type.setText(String.format("%03d", current.getDuration())+" min");

        }

        else if(this.eventType.equals(DatabaseHandler.TABLE_AUDIO_BOOK)){
            AudioBook current= (AudioBook)events.get(position);
            char c = current.getTitle().charAt(0);
            TextDrawable drawable2 = TextDrawable.builder()
                    .buildRound(String.valueOf(c).toUpperCase(), color);

            holder.image.setImageDrawable(drawable2);
            holder.caption.setText(current.getTitle().toUpperCase());
            holder.description.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressBar.setMax(current.getDuration());
            holder.progressBar.setProgress(current.getCurrentTime());
            holder.type.setText(String.format("%03d", current.getDuration())+" min");

        }
    }



    @Override
    public int getItemCount() {
        return events.size();
    }

    public void delete(int position){
        this.events.remove(position);
        notifyItemRemoved(position);
    }


    public class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        ImageView image;
        TextView caption;
        TextView description;
        ProgressBar progressBar;
        TextView type;
        public ContentViewHolder(View itemView) {
            super(itemView);

            caption= (TextView) itemView.findViewById(R.id.contentCaption);
            image= (ImageView)itemView.findViewById(R.id.contentIcon);
            description= (TextView)itemView.findViewById(R.id.contentDescription);
            progressBar= (ProgressBar)itemView.findViewById(R.id.progressBar);
            type= (TextView)itemView.findViewById(R.id.textViewType);

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "viewing not implemented yet!", Toast.LENGTH_SHORT).show();
                    ((MainActivity)context).showViewingDialog(eventType, getPosition());

                }
            });

        }


        @Override
        public boolean onLongClick(View v) {
            /*
            * relationship with getPosition() and item id must be used.
            * which means if you take allItems from database, in same soring order which is showing,
            * items.get(getPosition()) returns the clicked element!
            * */
            ((MainActivity)context).setContextMenu(getPosition(), eventType);
            return true;
        }
    }
}
