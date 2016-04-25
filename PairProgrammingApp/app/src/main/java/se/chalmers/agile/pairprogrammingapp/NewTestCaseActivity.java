package se.chalmers.agile.pairprogrammingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import se.chalmers.agile.pairprogrammingapp.activities.TestCasesActivity;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;

public class NewTestCaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test_case);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openTestCases(View view)
    {
        TestCasesActivity.mTestCases.add(
                new TestCase(
                        ((EditText) this.findViewById(R.id.item_TestCaseTitle)).getText().toString(),
                        ((EditText) this.findViewById(R.id.item_TestCaseDescription)).getText().toString(), 3, 0));
        Intent intent = new Intent(NewTestCaseActivity.this, TestCasesActivity.class);
        startActivity(intent);
    }

}
