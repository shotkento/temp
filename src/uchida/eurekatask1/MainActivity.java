
package uchida.eurekatask1;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uchida.eurekatask1.BaseFragment.Dribbble;
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
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends FragmentActivity implements TabListener {

    ViewPager mViewPager;
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private String mNextPageToken;
    private ArrayList<Dribbble> dribbbleList;

    private final String urlEveryone = "http://api.dribbble.com/shots/everyone";
    private final String urlDebuts = "http://api.dribbble.com/shots/debuts";
    private final String urlPopular = "http://api.dribbble.c om/shots/popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("test", "test onCreate");
        dribbbleList = new ArrayList<Dribbble>();
        mQueue = Volley.newRequestQueue(this);
        mQueue.add(new JsonObjectRequest(Method.GET, urlEveryone, null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray shots = response.getJSONArray("shots");

                            for (int i = 0; i < shots.length(); i++) {
                                JSONObject shot = shots.getJSONObject(i);
                                JSONObject player = shot.getJSONObject("player");
                                dribbbleList.add(new Dribbble(shot.getString("title"),
                                        shot.getString("image_url"), shot.getString("likes_count"),
                                        player.getString("name")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        temp();
                        Log.w("test DOYA!", "test size=" + dribbbleList.size());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }));

    }

    public void temp() {

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                getActionBar().setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < adapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(adapter.getPageTitle(i))
                    .setTabListener(this));
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        final String[] tabTitle = {
                "My Trip", "Gourmet"
        };

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("test", "test getItem");
            switch (position) {
                case 0:
                    Log.w("test MAE!", "test size=" + dribbbleList.size());
                    return new BaseFragment(dribbbleList);
                case 1:
                    return new BaseFragment(dribbbleList);
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return tabTitle[0];
                case 1:
                    return tabTitle[1];
            }
            return super.getPageTitle(position);
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
