package com.awesome.wathmal.awesomeapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends FragmentActivity
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
    private ActionMode.Callback mActionModeCallback;
    android.view.ActionMode mActionMode = null;

    /*variables to handle long press items*/
    private int longPressedPosition;
    private int clickedItemPosition;
    private String longPressedType;


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

        this.dh = new DatabaseHandler(this);
        this.context = this;

        List<Event> allEvents = dh.getAllEvents();
        this.adapterEventType = DatabaseHandler.TABLE_EVENT;

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // Attach recycler view to the floating action button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.action_addEvent);
        FloatingActionButton fabMedicineButton = (FloatingActionButton) findViewById(R.id.action_medicine);

        FloatingActionButton fabMovieButton = (FloatingActionButton) findViewById(R.id.action_movie);
        FloatingActionButton fabAudioBookButton = (FloatingActionButton) findViewById(R.id.action_audio_book);
        FloatingActionButton fabBookButton = (FloatingActionButton) findViewById(R.id.action_book);

        /*fab / FloatingActionButton listeners*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEventIntent = new Intent(MainActivity.this, AddEventActivity.class);
                startActivity(addEventIntent);
            }
        });
        fabMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMedicineIntent = new Intent(MainActivity.this, AddEventActivity.class);
                addMedicineIntent.putExtra("eventType", 2);

                startActivity(addMedicineIntent);

            }
        });
        fabMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
                intent.putExtra("eventType", 3);

                startActivity(intent);

            }
        });
        fabAudioBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
                intent.putExtra("eventType", 4);

                startActivity(intent);

            }
        });
        fabBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
                intent.putExtra("eventType", 1);

                startActivity(intent);
            }
        });



        /*
        * set the adapter to RecyclerView
        * */
        ContentAdapter contentAdapter = new ContentAdapter(context, allEvents, this.adapterEventType);
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
                if (adapterEventType.equals(DatabaseHandler.TABLE_EVENT)) {
                    List<Event> allEvents = dh.getAllEvents();
                    ContentAdapter contentAdapter = new ContentAdapter(context, allEvents, adapterEventType);
                    mRecyclerView.setAdapter(contentAdapter);
                } else if (adapterEventType.equals(DatabaseHandler.TABLE_BOOK)) {
                    List<Book> allBooks = dh.getAllBooks();
                    ContentAdapter contentAdapter = new ContentAdapter(context, allBooks, adapterEventType);
                    mRecyclerView.setAdapter(contentAdapter);
                } else if (adapterEventType.equals(DatabaseHandler.TABLE_MEDICINE)) {

                    List<Medicine> allMedicines = dh.getAllMedicines();
                    ContentAdapter contentAdapter = new ContentAdapter(context, allMedicines, adapterEventType);
                    mRecyclerView.setAdapter(contentAdapter);
                } else if (adapterEventType.equals(DatabaseHandler.TABLE_MOVIE)) {

                    List<Movie> allMovies = dh.getAllMovies();
                    ContentAdapter contentAdapter = new ContentAdapter(context, allMovies, adapterEventType);
                    mRecyclerView.setAdapter(contentAdapter);
                } else if (adapterEventType.equals(DatabaseHandler.TABLE_AUDIO_BOOK)) {

                    List<AudioBook> allAudioBooks = dh.getAllAudioBooks();
                    ContentAdapter contentAdapter = new ContentAdapter(context, allAudioBooks, adapterEventType);
                    mRecyclerView.setAdapter(contentAdapter);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        /*
        * contextual action bar setup and
        * click listeners
        * */
        mActionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

                // edit button
                if (menuItem.getItemId() == R.id.item_edit) {

                    if (adapterEventType.equals(DatabaseHandler.TABLE_BOOK)) {
                        android.support.v4.app.DialogFragment dialogAddBook = new DialogAddBook(context, false, true,

                                (Book) dh.getAllBooks().get(clickedItemPosition)
                        );
                        dialogAddBook.show(getSupportFragmentManager(), "book");
                    } else if (adapterEventType.equals(DatabaseHandler.TABLE_MEDICINE)) {
                        android.support.v4.app.DialogFragment dialogAddMedicine = new DialogAddMedicine(context, false, true,

                                (Medicine) dh.getAllMedicines().get(clickedItemPosition)
                        );
                        dialogAddMedicine.show(getSupportFragmentManager(), "medicine");
                    } else if (adapterEventType.equals(DatabaseHandler.TABLE_MOVIE)) {
                        android.support.v4.app.DialogFragment dialogAddMovie = new DialogAddMovie(context, false, true,

                                (Movie) dh.getAllMovies().get(clickedItemPosition)
                        );
                        dialogAddMovie.show(getSupportFragmentManager(), "movie");
                    } else if (adapterEventType.equals(DatabaseHandler.TABLE_AUDIO_BOOK)) {

                        android.support.v4.app.DialogFragment dialogAddAudioBook = new DialogAddAudioBook(context, false, true,

                                (AudioBook) dh.getAllAudioBooks().get(clickedItemPosition)
                        );
                        dialogAddAudioBook.show(getSupportFragmentManager(), "audio book");
                    } else {
                        android.support.v4.app.DialogFragment dialogEvent = new DialogEvent(context, false, true,

                                dh.getAllEvents().get(clickedItemPosition)
                        );
                        dialogEvent.show(getSupportFragmentManager(), "event");
                    }
                    actionMode.finish();
                }

                // delete button
                else if (menuItem.getItemId() == R.id.item_delete) {
                    dh.deleteItemFromATable(longPressedPosition, longPressedType);

                    Toast.makeText(context, longPressedType + " item deleted", Toast.LENGTH_SHORT).show();
                    refreshRecyclerView();
                    actionMode.finish();
                }

                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                mActionMode = null;
            }
        };


    }

    /*
    * refresh the main recycler view based on navigational drawer selections
    * */
    public void refreshAdapter(int adapterPosition) {
        String[] drawerMenuNames = getResources().getStringArray(R.array.drawer_list);
        /*
        * use switch() case;
        * */
    if(adapterPosition!=0 && !caldroidFragment.equals(null)){
        caldroidFragment.dismiss();//hide();
        mRecyclerView.setVisibility(View.VISIBLE);
    }
     if (adapterPosition==0){

        showCalender();
    }
         else if (adapterPosition == 1) {
            this.adapterEventType = DatabaseHandler.TABLE_EVENT;

            List<Event> allEvents = dh.getAllEvents();
            ContentAdapter contentAdapter = new ContentAdapter(this, allEvents, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);


        } else if (adapterPosition == 2) {
            this.adapterEventType = DatabaseHandler.TABLE_BOOK;

            List<Book> allBooks = dh.getAllBooks();
            ContentAdapter contentAdapter = new ContentAdapter(context, allBooks, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        } else if (adapterPosition == 3) {
            this.adapterEventType = DatabaseHandler.TABLE_MEDICINE;

            List<Medicine> allMedicines = dh.getAllMedicines();
            ContentAdapter contentAdapter = new ContentAdapter(context, allMedicines, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        } else if (adapterPosition == 4) {
            this.adapterEventType = DatabaseHandler.TABLE_MOVIE;

            List<Movie> allMovies = dh.getAllMovies();
            ContentAdapter contentAdapter = new ContentAdapter(context, allMovies, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        } else if (adapterPosition == 5) {
            this.adapterEventType = DatabaseHandler.TABLE_AUDIO_BOOK;

            List<AudioBook> allAudioBooks = dh.getAllAudioBooks();
            ContentAdapter contentAdapter = new ContentAdapter(context, allAudioBooks, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        }

        // set the title of action bar
        getActionBar().setTitle(drawerMenuNames[adapterPosition]);
        mNavigationDrawerFragment.refresh();
        mNavigationDrawerFragment.closeDrawer();


    }


    /*
    * initial overrides
    * not functioning, but removing may break the flow
    * */
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
            //restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /*
    * main activity menu actions
    * add appropriate actions
    * */
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

    /*
    * long press click action function
    * adapter calls this function when a long click on recycler view item
    * actual functionality of CAB is on mActionModeCallback inside onCreate()
    * */
    public void setContextMenu(int position, String type) {
        if (mActionMode != null) {

        }
        if (type.equals(this.adapterEventType)) {
            this.longPressedPosition = position;
            this.longPressedType = type;
        } else {

        }
        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = this.startActionMode(mActionModeCallback);

    }

    /*
    * this to refresh adapter from refreshAdapter()
    * we should use that method without this method
    * bad engineering!
    * */
    private void refreshRecyclerView() {
        if (this.longPressedType.equals(DatabaseHandler.TABLE_EVENT)) {
            refreshAdapter(1);
        } else if (this.longPressedType.equals(DatabaseHandler.TABLE_BOOK)) {
            refreshAdapter(2);
        } else if (this.longPressedType.equals(DatabaseHandler.TABLE_MEDICINE)) {
            refreshAdapter(3);
        } else if (this.longPressedType.equals(DatabaseHandler.TABLE_MOVIE)) {
            refreshAdapter(4);
        } else if (this.longPressedType.equals(DatabaseHandler.TABLE_AUDIO_BOOK)) {
            refreshAdapter(5);
        }
    }

    /*
    * handles on click events on recycler view
    * adapter calls this method when an item is clicked
    * */
    public void showViewingDialog(String eventType, int position) {
        if (eventType.equals(this.adapterEventType)) {
            // negation can't be happen
            this.clickedItemPosition = position;

            if (eventType.equals(DatabaseHandler.TABLE_BOOK)) {
                android.support.v4.app.DialogFragment dialogAddBook = new DialogAddBook(context, true, false,

                        (Book) dh.getAllBooks().get(position)
                );
                dialogAddBook.show(getSupportFragmentManager(), "book");
            } else if (eventType.equals(DatabaseHandler.TABLE_MEDICINE)) {
                android.support.v4.app.DialogFragment dialogAddMedicine = new DialogAddMedicine(context, true, false,

                        (Medicine) dh.getAllMedicines().get(position)
                );
                dialogAddMedicine.show(getSupportFragmentManager(), "medicine");
            } else if (eventType.equals(DatabaseHandler.TABLE_MOVIE)) {
                android.support.v4.app.DialogFragment dialogAddMovie = new DialogAddMovie(context, true, false,

                        (Movie) dh.getAllMovies().get(position)
                );
                dialogAddMovie.show(getSupportFragmentManager(), "movie");
            } else if (eventType.equals(DatabaseHandler.TABLE_AUDIO_BOOK)) {

                android.support.v4.app.DialogFragment dialogAddAudioBook = new DialogAddAudioBook(context, true, false,

                        (AudioBook) dh.getAllAudioBooks().get(position)
                );
                dialogAddAudioBook.show(getSupportFragmentManager(), "audio book");
            } else {
                android.support.v4.app.DialogFragment dialogEvent = new DialogEvent(context, true, false,

                        dh.getAllEvents().get(position)
                );
                dialogEvent.show(getSupportFragmentManager(), "event");

                //Toast.makeText(context, "viewing not implemented yet!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapterEventType.equals(DatabaseHandler.TABLE_EVENT)) {
            List<Event> allEvents = dh.getAllEvents();
            ContentAdapter contentAdapter = new ContentAdapter(context, allEvents, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        } else if (adapterEventType.equals(DatabaseHandler.TABLE_BOOK)) {
            List<Book> allBooks = dh.getAllBooks();
            ContentAdapter contentAdapter = new ContentAdapter(context, allBooks, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        } else if (adapterEventType.equals(DatabaseHandler.TABLE_MEDICINE)) {

            List<Medicine> allMedicines = dh.getAllMedicines();
            ContentAdapter contentAdapter = new ContentAdapter(context, allMedicines, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        } else if (adapterEventType.equals(DatabaseHandler.TABLE_MOVIE)) {

            List<Movie> allMovies = dh.getAllMovies();
            ContentAdapter contentAdapter = new ContentAdapter(context, allMovies, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        } else if (adapterEventType.equals(DatabaseHandler.TABLE_AUDIO_BOOK)) {

            List<AudioBook> allAudioBooks = dh.getAllAudioBooks();
            ContentAdapter contentAdapter = new ContentAdapter(context, allAudioBooks, adapterEventType);
            mRecyclerView.setAdapter(contentAdapter);
        }
        mNavigationDrawerFragment.refresh();
    }

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    public void showCalender(){
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.calender_main);

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        // caldroidFragment = new CaldroidFragment();

        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
        caldroidFragment = new CalenderCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        /*if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }*/
        // If activity is created from fresh
       // else {
        {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }

       // setCustomResourceForDates();

        // Attach to the activity
        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        //FragmentTransaction t = getChildFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getApplicationContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        final TextView textView = (TextView) findViewById(R.id.textview);

        /*final Button customizeButton = (Button) findViewById(R.id.customize_button);

        // Customize the calendar
        customizeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (undo) {
                    customizeButton.setText(getString(R.string.customize));
                    textView.setText("");

                    // Reset calendar
                    caldroidFragment.clearDisableDates();
                    caldroidFragment.clearSelectedDates();
                    caldroidFragment.setMinDate(null);
                    caldroidFragment.setMaxDate(null);
                    caldroidFragment.setShowNavigationArrows(true);
                    caldroidFragment.setEnableSwipe(true);
                    caldroidFragment.refreshView();
                    undo = false;
                    return;
                }

                // Else
                undo = true;
                customizeButton.setText(getString(R.string.undo));
                Calendar cal = Calendar.getInstance();

                // Min date is last 7 days
                cal.add(Calendar.DATE, -7);
                Date minDate = cal.getTime();

                // Max date is next 7 days
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 14);
                Date maxDate = cal.getTime();

                // Set selected dates
                // From Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 2);
                Date fromDate = cal.getTime();

                // To Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 3);
                Date toDate = cal.getTime();

                // Set disabled dates
                ArrayList<Date> disabledDates = new ArrayList<Date>();
                for (int i = 5; i < 8; i++) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, i);
                    disabledDates.add(cal.getTime());
                }

                // Customize
                caldroidFragment.setMinDate(minDate);
                caldroidFragment.setMaxDate(maxDate);
                caldroidFragment.setDisableDates(disabledDates);
                caldroidFragment.setSelectedDates(fromDate, toDate);
                caldroidFragment.setShowNavigationArrows(false);
                caldroidFragment.setEnableSwipe(false);

                caldroidFragment.refreshView();

                // Move to date
                // cal = Calendar.getInstance();
                // cal.add(Calendar.MONTH, 12);
                // caldroidFragment.moveToDate(cal.getTime());

                String text = "Today: " + formatter.format(new Date()) + "\n";
                text += "Min Date: " + formatter.format(minDate) + "\n";
                text += "Max Date: " + formatter.format(maxDate) + "\n";
                text += "Select From Date: " + formatter.format(fromDate)
                        + "\n";
                text += "Select To Date: " + formatter.format(toDate) + "\n";
                for (Date date : disabledDates) {
                    text += "Disabled Date: " + formatter.format(date) + "\n";
                }

                textView.setText(text);
            }
        });*/

       /* Button showDialogButton = (Button) findViewById(R.id.show_dialog_button);

        //final Bundle state = savedInstanceState;
        showDialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Setup caldroid to use as dialog
                dialogCaldroidFragment = new CaldroidFragment();
                dialogCaldroidFragment.setCaldroidListener(listener);

                // If activity is recovered from rotation
                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
             /*   if (state != null) {
                    dialogCaldroidFragment.restoreDialogStatesFromKey(
                            getSupportFragmentManager(), state,
                            "DIALOG_CALDROID_SAVED_STATE", dialogTag);
                    Bundle args = dialogCaldroidFragment.getArguments();
                    if (args == null) {
                        args = new Bundle();
                        dialogCaldroidFragment.setArguments(args);
                    }
                }*//* {
                    // Setup arguments
                    Bundle bundle = new Bundle();
                    // Setup dialogTitle
                    dialogCaldroidFragment.setArguments(bundle);
                }

                dialogCaldroidFragment.show(getSupportFragmentManager(),
                        dialogTag);
            }


        });*/
        markEvents();
    }
    public void markEvents() {
        DatabaseHandler dh = new DatabaseHandler(this);
        //this.context= this;

        List<Event> allEvents = dh.getAllEvents();
        for (int i = 0; i < allEvents.size(); i++) {
           allEvents.get(i).get_date();
        Date date= allEvents.get(0).get_date();
            //TextDrawable drawable2 = TextDrawable.builder().buildRect("S",2);
        caldroidFragment.setBackgroundResourceForDate(R.color.blue, date);
        caldroidFragment.setTextColorForDate(R.color.white, date);
           // caldroidFragment.
        //caldroidFragment.set
        }
    }
}
