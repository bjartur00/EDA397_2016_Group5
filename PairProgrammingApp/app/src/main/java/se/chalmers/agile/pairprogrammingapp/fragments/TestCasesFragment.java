package se.chalmers.agile.pairprogrammingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.chalmers.agile.pairprogrammingapp.R;

public class TestCasesFragment extends Fragment {
    private static final String ARG_UNIT_ID = "unitId";

    private String mUnitId;


    public TestCasesFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of the TestCasesFragment
     *
     * @param unitId The unit ID.
     * @return A new instance of fragment TestCasesFragment.
     */
    public static TestCasesFragment newInstance(String unitId) {
        TestCasesFragment fragment = new TestCasesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_UNIT_ID, unitId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUnitId = getArguments().getString(ARG_UNIT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_cases, container, false);
    }

}
