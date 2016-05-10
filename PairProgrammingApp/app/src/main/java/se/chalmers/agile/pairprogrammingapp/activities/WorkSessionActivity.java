package se.chalmers.agile.pairprogrammingapp.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.fragments.TestCasesFragment;
import se.chalmers.agile.pairprogrammingapp.fragments.TimerFragment;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

public class WorkSessionActivity extends AppCompatActivity {

    private TabsAdapter mTabsAdapter;

    private static final int TEST_CASES = 0;
    private static final int NOTES = 1;
    private static final int TIMER = 2;

    private String mUnitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_session);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUnitId = extras.getString(ExtraKeys.UNIT_ID);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            String[] tabNames = getResources().getStringArray(R.array.session_tabs_names);

            // Set up view pager
            // Set up ViewPager and adapter
            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            mTabsAdapter = new TabsAdapter(getSupportFragmentManager());
            viewPager.setAdapter(mTabsAdapter);


            mTabsAdapter.addTab(tabNames[TEST_CASES], TestCasesFragment.newInstance(mUnitId), TEST_CASES);

            mTabsAdapter.addTab(tabNames[NOTES], TestCasesFragment.newInstance(mUnitId), NOTES);

            mTabsAdapter.addTab(tabNames[TIMER], TimerFragment.newInstance(), TIMER);

            TabLayout tabLayout =
                    (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText(tabNames[TEST_CASES]));
            tabLayout.addTab(tabLayout.newTab().setText(tabNames[NOTES]));
            tabLayout.addTab(tabLayout.newTab().setText(tabNames[TIMER]));

            viewPager.addOnPageChangeListener(new
                    TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
        }
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
