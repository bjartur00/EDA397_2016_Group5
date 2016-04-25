package se.chalmers.agile.pairprogrammingapp.modelview;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.NewTestCaseActivity;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.activities.TestCasesActivity;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;


/*
 * Copyright (C), Owner, Omar Thor Omarsson and co.
*/
// Here is the adapter built for the Test cases
public class TestCasesListAdapter extends RecyclerView.Adapter<TestCasesListAdapter.TestCaseViewHolder> {

    // The global variables
    private ArrayList<TestCase> testCases;
    private OnTestCaseItemClickedListener mListener;

    //Here the adapter is given the correct test cases and has a listener which is responsible for identifying the item that was selected / clicked
    public TestCasesListAdapter(ArrayList<TestCase> pTestCases, OnTestCaseItemClickedListener listener) {
        this.testCases = pTestCases;
        this.mListener = listener;
    }

    @Override
    public TestCasesListAdapter.TestCaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.testcase, parent, false);

        TestCaseViewHolder testCaseViewHolder = new TestCaseViewHolder(view, mListener);
        return testCaseViewHolder;
    }

    @Override
    public void onBindViewHolder(TestCasesListAdapter.TestCaseViewHolder holder, int position) {
        holder.setData(position, testCases.get(position));
    }

    @Override
    public int getItemCount() {
        return testCases.size();
    }

    public void deleteItem(int index) {
        if (index < testCases.size()) {
            testCases.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void notifyItemAdded() {
        notifyItemInserted(testCases.size() - 1);
    }

    public void notifyItemModified(int position) {
        notifyItemChanged(position);
    }

    public static class TestCaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnTestCaseItemClickedListener listener;
        private int position;

        private TextView tvTitle;
        private TextView tvDescription;

        public TestCaseViewHolder(View itemView, OnTestCaseItemClickedListener listener) {
            super(itemView);

            this.listener = listener;
            tvTitle = (TextView) itemView.findViewById(R.id.item_TestCaseTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.item_TestCaseDescription);

            itemView.findViewById(R.id.testCase).setOnClickListener(this);
        }

        public void setData(int position, TestCase testCase) {
            this.position = position;
            TestCase currentTestCase = TestCasesActivity.mTestCases.get(position);

            switch (currentTestCase.getStatus()) {
                case 1:  itemView.setBackgroundColor(Color.GREEN);
                    break;
                case 2:  itemView.setBackgroundColor(Color.YELLOW);
                    break;
                case 3:  itemView.setBackgroundColor(Color.RED);
                    break;
                default: itemView.setBackgroundColor(Color.WHITE);
                    break;
            }

            tvTitle.setText(testCase.getTitle());
            tvDescription.setText(testCase.getDescription());
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                if (v.getId() == R.id.testCase) {
                    TestCase clickedTestCase = TestCasesActivity.mTestCases.get(position);
                    switch (clickedTestCase.getStatus()) {
                        case 1:
                            v.setBackgroundColor(Color.YELLOW);
                            clickedTestCase.setStatus(clickedTestCase.getStatus() + 1);
                            break;
                        case 2:
                            v.setBackgroundColor(Color.RED);
                            clickedTestCase.setStatus(clickedTestCase.getStatus() + 1);
                            break;
                        case 3:
                            v.setBackgroundColor(Color.GREEN);
                            clickedTestCase.setStatus(clickedTestCase.getStatus() - 2);
                            break;
                        default:
                            v.setBackgroundColor(Color.WHITE);
                            break;
                    }
                }  else if (v.getId() == R.id.item_testCaseChange) {
                    listener.onChangeTestCaseClicked(getAdapterPosition());
                }  else if (v.getId() == R.id.item_testCaseDelete) {
                    listener.onDeleteTestCaseClicked(getAdapterPosition());
                }
            }
        }
    }

    // Abstract classes
    public interface OnTestCaseItemClickedListener {
        void onTestCaseItemClicked(int position);

        void onChangeTestCaseClicked(int position);

        void onDeleteTestCaseClicked(int position);
    }
}
