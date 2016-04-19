package se.chalmers.agile.pairprogrammingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

public class DisplayProjectActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.wanziguelva.myapplication.MESSAGE";
    public String message = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        ((TextView)findViewById(R.id.project_title)).setText(intent.getStringExtra(ExtraKeys.USERNAME));

        populateListView();
        registerClickCallBack();
    }

    private void populateListView() {
        //create the list of items
        String[] projectItems = {"Project 1", "Project 2", "Project 3", "Project 4"};

        //build the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.project_items,   //layout used to create the list
                projectItems);

        //configure the list view
        ListView list = (ListView) findViewById(R.id.project_list);
        list.setAdapter(adapter);

    }

    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.project_list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewclicked, int position, long id) {
                //position gives the number of the item clicked.
                Intent intent = new Intent(DisplayProjectActivity.this, DisplayUnitsActivity.class);
                TextView textView = (TextView) viewclicked;
                message = textView.getText().toString();

                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

    }

}
