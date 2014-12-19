package uk.co.appsbystudio.easybmi;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.util.Locale;

import uk.co.appsbystudio.easybmi.SQL.DBHelper;
import uk.co.appsbystudio.easybmi.SQL.HistoryRepo;
import uk.co.appsbystudio.easybmi.SQL.SQL_History_table;
import uk.co.appsbystudio.easybmi.Scale_Info.scale_detail;


public class MainActivity extends ActionBarActivity implements android.support.v7.app.ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    public void onClick(View view){
        EditText weight_entry = (EditText) findViewById(R.id.weight_entry);
        EditText height_entry = (EditText) findViewById(R.id.height_entry);
        TextView weight_label = (TextView) findViewById(R.id.weight_label);
        TextView height_label = (TextView) findViewById(R.id.height_label);
        TextView calc_label = (TextView) findViewById(R.id.total_label);
        Button calculate = (Button) findViewById(R.id.calculate);
        String check_1 = weight_entry.getText().toString();
        String check_2 = height_entry.getText().toString();

        if (check_1.matches("")|| check_2.matches("")){
            return;
        } else {
            float t;
            float w = Float.parseFloat(weight_entry.getText().toString());
            float h = Float.parseFloat(height_entry.getText().toString());

            t = (float) ((double)Math.round(10D * (double)(w / (h * h))) / 10D);
            calc_label.setText("Your BMI is: " + Float.toString(t));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Calculator();
                case 1:
                    return new Scale();
                case 2:
                    return new History();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }


    public static class Calculator extends Fragment {

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_calculator, container, false);
            return rootView;
        }
    }

    public static class Scale extends Fragment {

        private AbsListView mListView;
        private ListAdapter mAdapter;

        public Scale() {

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            String[] scale_values = new String[] {"Very severely underweight", "Severely underweight", "Underweight", "Normal", "Overweight", "Obese"};

            mAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, scale_values);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_scale, container, false);

            // Set the adapter
            mListView = (AbsListView) view.findViewById(android.R.id.list);
            ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

            mListView.setOnItemClickListener(new itemSelected());

            return view;
        }

        private class  itemSelected implements  ListView.OnItemClickListener{
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        }

        public void selectItem(int position) {
            TextView detail = (TextView) getView().findViewById(R.id.detail);
            Intent intent = new Intent(getActivity(), scale_detail.class);
            switch (position) {
                case 0:
                    startActivity(intent);
                case 1:
                    startActivity(intent);
                case 2:
                    startActivity(intent);
                case 3:
                    startActivity(intent);
                case 4:
                    startActivity(intent);
                case 5:
                    startActivity(intent);
            }
        }

    }

    public static class History extends Fragment {

        private AbsListView mListView;
        private ListAdapter mAdapter;

        public History() {

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            String[] values = new String[] {"1", "2", "3", "4"};

            mAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_history, container, false);

            // Set the adapter
            mListView = (AbsListView) view.findViewById(android.R.id.list);
            ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

            return view;
        }

    }
}
