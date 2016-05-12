package se.chalmers.agile.pairprogrammingapp.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.fragments.DisplayNotesFragment;
import se.chalmers.agile.pairprogrammingapp.fragments.TestCasesFragment;
import se.chalmers.agile.pairprogrammingapp.fragments.TimerFragment;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

public class WorkSessionActivity extends AppCompatActivity {

    private TabsAdapter mTabsAdapter;

    private static final int TEST_CASES = 0;
    private static final int NOTES = 1;
    private static final int TIMER = 2;

    private String mProjectId;
    private String mUnitId;

    private TestCasesFragment mTestCasesFragment;
    private DisplayNotesFragment mDisplayNotesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_session);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUnitId = extras.getString(ExtraKeys.UNIT_ID);
            mProjectId = extras.getString(ExtraKeys.PROJECT_ID);

            getSupportActionBar().setTitle(extras.getString(ExtraKeys.UNIT_NAME));

            String[] tabNames = getResources().getStringArray(R.array.session_tabs_names);

            // Set up view pager
            // Set up ViewPager and adapter
            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            mTabsAdapter = new TabsAdapter(getSupportFragmentManager());
            viewPager.setAdapter(mTabsAdapter);


            mTestCasesFragment = TestCasesFragment.newInstance(mUnitId);
            mTabsAdapter.addTab(tabNames[TEST_CASES], mTestCasesFragment, TEST_CASES);

            mDisplayNotesFragment = DisplayNotesFragment.newInstance(mProjectId);
            mTabsAdapter.addTab(tabNames[NOTES], mDisplayNotesFragment, NOTES);

            mTabsAdapter.addTab(tabNames[TIMER], TimerFragment.newInstance(mProjectId), TIMER);

            TabLayout tabLayout =
                    (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText(tabNames[TEST_CASES]));
            tabLayout.addTab(tabLayout.newTab().setText(tabNames[NOTES]));
            tabLayout.addTab(tabLayout.newTab().setText(tabNames[TIMER]));

            // Listener for tab layout
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            // Listener for hiding and showing the fab
            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    /*if (position == TIMER) {
                        fab.hide();
                    } else {
                        fab.show();
                    }*/
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }

            });


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewPager.getCurrentItem() == NOTES) {
                        mDisplayNotesFragment.addNewNote();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_projects, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.action_logout) {
            //((PairProgrammingApplication) getApplication()).showLogoutDialog(DisplayProjectActivity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("Log out");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ((PairProgrammingApplication) getApplication()).logOut(WorkSessionActivity.this);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Close dialog
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static class TabsAdapter extends FragmentStatePagerAdapter {

        private final HashMap<Integer, Fragment> mFragments;
        private final ArrayList<Integer> mTabNums;
        private final ArrayList<CharSequence> mTabTitles;

        @SuppressLint("UseSparseArrays")
        public TabsAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new HashMap<>();
            mTabNums = new ArrayList<>();
            mTabTitles = new ArrayList<>();
        }

        public void addTab(String tabTitle, Fragment newFragment, int tabId) {
            mTabTitles.add(tabTitle);
            mFragments.put(tabId, newFragment);
            mTabNums.add(tabId);
            notifyDataSetChanged();
        }

        public Fragment getTabFragment(int tabNum) {
            if (mFragments.containsKey(tabNum)) {
                return mFragments.get(tabNum);
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(mTabNums.get(position));
        }
    }
}
