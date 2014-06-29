
package uchida.eurekatask1;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity implements TabListener {

    ViewPager mViewPager;

    private final String[] TAB_TITLE = {
            "everyone", "debuts", "popular"
    };

    private final String URL_EVERYONE = "http://api.dribbble.com/shots/everyone";
    private final String URL_DEBUTS = "http://api.dribbble.com/shots/debuts";
    private final String URL_POPULAR = "http://api.dribbble.com/shots/popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ActionBarにタブを設定
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // PagerにFragmentを設定
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                getActionBar().setSelectedNavigationItem(position);
            }
        });

        // タブ項目追加
        for (int i = 0; i < adapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(adapter.getPageTitle(i))
                    .setTabListener(this));
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment fragment = new MyFragment();
            Bundle data = new Bundle();
            data.putString(getString(R.string.KEY_CATEGORY), TAB_TITLE[position]);
            switch (position) {
                case 0:
                    data.putString(getString(R.string.KEY_URL), URL_EVERYONE);
                    break;
                case 1:
                    data.putString(getString(R.string.KEY_URL), URL_DEBUTS);
                    break;
                case 2:
                    data.putString(getString(R.string.KEY_URL), URL_POPULAR);
                    break;
            }
            fragment.setArguments(data);
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_TITLE.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLE[position];
        }
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
}
