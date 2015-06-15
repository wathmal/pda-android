package com.awesome.wathmal.awesomeapp;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

public class CalenderCustomFragment extends CaldroidFragment {

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        // TODO Auto-generated method stub
        return new CalenderCustomAdapter(getActivity(), month, year,
                getCaldroidData(), extraData);
    }

}