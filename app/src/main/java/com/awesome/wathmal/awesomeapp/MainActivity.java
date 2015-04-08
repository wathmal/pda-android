package com.awesome.wathmal.awesomeapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private DatabaseHandler dh;
    private String adapterEventType;
    private Context context;
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setContext(context);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        this.dh= new DatabaseHandler(this);
        this.context= this;

        List<Event> allEvents= dh.getAllEvents();
        this.adapterEventType= DatabaseHandler.TABLE_EVENT;

        mRecyclerView= (RecyclerView)findViewById(R.id.my_recycler_view);
        // Attach recycler view to the floating action button
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.action_addEvent);
        FloatingActionButton fabMedicineButton= (FloatingActionButton)findViewById(R.id.action_medicine);

        FloatingActionButton fabMovieButton= (FloatingActionButton)findViewById(R.id.action_movie);
        FloatingActionButton fabAudioBookButton= (FloatingActionButton)findViewById(R.id.action_audio_book);
        FloatingActionButton fabBookButton= (FloatingActionButton)findViewById(R.id.action_book);

//        com.melnykov.fab.FloatingActionButton fab1= (com.melnykov.fab.FloatingActionButton) findViewById(R.id.action_addEvent);
//        fab1.attachToRecyclerView(mRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEventIntent= new Intent(MainActivity.this, AddEventActivity.class);
                MainActivity.this.startActivity(addEventIntent);
            }
        });
        fabMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMedicineIntent= new Intent(MainActivity.this, AddEventActivity.class);
                addMedicineIntent.putExtra("eventType", 2);

                MainActivity.this.startActivity(addMedicineIntent);

            }
        });
        fabMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, AddEventActivity.class);
                intent.putExtra("eventType", 3);

                MainActivity.this.startActivity(intent);

            }
        });
        fabAudioBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, AddEventActivity.class);
                intent.putExtra("eventType", 4);

                MainActivity.this.startActivity(intent);

            }
        });
        fabBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, AddEventActivity.class);
                intent.putExtra("eventType", 1);

                MainActivity.this.startActivity(intent);

            }
        });



//        String a[]= new String[allEvents.size()];
//        int i=0;
//
//        for(Event e: allEvents){
//            a[i++]= e.get_title();
//            Log.d("sqlite", "reading event "+ e.get_title());
//        }

        //String a[]= {"hello wathmal this is awesome", "i am awesome","කාලේ මටයි පින පෑදිච්චී", "සිංහල වැඩනේ"};
//        int b[]= {};
        ContentAdapter contentAdapter= new ContentAdapter(context, allEvents, this.adapterEventType);
        mRecyclerView.setAdapter(contentAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        /*
        swipeRefresh listener
        reload the Events from db and set the Adapter
         */
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                /*if else block to refresh particular event type*/
                if(adapterEventType.equals(DatabaseHandler.TABLE_EVENT)){
                    List<Event> allEvents= dh.getAllEvents();
                    ContentAdapter contentAdapter= new ContentAdapter(context, allEvents, adapterEventType);
                    mRecyclerView.setAdapter(contentAdapter);
                }

                else if(adapterEventType.equals(DatabaseHandler.TABLE_BOOK)){
                    List<Book> allBooks= dh.getAllBooks();
                    ContentAdapter contentAdapter= new ContentAdapter(context, allBooks, adapterEventType);
                    mRecyclerView.setAdapter(contentAdapter);
                }

                else if(adapterEventType.equals(DatabaseHandler.TABLE_MEDICINE)){

                    List<Medicine> allMedicines= dh.getAllMedicines();
                    ContentAdapter contentAdapter= new ContentAdapter(context, allMedicines, adapterEventType);
                    mRecyclerView.setAdapter(contentAdapter);
                }

                else if(adapterEventType.equals(DatabaseHandler.TABLE_MOVIE)){

                    List<Movie> allMovies= dh.getAllMovies();
                    ContentAdapter contentAdapter= new ContentAdapter(context, allMovies, adapterEventType);
                    mRecyclerView.setAdapter(contentAdapter);
                }

                else if(adapterEventType.equals(DatabaseHandler.TABLE_AUDIO_BOOK)){

                    List<AudioBook> allAudioBooks= dh.getAllAudioBooks();
                    ContentAdapter contentAdapter= new ContentAdapter(context, allAudioBooks, adapterEventType);
                    mRecyclerView.setAdapter(contentAdapter);
                }

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void refreshAdapter(int adapterPosition){
        //this.adapterEventType= adapterEventType;
        if(adapterPosition == 1){
            this.adapterEventType= DatabaseHandler.TABLE_EVENT;

            List<Event> allEvents= dh.getAllEvents();
            ContentAdapter contentAdapter= new ContentAdapter(this, allEvents, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        }

        else if(adapterPosition == 2){
            this.adapterEventType= DatabaseHandler.TABLE_BOOK;

            List<Book> allBooks= dh.getAllBooks();
            ContentAdapter contentAdapter= new ContentAdapter(context, allBooks, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        }

        else if(adapterPosition == 3){
            this.adapterEventType= DatabaseHandler.TABLE_MEDICINE;

            List<Medicine> allMedicines= dh.getAllMedicines();
            ContentAdapter contentAdapter= new ContentAdapter(context, allMedicines, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        }

        else if(adapterPosition == 4){
            this.adapterEventType= DatabaseHandler.TABLE_MOVIE;

            List<Movie> allMovies= dh.getAllMovies();
            ContentAdapter contentAdapter= new ContentAdapter(context, allMovies, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        }

        else if(adapterPosition == 5){
            this.adapterEventType= DatabaseHandler.TABLE_AUDIO_BOOK;

            List<AudioBook> allAudioBooks= dh.getAllAudioBooks();
            ContentAdapter contentAdapter= new ContentAdapter(context, allAudioBooks, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        }


    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        /*FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();*/
        Toast.makeText(this, "ha mallii ", Toast.LENGTH_SHORT).show();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
