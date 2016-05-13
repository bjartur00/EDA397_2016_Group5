package se.chalmers.agile.pairprogrammingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;
import se.chalmers.agile.pairprogrammingapp.modelview.TestCasesListAdapter;
import se.chalmers.agile.pairprogrammingapp.network.JsonSerializer;
import se.chalmers.agile.pairprogrammingapp.network.RequestHandler;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;

public class DisplayTestCasesFragment extends Fragment implements TestCasesListAdapter.OnTestCaseItemClickedListener {
    private static final String ARG_UNIT_ID = "unitId";

    private String mUnitId;

    private final static String TAG = "se.chalmers.agile.pairprogrammingapp.fragments.DisplayTestCasesFragment";

    // Variable that holds all the test cases that are displayed
    public static ArrayList<TestCase> mTestCases;
    // Variable the hold the view adapter.
    private TestCasesListAdapter mAdapter;


    public DisplayTestCasesFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of the DisplayTestCasesFragment
     *
     * @param unitId The unit ID.
     * @return A new instance of fragment DisplayTestCasesFragment.
     */
    public static DisplayTestCasesFragment newInstance(String unitId) {
        DisplayTestCasesFragment fragment = new DisplayTestCasesFragment();
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
                            mTestCases = JsonSerializer.json2TestCases(data);

                            RecyclerView rvList = (RecyclerView) getView().findViewById(R.id.listViewTestCases);
                            rvList.setLayoutManager(new LinearLayoutManager(getContext()));
                            mAdapter = new TestCasesListAdapter(mTestCases, DisplayTestCasesFragment.this);
                            rvList.setAdapter(mAdapter);
                        }

                        @Override
                        public void onJsonDataLoadingFailure(int errorId) {
                        }
                    }, Request.Priority.HIGH, TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_cases, container, false);
    }


    @Override
    public void onTestCaseItemClicked(final int position) {
        final TestCase clickedTestCase = mTestCases.get(position);
        String newColor = "";
        String oldColor = "";
        int newColorId = TestCase.TESTING;
        switch (clickedTestCase.getStatus()) {
            case TestCase.PASSED:
                oldColor = "green";
                newColor = "yellow";
                newColorId = TestCase.TESTING;
                break;
            case TestCase.TESTING:
                oldColor = "yellow";
                newColor = "red";
                newColorId = TestCase.NOT_PASSED;
                break;
            case TestCase.NOT_PASSED:
                oldColor = "red";
                newColor = "green";
                newColorId = TestCase.PASSED;
                break;
        }

        // To be used in inner class
        final int finalNewColorId = newColorId;
        final String finalNewColor = newColor;

        RequestHandler.loadStringDelete(
                TrelloUrls.removeTestCaseColorUrl(
                        clickedTestCase.getId(),
                        oldColor,
                        ((PairProgrammingApplication) getActivity().getApplication()).getToken()),
                new RequestHandler.OnStringLoadedListener() {
                    @Override
                    public void onStringLoadedSuccessfully(String data) {
                        RequestHandler.loadJsonDataPost(
                                TrelloUrls.addTestCaseColorUrl(
                                        clickedTestCase.getId(),
                                        finalNewColor,
                                        ((PairProgrammingApplication) getActivity().getApplication()).getToken()),
                                new RequestHandler.OnJsonDataLoadedListener() {
                                    @Override
                                    public void onJsonDataLoadedSuccessfully(JSONObject data) {
                                        clickedTestCase.setStatus(finalNewColorId);
                                        mAdapter.notifyItemChanged(position);
                                    }

                                    @Override
                                    public void onJsonDataLoadingFailure(int errorId) {
                                    }
                                },
                                null,
                                Request.Priority.HIGH,
                                TAG);
                    }

                    @Override
                    public void onStringLoadingFailure(int errorId) {
                    }
                },
                Request.Priority.HIGH,
                TAG);
    }

    @Override
    public void onEditTestCaseClicked(int position) {

    }

    @Override
    public void onDeleteTestCaseClicked(int position) {

    }
}
