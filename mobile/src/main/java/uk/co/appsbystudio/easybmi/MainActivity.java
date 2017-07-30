package uk.co.appsbystudio.easybmi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import uk.co.appsbystudio.easybmi.pages.Calculator;
import uk.co.appsbystudio.easybmi.pages.History;
import uk.co.appsbystudio.easybmi.pages.Scale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new Calculator();
                    case 1:
                        return new Scale();
                    case 2:
                        return new History();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Calculate";
                    case 1:
                        return "Scale";
                    case 2:
                        return "History";
                    default:
                        return null;
                }
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        SharedPreferences sharedPreferences = getSharedPreferences("remember", Context.MODE_PRIVATE);
        if (sharedPreferences.getInt("remember", 0) == 0) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("remember", 0);
            editor.apply();
        }
    }


}
