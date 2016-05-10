package se.chalmers.agile.pairprogrammingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;

import org.json.JSONArray;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;
import se.chalmers.agile.pairprogrammingapp.modelview.TestCasesListAdapter;
import se.chalmers.agile.pairprogrammingapp.network.RequestHandler;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

public class TestCasesFragment extends Fragment implements TestCasesListAdapter.OnTestCaseItemClickedListener {
    private static final String ARG_UNIT_ID = "unitId";

    private String mUnitId;

    private final static String TAG = "se.chalmers.agile.pairprogrammingapp.fragments.TestCasesFragment";

    // Variable that holds all the test cases that are displayed
    public static ArrayList<TestCase> mTestCases;
    // Variable the hold the view adapter.
    private TestCasesListAdapter mAdapter;


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
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mUnitId = getArguments().getString(ARG_UNIT_ID);
            RequestHandler.loadJsonArrayGet(TrelloUrls.getTestCasesUrl(mUnitId, ((PairProgrammingApplication) getActivity().getApplication()).getToken()),
                    new RequestHandler.OnJsonArrayLoadedListener() {
                        @Override
                        public void onJsonDataLoadedSuccessfully(JSONArray data) {
                            mTestCases = new ArrayList<>();

                            int iColor = 0;
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    String color = "";
                                    try {
                                        color = new JSONArray(data.getJSONObject(i).getString("labels")).getJSONObject(0).getString("color");
                                    } catch (Exception e) {
                                        color = "";
                                    }
                                    if (color.contains("green")) {
                                        iColor = 1;
                                    } else if (color.contains("yellow")) {
                                        iColor = 2;
                                    } else if (color.contains("red")) {
                                        iColor = 3;
                                    }
                                    color = "";
                                    mTestCases.add(new TestCase(data.getJSONObject(i).getString("name"), "", iColor, i, data.getJSONObject(i).getString("idList")));
                                } catch (Exception e) {
                                }
                            }
                            populateRecyclerView();
                        }

                        @Override
                        public void onJsonDataLoadingFailure(int errorId) {
                            Log.d("wissam", errorId + "");
                        }
                    }, Request.Priority.HIGH, TAG);
        }
    }

    // Initializes the recycler view for the test cases
    private void populateRecyclerView() {
        RecyclerView rvList = (RecyclerView) getView().findViewById(R.id.listViewTestCases);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TestCasesListAdapter(mTestCases, this);
        rvList.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_cases, container, false);
    }


    @Override
    public void onTestCaseItemClicked(int position) {

    }

    @Override
    public void onChangeTestCaseClicked(int position) {

    }

    @Override
    public void onDeleteTestCaseClicked(int position) {

    }
}
