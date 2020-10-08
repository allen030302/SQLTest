package com.example.sqltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MainPagerAdapter mainPagerAdapter;
    private FragmentSqlSelect fragmentSqlSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        tabLayout = findViewById(R.id.tabLayoutMain);
        viewPager = findViewById(R.id.viewPagerMain);
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainPagerAdapter.addFragment(new FragmentSqlSelect(getApplicationContext()),"顯示模式");
        mainPagerAdapter.addFragment(new FragmentSqlInsert(),"新增模式");
        mainPagerAdapter.addFragment(new FragmentSqlUpdate(),"更新模式");
        mainPagerAdapter.addFragment(new FragmentSqlDelete(),"刪除模式");

        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        fragmentSqlSelect = new FragmentSqlSelect(getApplicationContext());
                        fragmentSqlSelect.SqlGetData();
                        fragmentSqlSelect.ListView_Customer(getApplicationContext());
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                    case 3:
                        viewPager.setCurrentItem(3);
                        break;
                }
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