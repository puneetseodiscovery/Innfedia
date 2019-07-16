package com.mandy.innfedia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.mandy.innfedia.Activities.ActivityCart;
import com.mandy.innfedia.Activities.ActivityOrderList;
import com.mandy.innfedia.Activities.ActivityProfile;
import com.mandy.innfedia.Activities.LoginActivity;
import com.mandy.innfedia.adapter.CategoryAdapter;
import com.mandy.innfedia.adapter.DiscountedAdapter;
import com.mandy.innfedia.adapter.NewArrivalAdapter;
import com.mandy.innfedia.adapter.ViewPagerAdapter;
import com.mandy.innfedia.adapter.main2.ExploreMoreAdapter;
import com.mandy.innfedia.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle mToggle;
    FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // find all id
        init();

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(mToggle);

        mToggle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        manager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, new HomeFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        onNavigationClick();


    }

    //find all id
    private void init() {

        navigationView = (NavigationView) findViewById(R.id.Drawer_navigation);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerNavigation);
        toolbar = (Toolbar) findViewById(R.id.tooolbar);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // navigation item click
    private void onNavigationClick() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.myorderlist:
                        drawerLayout.closeDrawers();
                        startActivity(new Intent(getApplicationContext(), ActivityOrderList.class));
                        break;
                    case R.id.cart:
                        drawerLayout.closeDrawers();
                        startActivity(new Intent(getApplicationContext(), ActivityCart.class));
                        break;
                    case R.id.my_profile:
                        drawerLayout.closeDrawers();
                        startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
                        break;

                    case R.id.my_logout:
                        drawerLayout.closeDrawers();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        break;
                }

                return false;
            }
        });
    }


}
